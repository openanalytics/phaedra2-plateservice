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

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/platetemplates")
public class PlateTemplateGraphQLController {

    private final PlateTemplateService plateTemplateService;
    private final MetadataServiceClient metadataServiceClient;

    public PlateTemplateGraphQLController(PlateTemplateService plateTemplateService, MetadataServiceClient metadataServiceClient) {
        this.plateTemplateService = plateTemplateService;
        this.metadataServiceClient = metadataServiceClient;
    }

    @QueryMapping
    List<PlateTemplateDTO> getPlateTemplates() {
        List<PlateTemplateDTO> result = plateTemplateService.getAllPlateTemplates();
        if (CollectionUtils.isNotEmpty(result)) {
            // Add tags
            result.stream().forEach(plateTemplateDTO -> {
                List<TagDTO> projectTags = metadataServiceClient.getTags(ObjectClass.PLATE_TEMPLATE, plateTemplateDTO.getId());
                plateTemplateDTO.setTags(projectTags.stream().map(TagDTO::getTag).collect(toList()));
            });
        }
        return result;
    }

    @QueryMapping
    PlateTemplateDTO getPlateTemplateById(@Argument Long plateTemplateId) {
        PlateTemplateDTO result = plateTemplateService.getPlateTemplateById(plateTemplateId);
        if (Objects.nonNull(result)) {
            // Add tags
            List<TagDTO> projectTags = metadataServiceClient.getTags(ObjectClass.PLATE_TEMPLATE, result.getId());
            result.setTags(projectTags.stream().map(TagDTO::getTag).collect(toList()));
        }
        return result;
    }
}
