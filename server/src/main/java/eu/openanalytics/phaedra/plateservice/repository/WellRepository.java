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
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellRepository extends CrudRepository<Well, Long> {

//	@Query("delete from hca_well w where w.plate_id = :plateId")
//	void deleteByPlateId(@Param("plateId") long plateId);
//
//	@Query("delete from hca_well w where w.plate_id in"
//			+ " (select plate_id from hca_plate where experiment_id = :experimentId)")
//	void deleteByExperimentId(@Param("experimentId") long experimentId);
//
//	@Query("delete from hca_well w where w.plate_id in"
//			+ " (select p.plate_id from hca_plate p, hca_experiment e"
//			+ " where p.experiment_id = e.experiment_id and e.project_id = :projectId)")
//	void deleteByProjectId(@Param("projectId") long projectId);

	List<Well> findByPlateId(long plateId);
}
