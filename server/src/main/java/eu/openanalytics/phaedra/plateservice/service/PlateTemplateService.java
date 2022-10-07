/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.repository.PlateTemplateRepository;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class PlateTemplateService {
    private final ModelMapper modelMapper = new ModelMapper();

    private final PlateTemplateRepository plateTemplateRepository;

    private final WellTemplateService wellTemplateService;

	private final IAuthorizationService authService;

    public PlateTemplateService(PlateTemplateRepository plateTemplateRepository, @Lazy WellTemplateService wellTemplateService, IAuthorizationService authService) {
        this.plateTemplateRepository = plateTemplateRepository;
        this.wellTemplateService = wellTemplateService;
        this.authService = authService;

        // TODO move to dedicated ModelMapper service
        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());
        modelMapper.createTypeMap(PlateTemplate.class, PlateTemplateDTO.PlateTemplateDTOBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());
    }

    public PlateTemplateDTO createPlateTemplate(PlateTemplateDTO plateTemplateDTO) {
    	authService.performAccessCheck(p -> authService.hasUserAccess());
    	plateTemplateDTO.setCreatedBy(authService.getCurrentPrincipalName());
    	plateTemplateDTO.setCreatedOn(new Date());

        PlateTemplate plateTemplate = new PlateTemplate();
        modelMapper.typeMap(PlateTemplateDTO.class, PlateTemplate.class)
                .map(plateTemplateDTO, plateTemplate);
        plateTemplate = plateTemplateRepository.save(plateTemplate);
        wellTemplateService.createEmptyWellTemplates(plateTemplate);

        //Add wellTemplates
        if (CollectionUtils.isNotEmpty(plateTemplateDTO.getWells()))
            wellTemplateService.updateWellTemplates(plateTemplate, plateTemplateDTO.getWells());


        return mapToPlateTemplateDTO(plateTemplate);
    }

    public void updatePlateTemplate(PlateTemplateDTO plateTemplateDTO) {
    	authService.performOwnershipCheck(plateTemplateDTO.getCreatedBy());
    	plateTemplateDTO.setUpdatedBy(authService.getCurrentPrincipalName());
    	plateTemplateDTO.setUpdatedOn(new Date());

        Optional<PlateTemplate> plateTemplate = plateTemplateRepository.findById(plateTemplateDTO.getId());
        plateTemplate.ifPresent(p -> {
            modelMapper.typeMap(PlateTemplateDTO.class, PlateTemplate.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(plateTemplateDTO, p);
            plateTemplateRepository.save(p);
        });
    }

    public void deletePlateTemplate(long plateTemplateId) {
    	plateTemplateRepository.findById(plateTemplateId)
    		.map(PlateTemplate::getCreatedBy)
    		.ifPresent(creator -> authService.performOwnershipCheck(creator));

        plateTemplateRepository.deleteById(plateTemplateId);
    }

    public List<PlateTemplateDTO> getAllPlateTemplates() {
    	authService.performAccessCheck(p -> authService.hasUserAccess());

        List<PlateTemplate> plateTemplates = (List<PlateTemplate>) plateTemplateRepository.findAll();
        return plateTemplates.stream()
                .map(this::mapToPlateTemplateDTO)
                .collect(Collectors.toList());
    }

    public PlateTemplateDTO getPlateTemplateById(long plateTemplateId) {
    	authService.performAccessCheck(p -> authService.hasUserAccess());

        Optional<PlateTemplate> result = plateTemplateRepository.findById(plateTemplateId);
        return result.map(this::mapToPlateTemplateDTO).orElse(null);
    }

    private PlateTemplateDTO mapToPlateTemplateDTO(PlateTemplate plateTemplate) {
        var builder = modelMapper.map(plateTemplate, PlateTemplateDTO.PlateTemplateDTOBuilder.class);
        builder.wells(wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplate.getId()));
        return builder.build();
    }
}
