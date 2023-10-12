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
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlateGraphQLController {

    private final PlateService plateService;
    private final WellService wellService;
    private final PlateMeasurementService plateMeasurementService;
    private final MetadataServiceClient metadataServiceClient;
    private final IAuthorizationService authService;

    public PlateGraphQLController(PlateService plateService, WellService wellService,
                                  PlateMeasurementService plateMeasurementService,
                                  MetadataServiceClient metadataServiceClient, IAuthorizationService authService) {
        this.plateService = plateService;
        this.wellService = wellService;
        this.plateMeasurementService = plateMeasurementService;
        this.metadataServiceClient = metadataServiceClient;
        this.authService = authService;
    }

    @QueryMapping
    public List<PlateDTO> getPlatesByExperimentId(@Argument Long experimentId) {
        List<PlateDTO> result = ObjectUtils.isNotEmpty(experimentId) ? plateService.getPlatesByExperimentId(experimentId) : new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            // Add tags
            result.stream().forEach(plateDTO -> {
                List<TagDTO> plateTags = metadataServiceClient.getTags(ObjectClass.PLATE, plateDTO.getId());
                plateDTO.setTags(plateTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));
            });
        }
        return result;
    }

    @QueryMapping
    public List<PlateDTO> getPlatesByBarcode(@Argument String barcode) {
        List<PlateDTO> result = StringUtils.isNotEmpty(barcode) ? plateService.getPlatesByBarcode(barcode) : new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            // Add tags
            result.stream().forEach(plateDTO -> {
                List<TagDTO> plateTags = metadataServiceClient.getTags(ObjectClass.PLATE, plateDTO.getId());
                plateDTO.setTags(plateTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));
            });
        }
        return result;
    }

    @QueryMapping
    public PlateDTO getPlateById(@Argument Long plateId) {
        PlateDTO result = ObjectUtils.isNotEmpty(plateId) ? plateService.getPlateById(plateId) : null;
        if (result != null) {
            List<TagDTO> plateTags = metadataServiceClient.getTags(ObjectClass.PLATE, result.getId());
            result.setTags(plateTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));
        }
        return result;
    }

    @QueryMapping
    public List<WellDTO> getPlateWells(@Argument Long plateId) {
        List<WellDTO> result = ObjectUtils.isNotEmpty(plateId) ? wellService.getWellsByPlateId(plateId) : new ArrayList<>();
        return result;
    }

    @QueryMapping
    public List<PlateMeasurementDTO> getMeasurementsByPlateId(@Argument Long plateId) {
        List<PlateMeasurementDTO> result = plateMeasurementService.getPlateMeasurements(plateId);
        return result;
    }

    @QueryMapping
    public PlateMeasurementDTO getActiveMeasurementByPlateId(@Argument Long plateId) {
        PlateMeasurementDTO result = plateMeasurementService.getActivePlateMeasurement(plateId);
        return result;
    }

    @MutationMapping
    public PlateMeasurementDTO linkMeasurement(@Argument Long plateId, @Argument Long measurementId) {
        return plateMeasurementService.linkMeasurement(plateId, measurementId);
    }
}
