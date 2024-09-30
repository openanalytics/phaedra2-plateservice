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
package eu.openanalytics.phaedra.plateservice.client;

import eu.openanalytics.phaedra.plateservice.client.exception.UnresolvableObjectException;
import eu.openanalytics.phaedra.plateservice.dto.*;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;

import java.util.List;

public interface PlateServiceClient {

    List<ProjectDTO> getProjects();

    ExperimentDTO createExperiment(String name, long projectId);

    ExperimentDTO getExperiment(long experimentId) throws UnresolvableObjectException;

    List<ExperimentDTO> getExperiments();

    List<ExperimentDTO> getExperiments(long projectId);

    PlateDTO createPlate(String barcode, long experimentId, int rows, int columns);

    PlateDTO updatePlateCalculationStatus(long plateId, CalculationStatus status, String details) throws UnresolvableObjectException;

    PlateDTO getPlate(long plateId) throws UnresolvableObjectException;

    List<PlateDTO> getPlates();

    List<PlateDTO> getPlatesByBarcode(String barcode);

    List<PlateDTO> getPlatesByExperiment(long experimentId);

    List<PlateMeasurementDTO> getPlateMeasurements(long plateId, String... authToken) throws UnresolvableObjectException;

    List<PlateTemplateDTO> getPlateTemplatesByName(String name);

    List<WellDTO> getWells(long plateId) throws UnresolvableObjectException;

    List<WellDTO> getNWells(int n) throws UnresolvableObjectException;

    List<WellSubstanceDTO> getWellSubstances(long plateId) throws UnresolvableObjectException;
}
