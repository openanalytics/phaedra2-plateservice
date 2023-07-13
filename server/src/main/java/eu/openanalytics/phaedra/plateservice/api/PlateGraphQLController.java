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

import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlateGraphQLController {

    private final PlateMeasurementService plateMeasurementService;

    public PlateGraphQLController(PlateMeasurementService plateMeasurementService) {
        this.plateMeasurementService = plateMeasurementService;
    }

    @QueryMapping
    public List<PlateMeasurementDTO> plateMeasurements(@Argument Long plateId, @Argument boolean active) {
        if (active) {
            return plateMeasurementService.getPlateMeasurements(plateId);
        } else {
            return List.of(plateMeasurementService.getActivePlateMeasurement(plateId));
        }
    }
}
