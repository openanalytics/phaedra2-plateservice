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

import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import java.util.List;
import java.util.Set;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateRepository extends CustomPlateRepository, CrudRepository<Plate, Long> {

	@Query("delete from hca_plate p where p.experiment_id = :experimentId")
	void deleteByExperimentId(@Param("experimentId") long experimentId);

	@Query("delete from hca_plate p where p.experiment_id in"
			+ " (select id from hca_experiment where project_id = :projectId)")
	void deleteByProjectId(@Param("projectId") long projectId);

	@Query("select e.project_id from hca_plate p, hca_experiment e where p.experiment_id = e.id and p.id = :plateId")
	Long findProjectIdByPlateId(@Param("plateId") long plateId);

	@Query("select e.project_id from hca_experiment e where e.id = :experimentId")
	Long findProjectIdByExperimentId(@Param("experimentId") long experimentId);

	@Query("""
 			select experiment_id,
				count(id) as nr_plates,
				count(id) filter (where link_status = 'LINKED') as nr_plates_linked_layout,
				count(id) filter (where calculation_status = 'CALCULATION_OK') as nr_plates_calculated,
				count(id) filter (where validation_status = 'VALIDATED') as nr_plates_validated,
				count(id) filter (where approval_status = 'APPROVED') as nr_plates_approved
			from hca_plate
			group by experiment_id
		   """)
	List<ExperimentSummaryDTO> findExperimentSummaries();

	@Query("""
 			select experiment_id,
				count(id) as nr_plates,
				count(id) filter (where link_status = 'LINKED') as nr_plates_linked_layout,
				count(id) filter (where calculation_status = 'CALCULATION_OK') as nr_plates_calculated,
				count(id) filter (where validation_status = 'VALIDATED') as nr_plates_validated,
				count(id) filter (where approval_status = 'APPROVED') as nr_plates_approved
			from hca_plate
			where experiment_id in (:experimentIds)
			group by experiment_id
		   """)
	List<ExperimentSummaryDTO> findExperimentSummariesInExperimentIds(Set<Long> experimentIds);

	@Query("""
 			select experiment_id,
				count(id) as nr_plates,
				count(id) filter (where link_status = 'LINKED') as nr_plates_linked_layout,
				count(id) filter (where calculation_status = 'CALCULATION_OK') as nr_plates_calculated,
				count(id) filter (where validation_status = 'VALIDATED') as nr_plates_validated,
				count(id) filter (where approval_status = 'APPROVED') as nr_plates_approved
			from hca_plate
			where experiment_id = :experimentId
			group by experiment_id
		   """)
	ExperimentSummaryDTO findExperimentSummaryByExperimentId(Long experimentId);
}
