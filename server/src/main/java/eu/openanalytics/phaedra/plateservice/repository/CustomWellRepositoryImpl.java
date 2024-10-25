package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Well;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CustomWellRepositoryImpl implements CustomWellRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private static final String BASE_QUERY = "SELECT w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
      "p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns, " +
      "e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as experiment_project_id, " +
      "pr.id as project_id, pr.name as project_name, pr.description as project_description " +
      "FROM hca_well w " +
      "JOIN hca_plate p ON w.plate_id = p.id " +
      "JOIN hca_experiment e ON p.experiment_id = e.id " +
      "JOIN hca_project pr ON e.project_id = pr.id ";

  public CustomWellRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Well findById(long id) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE w.id = :id");

    return queryForObject(sql.toString(), buildParams("id", id));
  }

  @Override
  public List<Well> findAllByIdIn(List<Long> ids) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE w.id IN (:ids)");

    return queryForList(sql.toString(), buildParams("ids", ids));
  }

  @Override
  public List<Well> findAllByPlateId(long plateId) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE p.id = :plateId");

    return queryForList(sql.toString(), buildParams("plateId", plateId));
  }

  @Override
  public List<Well> findAllByPlateIdIn(List<Long> plateIds) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE p.id IN (:plateIds)");

    return queryForList(sql.toString(), buildParams("plateIds", plateIds));
  }

  @Override
  public List<Well> findAllByExperimentId(long experimentId) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE e.id = :experimentId");

    return queryForList(sql.toString(), buildParams("experimentId", experimentId));
  }

  @Override
  public List<Well> findAllByExperimentIdIn(List<Long> experimentIds) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE e.id IN (:experimentIds)");

    return queryForList(sql.toString(), buildParams("experimentIds", experimentIds));
  }

  private Map<String, Object> buildParams(String key, Object value) {
    Map<String, Object> params = new HashMap<>();
    params.put(key, value);
    return params;
  }

  private Well queryForObject(String sql, Map<String, Object> params) {
    return jdbcTemplate.queryForObject(sql, params, new WellRowMapper());
  }

  private List<Well> queryForList(String sql, Map<String, Object> params) {
    return jdbcTemplate.query(sql, params, new WellRowMapper());
  }
}
