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
package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlateGraphQLController {

    private final PlateService plateService;
    private final WellService wellService;
    private final PlateMeasurementService plateMeasurementService;
    private final MetadataServiceClient metadataServiceClient;

    public PlateGraphQLController(PlateService plateService, WellService wellService,
                                  PlateMeasurementService plateMeasurementService,
                                  MetadataServiceClient metadataServiceClient) {
        this.plateService = plateService;
        this.wellService = wellService;
        this.plateMeasurementService = plateMeasurementService;
        this.metadataServiceClient = metadataServiceClient;
    }

    @QueryMapping
    public List<PlateDTO> getPlatesByExperimentId(@Argument Long experimentId) {
        List<PlateDTO> result = ObjectUtils.isNotEmpty(experimentId) ? plateService.getPlatesByExperimentId(experimentId) : new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            result.forEach(plateDTO -> {
                addPlateMetadata(plateDTO);
            });
        }
        return result;
    }

    @QueryMapping
    public List<PlateDTO> getPlatesByBarcode(@Argument String barcode) {
        List<PlateDTO> result = StringUtils.isNotEmpty(barcode) ? plateService.getPlatesByBarcode(barcode) : new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            result.forEach(plateDTO -> {
                addPlateMetadata(plateDTO);
            });
        }
        return result;
    }

    @QueryMapping
    public PlateDTO getPlateById(@Argument Long plateId) {
        PlateDTO result = ObjectUtils.isNotEmpty(plateId) ? plateService.getPlateById(plateId) : null;
        if (result != null) {
            addPlateMetadata(result);
        }
        return result;
    }

    @QueryMapping
    public List<WellDTO> getPlateWells(@Argument Long plateId) {
        return ObjectUtils.isNotEmpty(plateId) ? wellService.getWellsByPlateId(plateId) : new ArrayList<>();
    }

    @QueryMapping
    public List<PlateMeasurementDTO> getMeasurementsByPlateId(@Argument Long plateId) {
        return plateMeasurementService.getPlateMeasurements(plateId);
    }

    @QueryMapping
    public PlateMeasurementDTO getActiveMeasurementByPlateId(@Argument Long plateId) {
        return plateMeasurementService.getActivePlateMeasurement(plateId);
    }

    @MutationMapping
    public PlateMeasurementDTO linkMeasurement(@Argument Long plateId, @Argument Long measurementId) {
        return plateMeasurementService.linkMeasurement(plateId, measurementId);
    }

    @QueryMapping
    public List<PlateMeasurementDTO> getActiveMeasurementsByExperimentId(@Argument Long experimentId) {
        return plateMeasurementService.getActivePlateMeasurementsByExperimentId(experimentId);
    }

    private void addPlateMetadata(PlateDTO plateDTO) {
        List<TagDTO> tags = metadataServiceClient.getTags(ObjectClass.PLATE, plateDTO.getId());
        plateDTO.setTags(tags.stream().map(tagDTO -> tagDTO.getTag()).toList());

        List<PropertyDTO> properties = metadataServiceClient.getPorperties(ObjectClass.PLATE, plateDTO.getId());
        plateDTO.setProperties(properties.stream().map(prop -> new eu.openanalytics.phaedra.plateservice.dto.PropertyDTO(prop.getPropertyName(), prop.getPropertyValue())).toList());
    }
}
