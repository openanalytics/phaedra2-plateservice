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
package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.model.WellSubstance;
import eu.openanalytics.phaedra.plateservice.repository.WellSubstanceRepository;
import java.util.Collections;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WellSubstanceService {
    private final org.modelmapper.ModelMapper modelMapper = new ModelMapper();

    private WellSubstanceRepository wellSubstanceRepository;

    public WellSubstanceService(WellSubstanceRepository wellSubstanceRepository) {
        this.wellSubstanceRepository = wellSubstanceRepository;
    }

    public WellSubstanceDTO getWellSubstanceByWellId(long wellId) {
        List<WellSubstance> results = wellSubstanceRepository.findByWellId(wellId);
        if (CollectionUtils.isEmpty(results)) return null;
        return mapToWellSubstanceDTO(results.get(0));
    }

    public List<WellSubstanceDTO> getWellSubstancesByWellId(long wellId) {
        List<WellSubstance> results = wellSubstanceRepository.findByWellId(wellId);
        if (CollectionUtils.isEmpty(results)) return Collections.emptyList();
        return results.stream().map(wellSubstance -> mapToWellSubstanceDTO(wellSubstance)).toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateId(long plateId) {
    	return wellSubstanceRepository.findByPlateId(plateId)
    			.stream()
    			.map(this::mapToWellSubstanceDTO)
    			.toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateIds(List<Long> plateIds) {
        return wellSubstanceRepository.findByPlateIds(plateIds)
            .stream()
            .map(this::mapToWellSubstanceDTO)
            .toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateIdAndName(long plateId, String substanceName) {
        return wellSubstanceRepository.findWellSubstanceByPlateIdAndName(plateId, substanceName)
                .stream()
                .map(this::mapToWellSubstanceDTO)
                .toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateIdAndType(long plateId, SubstanceType substanceType) {
        return wellSubstanceRepository.findWellSubstanceByPlateIdAndType(plateId, substanceType)
                .stream()
                .map(this::mapToWellSubstanceDTO)
                .toList();
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateIdAndNameAndType(long plateId, String substanceName, SubstanceType substanceType) {
        return wellSubstanceRepository.findWellSubstanceByPlateIdAndNameAndType(plateId, substanceName, substanceType)
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
        wellSubstanceDTO.setType(wellSubstance.getType() != null ? wellSubstance.getType().name() : null);
        wellSubstanceDTO.setConcentration(wellSubstance.getConcentration());
        wellSubstanceDTO.setWellId(wellSubstance.getWellId());
        return wellSubstanceDTO;
    }
}
