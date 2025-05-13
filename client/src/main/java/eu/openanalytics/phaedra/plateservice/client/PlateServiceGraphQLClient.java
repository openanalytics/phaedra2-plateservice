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
package eu.openanalytics.phaedra.plateservice.client;

import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import java.util.List;

public interface PlateServiceGraphQLClient {
  public WellDTO getWell(long wellId);
  public List<WellDTO> getWells(List<Long> wellIds);
  public List<WellDTO> getWellsByPlateId(long plateId);
  public List<WellDTO> getWellsByPlateIds(List<Long> plateIds);
  public List<WellDTO> getWellsByExperimentId(long experimentId);
  public List<WellDTO> getWellsByExperimentIds(List<Long> experimentIds);

  public List<PlateMeasurementDTO> getActivePlateMeasurements(List<Long> plateIds);
}
