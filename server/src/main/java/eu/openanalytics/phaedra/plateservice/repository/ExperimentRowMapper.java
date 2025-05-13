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

import static eu.openanalytics.phaedra.plateservice.util.StatusUtils.getStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import eu.openanalytics.phaedra.plateservice.enumeration.ExperimentStatus;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.record.ProjectProjection;

public class ExperimentRowMapper implements RowMapper<Experiment> {

  @Override
  public Experiment mapRow(ResultSet rs, int rowNum) throws SQLException {
    Experiment experiment = new Experiment();
    experiment.setId(rs.getLong("id"));
    experiment.setName(rs.getString("name"));
    experiment.setDescription(rs.getString("description"));
    experiment.setProjectId(rs.getLong("project_id"));
    experiment.setCreatedOn(rs.getDate("created_on"));
    experiment.setCreatedBy(rs.getString("created_by"));
    experiment.setUpdatedOn(rs.getDate("updated_on"));
    experiment.setUpdatedBy(rs.getString("updated_by"));
    experiment.setMultiploMethod(rs.getString("multiplo_method"));
    experiment.setMultiploParameter(rs.getString("multiplo_parameter"));

    experiment.setStatus(getStatus(rs.getString("status"), ExperimentStatus.class, ExperimentStatus.OPEN));

    ProjectProjection project = new ProjectProjection(
        rs.getLong("project_id"),
        rs.getString("project_name"),
        rs.getString("project_description")
    );
    experiment.setProject(project);

    return experiment;
  }
}
