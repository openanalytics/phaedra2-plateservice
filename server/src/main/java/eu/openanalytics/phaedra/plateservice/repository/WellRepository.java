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

	List<Well> findByPlateId(long plateId);

	@Query("select * from hca_well w where w.plate_id in (:plateIds)")
	List<Well> findByPlateIds(List<Long> plateIds);

	@Query("select * from hca_well w where w.plate_id in (select p.id from hca_plate p where p.experiment_id = :experimentId)")
	List<Well> findByExperimentId(Long experimentId);

	@Query("select * from hca_well w where w.plate_id in (select p.id from hca_plate p where p.experiment_id in (:experimentIds))")
	List<Well> findByExperimentIds(List<Long> experimentIds);

}
