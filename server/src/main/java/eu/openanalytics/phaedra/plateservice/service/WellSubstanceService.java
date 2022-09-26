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

import java.util.List;
import java.util.Optional;

import eu.openanalytics.phaedra.plateservice.enumartion.SubstanceType;
import eu.openanalytics.phaedra.plateservice.model.Welltype;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.WellSubstance;
import eu.openanalytics.phaedra.plateservice.repository.WellSubstanceRepository;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;

@Service
public class WellSubstanceService {
    private final org.modelmapper.ModelMapper modelMapper = new ModelMapper();

    private WellSubstanceRepository wellSubstanceRepository;

    public WellSubstanceService(WellSubstanceRepository wellSubstanceRepository) {
        this.wellSubstanceRepository = wellSubstanceRepository;
    }

    public WellSubstanceDTO getWellSubstanceByWellId(long wellId) {
        WellSubstance result = wellSubstanceRepository.findByWellId(wellId);
        if (result == null) return null;
        return mapToWellSubstanceDTO(result);
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateId(long plateId) {
    	return wellSubstanceRepository.findByPlateId(plateId)
    			.stream()
    			.map(this::mapToWellSubstanceDTO)
    			.toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByName(String substanceName) {
        return wellSubstanceRepository.findWellSubstanceByName(substanceName)
                .stream()
                .map(this::mapToWellSubstanceDTO)
                .toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByType(SubstanceType substanceType) {
        return wellSubstanceRepository.findWellSubstanceByType(substanceType)
                .stream()
                .map(this::mapToWellSubstanceDTO)
                .toList();
    }

    public List<WellSubstanceDTO> getWellSubstanceByPlateIdAndWellTypes(long plateId, List<String> wellTypes) {
        return wellSubstanceRepository.findByPlateIdAndWellType(plateId, wellTypes)
                .stream()
                .map(this::mapToWellSubstanceDTO)
                .toList();
    }

    public void updateWellSubstance(WellSubstanceDTO wellSubstanceDTO) {
        Optional<WellSubstance> wellSubstance = wellSubstanceRepository.findById(wellSubstanceDTO.getId());
        wellSubstance.ifPresent(e -> {
            modelMapper.typeMap(WellSubstanceDTO.class, WellSubstance.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(wellSubstanceDTO, e);
            wellSubstanceRepository.save(e);
        });
    }

    public WellSubstanceDTO createWellSubstance(WellSubstanceDTO wellSubstanceDTO) {
        WellSubstance wellSubstance = new WellSubstance();
        wellSubstance.setId(wellSubstanceDTO.getId());
        wellSubstance.setName(wellSubstanceDTO.getName());
        wellSubstance.setType(EnumUtils.getEnumIgnoreCase(SubstanceType.class, wellSubstanceDTO.getType()));
        wellSubstance.setConcentration(wellSubstanceDTO.getConcentration());
        wellSubstance.setWellId(wellSubstanceDTO.getWellId());
        wellSubstance = wellSubstanceRepository.save(wellSubstance);
        return mapToWellSubstanceDTO(wellSubstance);
    }

    public void deleteWellSubstance(long wellSubstanceId) {
        wellSubstanceRepository.deleteById(wellSubstanceId);
    }

    private WellSubstanceDTO mapToWellSubstanceDTO(WellSubstance wellSubstance) {
        WellSubstanceDTO wellSubstanceDTO = new WellSubstanceDTO();
        wellSubstanceDTO.setId(wellSubstance.getId());
        wellSubstanceDTO.setName(wellSubstance.getName());
        wellSubstanceDTO.setType(wellSubstance.getType().name());
        wellSubstanceDTO.setConcentration(wellSubstance.getConcentration());
        wellSubstanceDTO.setWellId(wellSubstance.getWellId());
        return wellSubstanceDTO;
    }
}
