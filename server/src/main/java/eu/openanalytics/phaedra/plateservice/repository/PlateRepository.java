/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.model.Plate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateRepository extends CrudRepository<Plate, Long> {

	@Query("delete from hca_plate p where p.experiment_id = :experimentId")
	void deleteByExperimentId(@Param("experimentId") long experimentId);

	@Query("delete from hca_plate p where p.experiment_id in"
			+ " (select id from hca_experiment where project_id = :projectId)")
	void deleteByProjectId(@Param("projectId") long projectId);

	@Query("select e.project_id from hca_plate p, hca_experiment e where p.experiment_id = e.id and p.id = :plateId")
	Long findProjectIdByPlateId(@Param("plateId") long plateId);

	List<Plate> findByExperimentId(long experimentId);

	List<Plate> findByBarcode(String barcoce);
}
