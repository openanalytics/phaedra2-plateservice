/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.model.Well;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellRepository extends CrudRepository<Well, Long> {

	@Query("select w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
			"p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns" +
			"e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as project_id, " +
			"pr.id as project_id, pr.name as project_name, pr.description as project_description " +
			" from hca_well w " +
			" join hca_plate p on w.plate_id = p.id " +
			" join hca_experiment e on p.experiment_id = e.id " +
			" join hca_project pr on e.project_id = pr.id " +
			" where w.id = :id")
	Well findById(long id);

	@Query("select w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
			"p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns" +
			"e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as project_id, " +
			"pr.id as project_id, pr.name as project_name, pr.description as project_description " +
			" from hca_well w " +
			" join hca_plate p on w.plate_id = p.id " +
			" join hca_experiment e on p.experiment_id = e.id " +
			" join hca_project pr on e.project_id = pr.id " +
			" where w.id in (:ids)")
	List<Well> findAllByIdIn(List<Long> ids);

	@Query("select w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
			"p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns" +
			"e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as project_id, " +
			"pr.id as project_id, pr.name as project_name, pr.description as project_description " +
			" from hca_well w " +
			" join hca_plate p on w.plate_id = p.id " +
			" join hca_experiment e on p.experiment_id = e.id " +
			" join hca_project pr on e.project_id = pr.id " +
			" where p.id = :plateId")
	List<Well> findAllByPlateId(long plateId);

//	@Query("select * from hca_well w where w.plate_id in (:plateIds)")
	@Query("select w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
			"p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns" +
			"e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as project_id, " +
			"pr.id as project_id, pr.name as project_name, pr.description as project_description " +
			" from hca_well w " +
			" join hca_plate p on w.plate_id = p.id " +
			" join hca_experiment e on p.experiment_id = e.id " +
			" join hca_project pr on e.project_id = pr.id " +
			" where p.id in (:plateIds)")
	List<Well> findAllByPlateIdIn(List<Long> plateIds);

//	@Query("select * from hca_well w where w.plate_id in (select p.id from hca_plate p where p.experiment_id = :experimentId)")
	@Query("select w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
			"p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns" +
			"e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as project_id, " +
			"pr.id as project_id, pr.name as project_name, pr.description as project_description " +
			" from hca_well w " +
			" join hca_plate p on w.plate_id = p.id " +
			" join hca_experiment e on p.experiment_id = e.id " +
			" join hca_project pr on e.project_id = pr.id " +
			" where e.id = :experimentId")
	List<Well> findAllByExperimentId(long experimentId);

//	@Query("select * from hca_well w where w.plate_id in (select p.id from hca_plate p where p.experiment_id in (:experimentIds))")
	@Query("select w.id, w.plate_id, w.row, w.column, w.welltype, w.status, w.compound_id, w.description, " +
			"p.id as plate_id, p.barcode as plate_barcode, p.description as plate_description, p.experiment_id as plate_experiment_id, p.rows as plate_rows, p.columns as plate_columns" +
			"e.id as experiment_id, e.name as experiment_name, e.description as experiment_description, e.project_id as project_id, " +
			"pr.id as project_id, pr.name as project_name, pr.description as project_description " +
			" from hca_well w " +
			" join hca_plate p on w.plate_id = p.id " +
			" join hca_experiment e on p.experiment_id = e.id " +
			" join hca_project pr on e.project_id = pr.id " +
			" where e.id in (:experimentIds)")
	List<Well> findAllByExperimentIdIn(List<Long> experimentIds);
}


