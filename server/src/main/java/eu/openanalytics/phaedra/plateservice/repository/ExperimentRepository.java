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

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperimentRepository extends CustomExperimentRepository, CrudRepository<Experiment, Long> {

//	@Query("select * from hca_experiment order by id desc")
//	List<Experiment> findAll();
//
//	@Query("select * from hca_experiment order by id desc limit :n")
//	List<Experiment> findNMostRecentExperiments(int n);
//
//	List<Experiment> findByProjectId(long projectId);
//
//	@Query("select * from hca_experiment e where e.project_id in (:projectIds)")
//	List<Experiment> findByProjectIds(Collection<Long> projectIds);

	@Query("delete from hca_experiment e where e.project_id = :projectId")
	void deleteByProjectId(@Param("projectId") long projectId);
}
