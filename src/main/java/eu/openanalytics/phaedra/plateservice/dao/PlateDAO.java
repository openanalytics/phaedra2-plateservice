package eu.openanalytics.phaedra.plateservice.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Compound;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.util.caching.CacheFactory;
import eu.openanalytics.phaedra.util.caching.model.ICache;
import eu.openanalytics.phaedra.util.jdbc.JDBCUtils;

@Repository
public class PlateDAO extends BaseCachingDAO<Plate> {

	private static final String SELECT_PLATE_COLUMNS = "pl.plate_id,"
			+ " pl.barcode, pl.description as plate_description, pl.plate_info,"
			+ " pl.plate_rows, pl.plate_columns, pl.sequence_in_run,"
			+ " pl.link_status, pl.link_user, pl.link_dt,"
			+ " pl.calc_status, pl.calc_error, pl.calc_dt,"
			+ " pl.validate_status, pl.validate_user, pl.validate_dt,"
			+ " pl.approve_status, pl.approve_user, pl.approve_dt,"
			+ " pl.upload_status, pl.upload_user, pl.upload_dt";
	
	private static final String SELECT_WELL_COLUMNS = "w.well_id,"
			+ " w.row_nr, w.col_nr,"
			+ " w.welltype_code, w.concentration, w.is_valid as well_status, w.platecompound_id, w.description as well_description";
	
	private static final String SELECT_COMPOUND_COLUMNS = "cmp.platecompound_id,"
			+ " cmp.compound_ty, cmp.compound_nr,"
			+ " cmp.description as compound_description, cmp.saltform";
	
	@Override
	protected ICache<Plate> initializeCache() {
		return CacheFactory.createCache("plate");
	}
	
	public Optional<Plate> getPlate(long id) {
		String sql = "select " + SELECT_PLATE_COLUMNS + ", " + SELECT_WELL_COLUMNS + ", " + SELECT_COMPOUND_COLUMNS
				+ " from phaedra.hca_plate pl"
				+ " left outer join phaedra.hca_plate_well w on pl.plate_id = w.plate_id"
				+ " left outer join phaedra.hca_plate_compound cmp on w.platecompound_id = cmp.platecompound_id"
				+ " where pl.plate_id = " + id;

		return findValueById(id, sql, new FullPlateResultSetExtractor());
	}
	
	public List<Plate> getPlates(long[] ids) {
		String sql = "select " + SELECT_PLATE_COLUMNS + ", " + SELECT_WELL_COLUMNS + ", " + SELECT_COMPOUND_COLUMNS
				+ " from phaedra.hca_plate pl"
				+ " left outer join phaedra.hca_plate_well w on pl.plate_id = w.plate_id"
				+ " left outer join phaedra.hca_plate_compound cmp on w.platecompound_id = cmp.platecompound_id"
				+ " where pl.plate_id in (%s)"
				+ " order by pl.plate_id asc";
		
		return findValuesByIds(ids, sql, new FullPlateResultSetExtractor());
	}
	
	public List<Plate> getPlatesForExperiment(long expId) {
		long[] plateIds = getJdbcTemplate().query("select plate_id from phaedra.hca_plate where experiment_id = ?", rs -> {
			List<Long> ids = new ArrayList<>();
			while (rs.next()) ids.add(rs.getLong(1));
			return ids;
		}, expId).stream().mapToLong(i -> i).toArray();

		return getPlates(plateIds);
	}
	
	public long createPlate(Plate plate, long experimentId) {
		if (plate.getBarcode() == null || plate.getBarcode().trim().isEmpty()) throw new IllegalArgumentException("Plate must have a non-empty barcode");
		if (plate.getRows() == 0 || plate.getColumns() == 0) throw new IllegalArgumentException("Plate rows and columns must be > 0");
		if (plate.getId() != 0) throw new IllegalArgumentException("New plates must not have an existing ID");
		if (experimentId <= 0) throw new IllegalArgumentException("Experiment ID is not valid");
		
		String sql = "insert into phaedra.hca_plate(plate_id, experiment_id,"
				+ " barcode, description, plate_info,"
				+ " plate_rows, plate_columns, sequence_in_run,"
				+ " link_status, link_user, link_dt,"
				+ " calc_status, calc_error, calc_dt,"
				+ " validate_status, validate_user, validate_dt,"
				+ " approve_status, approve_user, approve_dt,"
				+ " upload_status, upload_user, upload_dt"
				+ ") values (nextval('hca_plate_s'), " + experimentId + ", " + String.join(",", Collections.nCopies(21, "?")) + ")"
				+ " returning plate_id";
		
		long plateId = createNewValue(plate, sql, ps -> {
			ps.setString(1, plate.getBarcode());
			ps.setString(2, plate.getDescription());
			ps.setString(3, plate.getLinkInfo());
			ps.setInt(4, plate.getRows());
			ps.setInt(5, plate.getColumns());
			ps.setInt(6, plate.getSequence());

			ps.setInt(7, plate.getLinkStatus());
			ps.setString(8, plate.getLinkUser());
			ps.setTimestamp(9, JDBCUtils.toTimestamp(plate.getLinkDate()));
			
			ps.setInt(10, plate.getCalculationStatus());
			ps.setString(11, plate.getCalculationError());
			ps.setTimestamp(12, JDBCUtils.toTimestamp(plate.getCalculationDate()));
			
			ps.setInt(13, plate.getValidationStatus());
			ps.setString(14, plate.getValidationUser());
			ps.setTimestamp(15, JDBCUtils.toTimestamp(plate.getValidationDate()));
			
			ps.setInt(16, plate.getApprovalStatus());
			ps.setString(17, plate.getApprovalUser());
			ps.setTimestamp(18, JDBCUtils.toTimestamp(plate.getApprovalDate()));
			
			ps.setInt(19, plate.getUploadStatus());
			ps.setString(20, plate.getUploadUser());
			ps.setTimestamp(21, JDBCUtils.toTimestamp(plate.getUploadDate()));
		});
		
		// Insert Well rows
		
		final List<Well> wells = plate.getWells();
		
		String insertWellSQL = "insert into phaedra.hca_plate_well(well_id, plate_id,"
				+ " row_nr, col_nr, welltype_code, concentration, is_valid, description)"
				+ " values (nextval('hca_plate_well_s')," + plateId + ", " + String.join(",", Collections.nCopies(6, "?")) + ") returning well_id";

		long[] wellIds = getJdbcTemplate().execute(conn -> {
			PreparedStatement ps = conn.prepareStatement(insertWellSQL, Statement.RETURN_GENERATED_KEYS);
			for (int row = 1; row <= plate.getRows(); row++) {
				for (int col = 1; col <= plate.getColumns(); col++) {
					Well well = new Well();
					well.setRow(row);
					well.setColumn(col);
					well.setWellType("EMPTY");
					wells.add(well);

					ps.setInt(1, well.getRow());
					ps.setInt(2, well.getColumn());
					ps.setString(3, well.getWellType());
					ps.setDouble(4, well.getCompoundConcentration());
					ps.setInt(5, well.getStatus());
					ps.setString(6, well.getDescription());
					
					ps.addBatch();
				}
			}
			return ps;
		}, new PreparedStatementCallback<long[]>() {
			@Override
			public long[] doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.executeBatch();
				ResultSet rs = ps.getGeneratedKeys();
				List<Long> keys = new ArrayList<>();
				while(rs.next()) keys.add(rs.getLong(1));
				return keys.stream().mapToLong(l -> l).toArray();
			}
		});

		for (int i = 0; i < wellIds.length; i++) {
			wells.get(i).setId(wellIds[i]);
		}
		
		return plateId;
	}
	
	public void updatePlate(long plateId, Plate plate) {
		String sql = "update phaedra.hca_plate"
				+ " set barcode = ?, description = ?, plate_info = ?,"
				+ " sequence_in_run = ?,"
				+ " link_status, link_user, link_dt,"
				+ " calc_status, calc_error, calc_dt,"
				+ " validate_status, validate_user, validate_dt,"
				+ " approve_status, approve_user, approve_dt,"
				+ " upload_status, upload_user, upload_dt"
				+ " where plate_id = ?";
		
		updateValue(plateId, plate, sql, ps -> {
			ps.setString(1, plate.getBarcode());
			ps.setString(2, plate.getDescription());
			ps.setString(3, plate.getLinkInfo());
			ps.setInt(4, plate.getSequence());
			ps.setInt(5, plate.getLinkStatus());
			ps.setString(6, plate.getLinkUser());
			ps.setTimestamp(7, JDBCUtils.toTimestamp(plate.getLinkDate()));
			ps.setInt(8, plate.getCalculationStatus());
			ps.setString(9, plate.getCalculationError());
			ps.setTimestamp(10, JDBCUtils.toTimestamp(plate.getCalculationDate()));
			ps.setInt(11, plate.getValidationStatus());
			ps.setString(12, plate.getValidationUser());
			ps.setTimestamp(13, JDBCUtils.toTimestamp(plate.getValidationDate()));
			ps.setInt(14, plate.getApprovalStatus());
			ps.setString(15, plate.getApprovalUser());
			ps.setTimestamp(16, JDBCUtils.toTimestamp(plate.getApprovalDate()));
			ps.setInt(17, plate.getUploadStatus());
			ps.setString(18, plate.getUploadUser());
			ps.setTimestamp(19, JDBCUtils.toTimestamp(plate.getUploadDate()));
			ps.setLong(20, plateId);
		});
	}
	
	public void deletePlate(long plateId) {
		deleteValue(plateId, "delete from phaedra.hca_plate where plate_id = " + plateId);
	}
	
	private static class PlateRowMapper implements RowMapper<Plate> {
		@Override
		public Plate mapRow(ResultSet rs, int rowNum) throws SQLException {
			Plate plate = new Plate();
			plate.setId(rs.getLong("plate_id"));
			plate.setBarcode(rs.getString("barcode"));
			plate.setDescription(rs.getString("plate_description"));
			plate.setLinkInfo(rs.getString("plate_info"));
			plate.setRows(rs.getInt("plate_rows"));
			plate.setColumns(rs.getInt("plate_columns"));
			plate.setSequence(rs.getInt("sequence_in_run"));
			plate.setLinkStatus(rs.getInt("link_status"));
			plate.setLinkUser(rs.getString("link_user"));
			plate.setLinkDate(rs.getDate("link_dt"));
			plate.setCalculationStatus(rs.getInt("calc_status"));
			plate.setCalculationError(rs.getString("calc_error"));
			plate.setCalculationDate(rs.getDate("calc_dt"));
			plate.setValidationStatus(rs.getInt("validate_status"));
			plate.setValidationUser(rs.getString("validate_user"));
			plate.setValidationDate(rs.getDate("validate_dt"));
			plate.setApprovalStatus(rs.getInt("approve_status"));
			plate.setApprovalUser(rs.getString("approve_user"));
			plate.setApprovalDate(rs.getDate("approve_dt"));
			plate.setUploadStatus(rs.getInt("upload_status"));
			plate.setUploadUser(rs.getString("upload_user"));
			plate.setUploadDate(rs.getDate("upload_dt"));
			return plate;
		}
	}
	
	private static class WellRowMapper implements RowMapper<Well> {
		@Override
		public Well mapRow(ResultSet rs, int rowNum) throws SQLException {
			Well well = new Well();
			well.setId(rs.getLong("well_id"));
			well.setRow(rs.getInt("row_nr"));
			well.setColumn(rs.getInt("col_nr"));
			well.setWellType(rs.getString("welltype_code"));
			well.setCompoundConcentration(rs.getDouble("concentration"));
			well.setStatus(rs.getInt("well_status"));
			well.setDescription(rs.getString("well_description"));
			return well;
		}
	}
	
	private static class CompoundRowMapper implements RowMapper<Compound> {
		@Override
		public Compound mapRow(ResultSet rs, int rowNum) throws SQLException {
			Compound compound = new Compound();
			compound.setId(rs.getLong("platecompound_id"));
			compound.setType(rs.getString("compound_ty"));
			compound.setNumber(rs.getString("compound_nr"));
			compound.setDescription(rs.getString("compound_description"));
			compound.setSaltform(rs.getString("saltform"));
			return compound;
		}
	}
	
	private static class FullPlateResultSetExtractor implements ResultSetExtractor<List<Plate>> {
		@Override
		public List<Plate> extractData(ResultSet rs) throws SQLException, DataAccessException {
			PlateRowMapper plateRowMapper = new PlateRowMapper();
			WellRowMapper wellRowMapper = new WellRowMapper();
			CompoundRowMapper compRowMapper = new CompoundRowMapper();
			
			List<Plate> plates = new ArrayList<>();
			Plate currentPlate = null;
			
			int rowNum = 0;
			while (rs.next()) {
				Plate thisRowPlate = plateRowMapper.mapRow(rs, rowNum);
				if (currentPlate == null) {
					currentPlate = thisRowPlate;
				} else if (currentPlate.getId() != thisRowPlate.getId()) {
					plates.add(currentPlate);
					currentPlate = thisRowPlate;
				}
				Well well = wellRowMapper.mapRow(rs, rowNum);
				currentPlate.getWells().add(well);
				Compound compound = compRowMapper.mapRow(rs, rowNum);
				if (compound.getId() > 0) well.setCompound(compound);
				rowNum++;
			}
			if (currentPlate != null) plates.add(currentPlate);
			
			return plates;
		}
	}
}
