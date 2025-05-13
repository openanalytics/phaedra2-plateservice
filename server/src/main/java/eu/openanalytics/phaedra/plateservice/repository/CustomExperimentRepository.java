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
import java.util.Collection;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomExperimentRepository {

  Experiment findById(long id);
  List<Experiment> findAll();
  List<Experiment> findAllByIdIn(List<Long> ids);
  List<Experiment> findNMostRecentExperiments(int n);
  List<Experiment> findAllByProjectId(long projectId);
  List<Experiment> findAllByProjectIdIn(Collection<Long> projectIds);

}
