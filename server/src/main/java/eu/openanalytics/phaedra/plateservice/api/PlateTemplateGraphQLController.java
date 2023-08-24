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

import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platetemplates")
public class PlateTemplateGraphQLController {

    private final PlateTemplateService plateTemplateService;

    public PlateTemplateGraphQLController(PlateTemplateService plateTemplateService) {
        this.plateTemplateService = plateTemplateService;
    }

    @QueryMapping
    List<PlateTemplateDTO> getPlateTemplates() {
        return plateTemplateService.getAllPlateTemplates();
    }

    @QueryMapping
    PlateTemplateDTO getPlateTemplateById(@Argument Long plateTemplateId) {
        return plateTemplateService.getPlateTemplateById(plateTemplateId);
    }
}
