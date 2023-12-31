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
package eu.openanalytics.phaedra.plateservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.repository.PlateTemplateRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class WellTemplateService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Comparator<WellTemplateDTO> WELL_TEMPLATE_DTO_COMPARATOR = Comparator.comparing(WellTemplateDTO::getRow).thenComparing(WellTemplateDTO::getColumn);

    private final PlateTemplateRepository plateTemplateRepository;
    private final WellTemplateRepository wellTemplateRepository;

	private final IAuthorizationService authService;

    public WellTemplateService(WellTemplateRepository wellTemplateRepository, PlateTemplateRepository plateTemplateRepository, IAuthorizationService authService) {
    	this.wellTemplateRepository = wellTemplateRepository;
    	this.plateTemplateRepository = plateTemplateRepository;
        this.authService = authService;
    }

    public WellTemplateDTO createWellTemplate(WellTemplateDTO wellTemplateDTO) {
    	plateTemplateRepository.findById(wellTemplateDTO.getPlateTemplateId())
    			.ifPresent(wellTemplate -> authService.performOwnershipCheck(wellTemplate.getCreatedBy()));

        WellTemplate wellTemplate = new WellTemplate(wellTemplateDTO.getPlateTemplateId());
        modelMapper.typeMap(WellTemplateDTO.class, WellTemplate.class)
                .map(wellTemplateDTO, wellTemplate);
        wellTemplate = wellTemplateRepository.save(wellTemplate);
        return mapToWellTemplateDTO(wellTemplate);
    }

    public List<WellTemplateDTO> createWellTemplates(PlateTemplate plateTemplate, List<WellTemplateDTO> wellTemplateDTOs) {
        authService.performOwnershipCheck(plateTemplate.getCreatedBy());

//        .map(well -> modelMapper.map(well, WellDTO.class))
//                .map(dto -> {
//                    dto.setWellSubstance(substances.stream().filter(s -> s.getWellId().longValue() == dto.getId().longValue()).findAny().orElse(null));
//                    return dto;
//                })
//                .sorted(WELL_COMPARATOR)
//                .toList();

        List<WellTemplate> wellTemplates = wellTemplateDTOs.stream().map(wellDTO -> modelMapper.map(wellDTO, WellTemplate.class))
                .map(well -> {
                    well.setPlateTemplateId(plateTemplate.getId());
                    return well;
                }).toList();

        wellTemplateRepository.saveAll(wellTemplates);
        return wellTemplates.stream().map(this::mapToWellTemplateDTO).collect(Collectors.toList());
    }
    public List<WellTemplateDTO> createEmptyWellTemplates(PlateTemplate plateTemplate) {
    	authService.performOwnershipCheck(plateTemplate.getCreatedBy());

        List<WellTemplate> wellTemplates = new ArrayList<>(plateTemplate.getRows()*plateTemplate.getColumns());
        for (int r = 1; r <= plateTemplate.getRows(); r++) {
            for (int c = 1; c <= plateTemplate.getColumns(); c++) {
                WellTemplate wellTemplate = new WellTemplate(plateTemplate.getId());
                wellTemplate.setRow(r);
                wellTemplate.setColumn(c);
                wellTemplate.setSkipped(false);
                wellTemplate.setWellType("EMPTY");
                wellTemplates.add(wellTemplate);
            }
        }
        wellTemplateRepository.saveAll(wellTemplates);
        return wellTemplates.stream().map(this::mapToWellTemplateDTO).collect(Collectors.toList());
    }

    public void updateWellTemplate(WellTemplateDTO wellTemplateDTO) {
        plateTemplateRepository.findById(wellTemplateDTO.getPlateTemplateId())
                .ifPresent(wellTemplate -> authService.performOwnershipCheck(wellTemplate.getCreatedBy()));

        Optional<WellTemplate> wellTemplate = wellTemplateRepository.findById(wellTemplateDTO.getId());
        wellTemplate.ifPresent( w -> {
            modelMapper.typeMap(WellTemplateDTO.class,WellTemplate.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(wellTemplateDTO, w);
            wellTemplateRepository.save(w);
        });
    }

    public WellTemplateDTO getWellTemplateById(long wellTemplateId) {
    	authService.performAccessCheck(p -> authService.hasUserAccess());

        Optional<WellTemplate> result = wellTemplateRepository.findById(wellTemplateId);
        return result.map(this::mapToWellTemplateDTO).orElse(null);
    }

    public List<WellTemplateDTO> getWellTemplatesByPlateTemplateId(long plateTemplateId) {
    	authService.performAccessCheck(p -> authService.hasUserAccess());

        List<WellTemplate> result = wellTemplateRepository.findByPlateTemplateId(plateTemplateId);
        return result.stream().map(this::mapToWellTemplateDTO).sorted(WELL_TEMPLATE_DTO_COMPARATOR).toList();
    }


    private WellTemplateDTO mapToWellTemplateDTO(WellTemplate wellTemplate) {
        WellTemplateDTO wellTemplateDTO = new WellTemplateDTO();
        modelMapper.typeMap(WellTemplate.class, WellTemplateDTO.class)
                .map(wellTemplate, wellTemplateDTO);
        return wellTemplateDTO;
    }

    public void updateWellTemplates(List<WellTemplateDTO> wellTemplates) {
        wellTemplates.stream().forEach(wtDTO -> {
            WellTemplate wellTemplate = wellTemplateRepository.findWellTemplateByPlateTemplateIdAndRowAndColumn(wtDTO.getPlateTemplateId(), wtDTO.getRow(), wtDTO.getColumn());
            if (wellTemplate != null) {
                wtDTO.setId(wellTemplate.getId());
                wtDTO.setPlateTemplateId(wellTemplate.getPlateTemplateId());
                updateWellTemplate(wtDTO);
            }
        });
    }

    public void updateWellTemplates(PlateTemplate plateTemplate, List<WellTemplateDTO> wells) {
        wells.stream().forEach(wtDTO -> {
            WellTemplate wellTemplate = wellTemplateRepository.findWellTemplateByPlateTemplateIdAndRowAndColumn(plateTemplate.getId(), wtDTO.getRow(), wtDTO.getColumn());
            if (wellTemplate != null) {
                wtDTO.setId(wellTemplate.getId());
                wtDTO.setPlateTemplateId(wellTemplate.getPlateTemplateId());
                updateWellTemplate(wtDTO);
            }
        });
    }
}
