/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CustomExperimentRepositoryImpl implements CustomExperimentRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private static final String BASE_QUERY = "SELECT e.*, " +
      "pr.id as project_id, pr.name as project_name, pr.description as project_description " +
      "FROM hca_experiment e " +
      "JOIN hca_project pr ON e.project_id = pr.id ";

  public CustomExperimentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Experiment findById(long id) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE e.id = :id");

    return queryForObject(sql.toString(), buildParams("id", id));
  }

  @Override
  public List<Experiment> findAll() {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    return queryForList(sql.toString(), Map.of());
  }

  @Override
  public List<Experiment> findAllByIdIn(List<Long> ids) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE e.id in (:ids)");
    return queryForList(sql.toString(), buildParams("ids", ids));
  }

  @Override
  public List<Experiment> findNMostRecentExperiments(int n) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("order by id desc limit :n");
    return queryForList(sql.toString(), buildParams("n", n));
  }

  @Override
  public List<Experiment> findAllByProjectId(long projectId) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE pr.id = :projectId");
    return queryForList(sql.toString(), buildParams("projectId", projectId));
  }

  @Override
  public List<Experiment> findAllByProjectIdIn(Collection<Long> projectIds) {
    StringBuilder sql = new StringBuilder(BASE_QUERY);
    sql.append("WHERE pr.id in (:projectIds)");
    return queryForList(sql.toString(), buildParams("projectIds", projectIds));
  }

  private Map<String, Object> buildParams(String key, Object value) {
    Map<String, Object> params = new HashMap<>();
    params.put(key, value);
    return params;
  }

  private Experiment queryForObject(String sql, Map<String, Object> params) {
    try {
      return jdbcTemplate.queryForObject(sql, params, new ExperimentRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  private List<Experiment> queryForList(String sql, Map<String, Object> params) {
    return jdbcTemplate.query(sql, params, new ExperimentRowMapper());
  }
}
