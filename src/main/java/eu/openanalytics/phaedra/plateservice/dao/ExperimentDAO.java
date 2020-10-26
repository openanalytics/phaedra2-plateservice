package eu.openanalytics.phaedra.plateservice.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.util.caching.CacheFactory;
import eu.openanalytics.phaedra.util.caching.model.ICache;
import eu.openanalytics.phaedra.util.jdbc.JDBCUtils;

@Repository
public class ExperimentDAO extends BaseCachingDAO<Experiment> {

	@Override
	protected ICache<Experiment> initializeCache() {
		return CacheFactory.createCache("experiment");
	}
	
	public Optional<Experiment> getExperiment(long id) {
		String sql = "select exp.experiment_id, exp.experiment_name,"
				+ " exp.experiment_dt, exp.experiment_user, exp.description as exp_description,"
				+ " exp.closed, exp.comments, exp.multiplo_method, exp.multiplo_parameter"
				+ " from phaedra.hca_experiment exp where exp.experiment_id = " + id;
		
		return findValueById(id, sql, (rs, rownum) -> {
			Experiment exp = new Experiment();
			exp.setId(rs.getLong("experiment_id"));
			exp.setName(rs.getString("experiment_name"));
			exp.setCreatedOn(rs.getDate("experiment_dt"));
			exp.setCreatedBy(rs.getString("experiment_user"));
			exp.setDescription(rs.getString("exp_description"));
			exp.setClosed(rs.getBoolean("closed"));
			exp.setComments(rs.getString("comments"));
			exp.setMultiploMethod(rs.getString("multiplo_method"));
			exp.setMultiploParameter(rs.getString("multiplo_parameter"));
			return exp;
		});
	}
	
	public List<Experiment> getExperiments(long[] ids) {
		String sql = "select exp.experiment_id, exp.experiment_name,"
				+ " exp.experiment_dt, exp.experiment_user, exp.description as exp_description,"
				+ " exp.closed, exp.comments, exp.multiplo_method, exp.multiplo_parameter"
				+ " from phaedra.hca_experiment exp where exp.experiment_id in (%s)";
		
		return findValuesByIds(ids, sql, (rs, rownum) -> {
			Experiment exp = new Experiment();
			exp.setId(rs.getLong("experiment_id"));
			exp.setName(rs.getString("experiment_name"));
			exp.setCreatedOn(rs.getDate("experiment_dt"));
			exp.setCreatedBy(rs.getString("experiment_user"));
			exp.setDescription(rs.getString("exp_description"));
			exp.setClosed(rs.getBoolean("closed"));
			exp.setComments(rs.getString("comments"));
			exp.setMultiploMethod(rs.getString("multiplo_method"));
			exp.setMultiploParameter(rs.getString("multiplo_parameter"));
			return exp;
		});
	}
	
	public List<Experiment> getExperimentsForProject(long projectId) {
		long[] expIds = getJdbcTemplate().query("select experiment_id from phaedra.hca_project_experiment where project_id = ?", rs -> {
			List<Long> ids = new ArrayList<>();
			while (rs.next()) ids.add(rs.getLong(1));
			return ids;
		}, projectId).stream().mapToLong(i -> i).toArray();
		
		return getExperiments(expIds);
	}
	
	public long createExperiment(Experiment experiment, long projectId) {
		if (experiment.getName() == null || experiment.getName().trim().isEmpty()) throw new IllegalArgumentException("Experiment must have a non-empty name");
		if (experiment.getId() != 0) throw new IllegalArgumentException("New experiments must not have an existing ID");
		if (projectId <= 0) throw new IllegalArgumentException("Project ID is not valid");
		
		String sql = "insert into phaedra.hca_experiment(experiment_id, experiment_name, description,"
				+ " experiment_user, experiment_dt,"
				+ " closed, comments,"
				+ " multiplo_method, multiplo_parameter"
				+ ") values (nextval('hca_experiment_s'), " + String.join(",", Collections.nCopies(8, "?")) + ")"
				+ " returning experiment_id";
		
		long experimentId = createNewValue(experiment, sql, ps -> {
			ps.setString(1, experiment.getName());
			ps.setString(2, experiment.getDescription());
			ps.setString(3, experiment.getCreatedBy());
			ps.setTimestamp(4, JDBCUtils.toTimestamp(experiment.getCreatedOn()));
			ps.setBoolean(5, experiment.isClosed());
			ps.setString(6, experiment.getComments());
			ps.setString(7, experiment.getMultiploMethod());
			ps.setString(8, experiment.getMultiploParameter());
		});

		getJdbcTemplate().execute("insert into phaedra.hca_project_experiment(project_id, experiment_id)"
				+ " values (" + projectId + ", " + experimentId + ")");
		
		return experimentId;
	}
	
	public void updateExperiment(long experimentId, Experiment experiment) {
		String sql = "update phaedra.hca_experiment"
				+ " set experiment_name = ?, description = ?, closed = ?, comments = ?, multiplo_method = ?, multiplo_parameter = ?"
				+ " where experiment_id = ?";
		
		updateValue(experimentId, experiment, sql, ps -> {
			ps.setString(1, experiment.getName());
			ps.setString(2, experiment.getDescription());
			ps.setBoolean(3, experiment.isClosed());
			ps.setString(4, experiment.getComments());
			ps.setString(5, experiment.getMultiploMethod());
			ps.setString(6, experiment.getMultiploParameter());
			ps.setLong(7, experimentId);
		});
	}

	public void deleteExperiment(long experimentId) {
		deleteValue(experimentId, "delete from phaedra.hca_experiment where experiment_id = " + experimentId);
	}
}
