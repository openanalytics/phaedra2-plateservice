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

import static eu.openanalytics.phaedra.plateservice.enumeration.WellStatus.*;
import static eu.openanalytics.phaedra.plateservice.util.StatusUtils.getStatus;

import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import eu.openanalytics.phaedra.plateservice.record.ExperimentProjection;
import eu.openanalytics.phaedra.plateservice.record.PlateProjection;
import eu.openanalytics.phaedra.plateservice.record.ProjectProjection;
import eu.openanalytics.phaedra.plateservice.model.Well;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class WellRowMapper implements RowMapper<Well> {
  @Override
  public Well mapRow(ResultSet rs, int rowNum) throws SQLException {
    Well well = new Well();
    well.setId(rs.getLong("id"));
    well.setPlateId(rs.getLong("plate_id"));
    well.setRow(rs.getInt("row"));
    well.setColumn(rs.getInt("column"));
    well.setStatus(getStatus(rs.getString("status"), WellStatus.class, ACCEPTED_DEFAULT));
    well.setCompoundId(rs.getLong("compound_id"));
    well.setDescription(rs.getString("description"));
    well.setWellType(rs.getString("welltype"));

    PlateProjection plate = new PlateProjection(
        rs.getLong("plate_id"),
        rs.getString("plate_barcode"),
        rs.getString("plate_description"),
        rs.getInt("plate_experiment_id"),
        rs.getInt("plate_rows"),
        rs.getInt("plate_columns")
    );
    well.setPlate(plate);

    ExperimentProjection experiment = new ExperimentProjection(
        rs.getLong("experiment_id"),
        rs.getString("experiment_name"),
        rs.getString("experiment_description"),
        rs.getLong("experiment_project_id"),
        rs.getString("experiment_status")
    );
    well.setExperiment(experiment);

    ProjectProjection project = new ProjectProjection(
        rs.getLong("project_id"),
        rs.getString("project_name"),
        rs.getString("project_description")
    );
    well.setProject(project);

    return well;
  }
}
