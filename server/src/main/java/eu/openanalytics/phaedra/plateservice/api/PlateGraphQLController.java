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
package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlateGraphQLController {

    private final PlateService plateService;
    private final WellService wellService;
    private final PlateMeasurementService plateMeasurementService;

    public PlateGraphQLController(PlateService plateService, WellService wellService, PlateMeasurementService plateMeasurementService) {
        this.plateService = plateService;
        this.wellService = wellService;
        this.plateMeasurementService = plateMeasurementService;
    }

    @QueryMapping
    public List<PlateDTO> getPlatesByExperimentId(@Argument long experimentId) {
        return plateService.getPlatesByExperimentId(experimentId);
    }

    @QueryMapping
    public List<PlateDTO> getPlatesByBarcode(@Argument String barcode) {
        return plateService.getPlatesByBarcode(barcode);
    }

    @QueryMapping
    public PlateDTO getPlateById(@Argument long plateId) {
        return plateService.getPlateById(plateId);
    }

    @QueryMapping
    public List<WellDTO> getPlateWells(@Argument long plateId) {
        return wellService.getWellsByPlateId(plateId);
    }

    @QueryMapping
    public List<PlateMeasurementDTO> getPlateMeasurements(@Argument long plateId, @Argument boolean active) {
        if (active) {
            return plateMeasurementService.getPlateMeasurements(plateId);
        } else {
            return List.of(plateMeasurementService.getActivePlateMeasurement(plateId));
        }
    }
}
