package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CustomPlateRepositoryImpl implements CustomPlateRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private static final String BASE_QUERY = "SELECT p.*, e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as experiment_project_id, " +
      "pr.id as project_id, pr.name as project_name, pr.description as project_description " +
      "FROM hca_plate p " +
      "JOIN hca_experiment e ON p.experiment_id = e.id " +
      "JOIN hca_project pr ON e.project_id = pr.id ";

  public CustomPlateRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Plate findById(long id) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE p.id = :id");

    return queryForObject(sql.toString(), buildParams("id", id));
  }

  @Override
  public List<Plate> findAll() {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    return queryForList(sql.toString(), Map.of());
  }

  @Override
  public List<Plate> findAllByBarcode(String barcode) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE p.barcode = :barcode");
    return queryForList(sql.toString(), buildParams("barcode", barcode));
  }

  @Override
  public List<Plate> findAllByIdIn(List<Long> ids) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE p.id in (:ids)");
    return queryForList(sql.toString(), buildParams("ids", ids));
  }

  @Override
  public List<Plate> findByExperimentId(long experimentId) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE e.id = :experimentId");

    return queryForList(sql.toString(), buildParams("experimentId", experimentId));
  }

  @Override
  public List<Plate> findAllByBarcodeAndExperimentId(String barcode, long experimentId) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE p.barcode = :barcode and e.id = :experimentId");

    Map<String, Object> params = buildParams("barcode", barcode);
    params.put("experimentId", experimentId);

    return queryForList(sql.toString(), params);
  }

  @Override
  public List<Plate> findAllByExperimentIdIn(List<Long> experimentIds) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE e.id IN (:experimentIds)");

    return queryForList(sql.toString(), buildParams("experimentIds", experimentIds));
  }

  @Override
  public List<Plate> findByProjectId(long projectId) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE pr.id = :projectId");

    return queryForList(sql.toString(), buildParams("projectId", projectId));
  }

  @Override
  public List<Plate> findAllByProjectIdIn(List<Long> projectIds) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE pr.id IN (:projectIds)");

    return queryForList(sql.toString(), buildParams("projectIds", projectIds));
  }

  private Map<String, Object> buildParams(String key, Object value) {
    Map<String, Object> params = new HashMap<>();
    params.put(key, value);
    return params;
  }

  private Plate queryForObject(String sql, Map<String, Object> params) {
    try {
      return jdbcTemplate.queryForObject(sql, params, new PlateRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  private List<Plate> queryForList(String sql, Map<String, Object> params) {
    return jdbcTemplate.query(sql, params, new PlateRowMapper());
  }
}
