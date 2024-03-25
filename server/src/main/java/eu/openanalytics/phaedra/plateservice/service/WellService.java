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
package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.util.WellNumberUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow).thenComparing(WellDTO::getColumn);

    private final WellRepository wellRepository;
    private final PlateService plateService;
    private final ProjectAccessService projectAccessService;
    private final WellSubstanceService wellSubstanceService;

    public WellService(WellRepository wellRepository, PlateService plateService, ProjectAccessService projectAccessService, WellSubstanceService wellSubstanceService) {
        this.wellRepository = wellRepository;
        this.plateService = plateService;
        this.projectAccessService = projectAccessService;
        this.wellSubstanceService = wellSubstanceService;
    }

    public WellDTO createWell(WellDTO wellDTO) {
    	long projectId = plateService.getProjectIdByPlateId(wellDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

        Well well = new Well(wellDTO.getPlateId());
        modelMapper.map(wellDTO, Well.class);
        well = wellRepository.save(well);
        return modelMapper.map(well, WellDTO.class);
    }

    public List<WellDTO> createWells(Plate plate) {
    	long projectId = plateService.getProjectIdByPlateId(plate.getId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

        List<Well> wells = new ArrayList<>(plate.getRows() * plate.getColumns());
        for (int r = 1; r <= plate.getRows(); r++) {
            for (int c = 1; c <= plate.getColumns(); c++) {
                Well well = new Well(plate.getId());
                well.setRow(r);
                well.setColumn(c);
                well.setWellType("EMPTY");
                wells.add(well);
            }
        }
        wellRepository.saveAll(wells);
        return wells.stream().map(well -> modelMapper.map(well, WellDTO.class)).collect(Collectors.toList());
    }

    public WellDTO updateWell(WellDTO wellDTO) {
    	long projectId = plateService.getProjectIdByPlateId(wellDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

        //TODO change to map function
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        well = wellRepository.save(well);

        WellDTO updatedDTO = modelMapper.map(well, WellDTO.class);
        updatedDTO.setWellSubstance(wellSubstanceService.getWellSubstanceByWellId(well.getId()));

        return updatedDTO;
    }

    public List<WellDTO> updateWells(List<WellDTO> wellDTOS){
        List<Well> wells = wellDTOS.stream().map(this::mapToWell).toList();
        wellRepository.saveAll(wells);
        return wellDTOS;
    }

    public List<WellDTO> getWellsByPlateId(long plateId) {
    	long projectId = Optional.ofNullable(plateService.getProjectIdByPlateId(plateId)).orElse(0l);
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);

        PlateDTO plate = plateService.getPlateById(plateId);
    	List<WellSubstanceDTO> substances = wellSubstanceService.getWellSubstancesByPlateId(plateId);

        return wellRepository.findByPlateId(plateId).stream()
        		.map(well -> modelMapper.map(well, WellDTO.class))
        		.map(wellDTO -> {
        			wellDTO.setWellSubstance(findWellSubstanceForWell(wellDTO, substances));
                    wellDTO.setWellNr(calculateWellNumber(wellDTO, plate));
        			return wellDTO;
        		})
        		.sorted(WELL_COMPARATOR)
        		.toList();
    }

    private WellSubstanceDTO findWellSubstanceForWell(WellDTO wellDTO, List<WellSubstanceDTO> substances) {
        return substances.stream()
                .filter(s -> s.getWellId().longValue() == wellDTO.getId().longValue())
                .findAny().orElse(null);
    }

    private Integer calculateWellNumber(WellDTO wellDTO, PlateDTO plate){
        return WellNumberUtils.getWellNr(wellDTO.getRow(), wellDTO.getColumn(), plate.getColumns());
    }

    private Well mapToWell(WellDTO wellDTO) {
        Well well = new Well(wellDTO.getPlateId());
        well.setId(wellDTO.getId());
        well.setWellType(wellDTO.getWellType());
        well.setDescription(wellDTO.getDescription());
        well.setRow(wellDTO.getRow());
        well.setColumn(wellDTO.getColumn());
        well.setStatus(wellDTO.getStatus());
        well.setPlateId(wellDTO.getPlateId());
        return well;
    }
}
