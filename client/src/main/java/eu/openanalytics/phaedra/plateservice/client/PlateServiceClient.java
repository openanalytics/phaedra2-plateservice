/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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

import java.util.List;

import eu.openanalytics.phaedra.plateservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;

public interface PlateServiceClient {

    PlateDTO getPlate(long plateId) throws PlateUnresolvableException;

    List<WellDTO> getWells(long plateId) throws PlateUnresolvableException;

    PlateDTO updatePlateCalculationStatus(long plateId, CalculationStatus status, String details) throws PlateUnresolvableException;

    List<WellSubstanceDTO> getWellSubstances(long plateId) throws PlateUnresolvableException;

    List<PlateMeasurementDTO> getPlateMeasurements(long plateId, String... authToken) throws PlateUnresolvableException;

    List<ExperimentDTO> getExperiments(long projectId);

    List<PlateDTO> getPlatesByBarcode(String barcode);

    List<PlateDTO> getPlatesByExperiment(long experimentId);

    List<PlateTemplateDTO> getPlateTemplatesByName(String name);
}
