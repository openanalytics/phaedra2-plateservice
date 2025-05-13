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
package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/platetemplates")
public class PlateTemplateGraphQLController {

    private final PlateTemplateService plateTemplateService;
    private final WellTemplateService wellTemplateService;
    private final MetadataServiceClient metadataServiceClient;

    public PlateTemplateGraphQLController(PlateTemplateService plateTemplateService, WellTemplateService wellTemplateService, MetadataServiceClient metadataServiceClient) {
        this.plateTemplateService = plateTemplateService;
        this.wellTemplateService = wellTemplateService;
        this.metadataServiceClient = metadataServiceClient;
    }

    @QueryMapping
    List<PlateTemplateDTO> getPlateTemplates() {
        List<PlateTemplateDTO> result = plateTemplateService.getAllPlateTemplates();
        if (CollectionUtils.isNotEmpty(result)) {
            result.stream().forEach(plateTemplateDTO -> {
                addPlateTemplateMetadata(plateTemplateDTO);
            });
        }
        return result;
    }

    @QueryMapping
    PlateTemplateDTO getPlateTemplateById(@Argument Long plateTemplateId) {
        PlateTemplateDTO result = plateTemplateService.getPlateTemplateById(plateTemplateId);
        if (Objects.nonNull(result)) {
            // Add wells
            List<WellTemplateDTO> wells = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);
            result.setWells(wells);

            addPlateTemplateMetadata(result);
        }
        return result;
    }

    @QueryMapping
    List<WellTemplateDTO> getWellTemplatesByPlateTemplateId(@Argument Long plateTemplateId) {
        List<WellTemplateDTO> result = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);
        return result;
    }

    @MutationMapping
    PlateTemplateDTO updatePlateTemplate(@Argument Long plateTemplateId, PlateTemplateDTO plateTemplate) {
        plateTemplateService.updatePlateTemplate(plateTemplate);
        return plateTemplate;
    }

    private void addPlateTemplateMetadata(PlateTemplateDTO plateTemplateDTO) {
        List<TagDTO> tags = metadataServiceClient.getTags(ObjectClass.PLATE_TEMPLATE.name(), plateTemplateDTO.getId());
        plateTemplateDTO.setTags(tags.stream().map(tagDTO -> tagDTO.getTag()).toList());

        List<PropertyDTO> properties = metadataServiceClient.getProperties(ObjectClass.PLATE_TEMPLATE.name(), plateTemplateDTO.getId());
        plateTemplateDTO.setProperties(properties.stream().map(prop -> new eu.openanalytics.phaedra.plateservice.dto.PropertyDTO(prop.getPropertyName(), prop.getPropertyValue())).toList());
    }
}
