package eu.openanalytics.phaedra.plateservice.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import eu.openanalytics.phaedra.plateservice.model.IValueObject;
import eu.openanalytics.phaedra.util.caching.model.CacheKey;
import eu.openanalytics.phaedra.util.caching.model.ICache;
import eu.openanalytics.phaedra.util.caching.model.impl.NoopCache;

/**
 * Base class for DAO's that work with a specific type of IValueObject.
 * Uses an internal cache to maintain performance of repeated lookups.
 * 
 * @param <T> The type of IValueObject this DAO will work with.
 */
public abstract class BaseCachingDAO<T extends IValueObject> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private ICache<T> cache;
	
	public BaseCachingDAO() {
		cache = initializeCache();
		if (cache == null) cache = new NoopCache<T>();
	}
	
	protected abstract ICache<T> initializeCache();
	
	protected ICache<T> getCache() {
		return cache;
	}
	
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	/**
	 * Look up a value using its ID.
	 * If the item is not cached, it will be queried in the DB.
	 * 
	 * @param id The ID of the item to find.
	 * @param sql The SQL query to use if the item is not cached.
	 * @param rowMapper The RowMapper to use to translate the SQL result row.
	 * @return A nullable Optional containing the item if it was found.
	 */
	protected Optional<T> findValueById(long id, String sql, RowMapper<T> rowMapper) {
		T value = null;

		CacheKey key = CacheKey.create(id);
		if (cache.contains(key)) {
			value = cache.get(key);
		} else {
			try {
				value = jdbcTemplate.queryForObject(sql, rowMapper);
			} catch (EmptyResultDataAccessException e) {
				// Object not found, cache a null value.
			}
			cache.put(key, value);
		}
		return Optional.ofNullable(value);
	}
	
	/**
	 * Look up a value using its ID.
	 * If the item is not cached, it will be queried in the DB.
	 * 
	 * @param id The ID of the item to find.
	 * @param sql The SQL query to use if the item is not cached.
	 * @param rsExtractor The ResultSetExtractor to use to translate the SQL result rows.
	 * @return A nullable Optional containing the item if it was found.
	 */
	protected Optional<T> findValueById(long id, String sql, ResultSetExtractor<List<T>> rsExtractor) {
		T value = null;

		CacheKey key = CacheKey.create(id);
		if (cache.contains(key)) {
			value = cache.get(key);
		} else {
			try {
				List<T> values = jdbcTemplate.query(sql, rsExtractor);
				if (values != null && !values.isEmpty()) value = values.get(0);
			} catch (EmptyResultDataAccessException e) {
				// Object not found, cache a null value.
			}
			cache.put(key, value);
		}
		return Optional.ofNullable(value);
	}
	
	/**
	 * Look up a List of values using their IDs.
	 * Only the items that are not cached will be queried in the DB.
	 * 
	 * @param ids The IDs of the items to find.
	 * @param sql The SQL query to use for the items that are not cached.
	 * @param rowMapper The RowMapper to use to translate the SQL result rows.
	 * @return A List of values that were found, possibly empty.
	 */
	protected List<T> findValuesByIds(long[] ids, String sql, RowMapper<T> rowMapper) {
		final List<T> values = new ArrayList<>();
		
		// Retrieve cached values first, if any.
		Arrays.stream(ids).filter(id -> cache.contains(CacheKey.create(id))).forEach(id -> {
			T value = cache.get(CacheKey.create(id));
			if (value != null) values.add(value);
		});

		// Query the remaining uncached values.
		List<Long> idsToQuery = Arrays.stream(ids)
				.filter(id -> !cache.contains(CacheKey.create(id)))
				.mapToObj(l -> l)
				.collect(Collectors.toList());
		
		if (!idsToQuery.isEmpty()) {
			sql = String.format(sql, String.join(",", Collections.nCopies(idsToQuery.size(), "?")));
			
			jdbcTemplate.query(sql, rowMapper, idsToQuery.toArray()).stream().forEach(value -> {
				cache.put(CacheKey.create(value.getId()), value);
				values.add(value);
			});
		}
		
		return values;
	}
	
	/**
	 * Look up a List of values using their IDs.
	 * Only the items that are not cached will be queried in the DB.
	 * 
	 * @param ids The IDs of the items to find.
	 * @param sql The SQL query to use for the items that are not cached.
	 * @param rsExtractor The ResultSetExtractor to use to translate the SQL result rows.
	 * @return A List of values that were found, possibly empty.
	 */
	protected List<T> findValuesByIds(long[] ids, String sql, ResultSetExtractor<List<T>> rsExtractor) {
		final List<T> values = new ArrayList<>();
		
		// Retrieve cached values first, if any.
		Arrays.stream(ids).filter(id -> cache.contains(CacheKey.create(id))).forEach(id -> {
			T value = cache.get(CacheKey.create(id));
			if (value != null) values.add(value);
		});

		// Query the remaining uncached values.
		List<Long> idsToQuery = Arrays.stream(ids)
				.filter(id -> !cache.contains(CacheKey.create(id)))
				.mapToObj(l -> l)
				.collect(Collectors.toList());
		
		if (!idsToQuery.isEmpty()) {
			sql = String.format(sql, String.join(",", Collections.nCopies(idsToQuery.size(), "?")));
			
			jdbcTemplate.query(sql, rsExtractor, idsToQuery.toArray()).stream().forEach(value -> {
				cache.put(CacheKey.create(value.getId()), value);
				values.add(value);
			});
		}
		
		return values;
	}
	
	/**
	 * Create a new value and immediately cache it. The generated ID will also be set on the value.
	 * 
	 * @param value The value to create in the DB.
	 * @param sql The SQL to insert the value in the DB.
	 * @param psSetter The PreparedStatementSetter to translate the value fields into the SQL insert statement.
	 * @return The generated ID for the new value.
	 */
	protected long createNewValue(T value, String sql, PreparedStatementSetter psSetter) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psSetter.setValues(ps);
			return ps;
		}, keyHolder);

		long id = (long) keyHolder.getKey();
		value.setId(id);
		
		cache.put(CacheKey.create(id), value);
		return id;
	}
	
	/**
	 * Update the fields of an existing value.
	 * 
	 * @param id The ID of the value to update.
	 * @param value The new fields for the value.
	 * @param sql The SQL to update the value in the DB.
	 * @param psSetter The PreparedStatementSetter to translate the value fields into the SQL update statement.
	 */
	protected void updateValue(long id, T value, String sql, PreparedStatementSetter psSetter) {
		value.setId(id);
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql);
			psSetter.setValues(ps);
			return ps;
		});
		cache.put(CacheKey.create(id), value);
	}

	/**
	 * Delete a value from the DB and the cache.
	 * 
	 * @param id The ID of the item to delete.
	 * @param sql The SQL to delete the item from the DB.
	 */
	protected void deleteValue(long id, String sql) {
		jdbcTemplate.execute(sql);
		cache.remove(CacheKey.create(id));
	}
}
