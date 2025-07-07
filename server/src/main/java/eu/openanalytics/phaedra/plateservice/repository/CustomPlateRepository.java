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

import eu.openanalytics.phaedra.plateservice.model.Plate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomPlateRepository {

  Plate findById(long id);

  List<Plate> findAll();

  List<Plate> findAllByBarcode(String barcode);

  List<Plate> findAllByIdIn(List<Long> ids);

  List<Plate> findByExperimentId(long experimentId);

  List<Plate> findAllByBarcodeAndExperimentId(String barcode, long experimentId);

  List<Plate> findAllByExperimentIdIn(List<Long> experimentIds);

  List<Plate> findByProjectId(long projectId);

  List<Plate> findAllByProjectIdIn(List<Long> projectIds);
}
