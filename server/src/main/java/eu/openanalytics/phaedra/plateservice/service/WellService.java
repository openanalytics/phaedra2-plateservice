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

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellStatusDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.exceptions.WellNotFoundException;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.util.WellNumberUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WellService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow).thenComparing(WellDTO::getColumn);

    private final WellRepository wellRepository;
    private final PlateService plateService;
    private final ProjectAccessService projectAccessService;
    private final WellSubstanceService wellSubstanceService;
    private final MetadataServiceClient metadataServiceClient;

    public WellService(WellRepository wellRepository, PlateService plateService, ProjectAccessService projectAccessService, WellSubstanceService wellSubstanceService, MetadataServiceClient metadataServiceClient) {
        this.wellRepository = wellRepository;
        this.plateService = plateService;
        this.projectAccessService = projectAccessService;
        this.wellSubstanceService = wellSubstanceService;
        this.metadataServiceClient = metadataServiceClient;
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
        projectAccessService.checkAccessLevel(
                plateService.getProjectIdByPlateId(wellDTO.getPlateId()),
                ProjectAccessLevel.Write
        );

        Well well = saveWell(wellDTO);
        WellDTO updatedWellDTO = modelMapper.map(well, WellDTO.class);
        populateWellSubstance(updatedWellDTO, well);

        return updatedWellDTO;
    }

  public List<WellDTO> getWells(List<Long> wellIds) {
    List<Well> wells = (List<Well>) wellRepository.findAllById(wellIds);
    if (CollectionUtils.isNotEmpty(wells)) {
      List<WellDTO> result = wells.stream()
          .map(well -> {
            try {
              return modelMapper.map(well, WellDTO.class)
                  .withWellSubstance(
                      wellSubstanceService.getWellSubstanceByWellId(well.getId()))
                  .withWellNr(calculateWellNumber(well,
                      plateService.getPlateById(well.getPlateId())));
            } catch (PlateNotFoundException e) {
              throw new RuntimeException(e);
            }
          })
          .toList();
      return result;
    } else {
      return Collections.emptyList();
    }
  }

  public WellDTO getWellById(Long wellId) throws WellNotFoundException, PlateNotFoundException {
    Well well = wellRepository.findById(wellId).orElseThrow(
        () -> new WellNotFoundException(String.format("Well with id %d not found!", wellId)));

    PlateDTO plate = plateService.getPlateById(well.getPlateId());
    List<WellSubstanceDTO> wellSubstances = wellSubstanceService.getWellSubstancesByPlateId(well.getPlateId());
    WellDTO wellDTO = modelMapper.map(well, WellDTO.class)
        .withWellNr(calculateWellNumber(well, plate))
        .withWellSubstance(findWellSubstanceForWell(well, wellSubstances))
        .withTags(retrieveWellTags(well))
        .withProperties(retrieveWellProperties(well));
    return wellDTO;
  }

  public List<WellDTO> getWellsByPlateId(long plateId) throws PlateNotFoundException {
    long projectId = Optional.ofNullable(plateService.getProjectIdByPlateId(plateId)).orElse(0l);
    projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);

    PlateDTO plate = plateService.getPlateById(plateId);
    List<WellSubstanceDTO> substances = wellSubstanceService.getWellSubstancesByPlateId(plateId);

    return wellRepository.findByPlateId(plateId).stream()
        .map(well -> modelMapper.map(well, WellDTO.class)
            .withWellSubstance(findWellSubstanceForWell(well, substances))
            .withWellNr(calculateWellNumber(well, plate)))
        .sorted(WELL_COMPARATOR)
        .toList();
  }

  public List<WellDTO> getWellsByPlateIds(List<Long> plateIds) {
    return wellRepository.findByPlateIds(plateIds).stream()
        .map(well -> modelMapper.map(well, WellDTO.class))
        .toList();
  }

    public List<WellDTO> updateWells(List<WellDTO> wellDTOS){
        List<Well> wells = wellDTOS.stream().map(this::mapToWell).toList();
        wellRepository.saveAll(wells);
        return wellDTOS;
    }

    public void rejectWell(long plateId, long wellId, WellStatusDTO wellStatusDTO) {
        changeWellStatus(plateId, wellId, wellStatusDTO.getStatus(), wellStatusDTO.getDescription());
    }

    public void rejectWells(long plateId, List<Long> wellIds, WellStatusDTO wellStatusDTO) {
        wellIds.forEach(wellId -> rejectWell(plateId, wellId, wellStatusDTO));
    }

    public void acceptWell(long plateId, long wellId) {
        changeWellStatus(plateId, wellId, WellStatus.ACCEPTED, null);
    }

    public void acceptWells(long plateId, List<Long> wellIds) {
        wellIds.forEach(wellId -> acceptWell(plateId, wellId));
    }

    private List<eu.openanalytics.phaedra.plateservice.dto.PropertyDTO> retrieveWellProperties(
        Well well) {
        List<PropertyDTO> properties = metadataServiceClient.getProperties(ObjectClass.WELL.name(),
            well.getId());
        return properties.stream().map(
            prop -> new eu.openanalytics.phaedra.plateservice.dto.PropertyDTO(prop.getPropertyName(),
                prop.getPropertyValue())).toList();
    }

    private List<String> retrieveWellTags(Well well) {
        List<TagDTO> tags = metadataServiceClient.getTags(ObjectClass.WELL.name(), well.getId());
        return tags.stream().map(tagDTO -> tagDTO.getTag()).toList();
    }

    private WellSubstanceDTO findWellSubstanceForWell(Well well, List<WellSubstanceDTO> substances) {
        return substances.stream()
                .filter(s -> s.getWellId().longValue() == well.getId().longValue())
                .findAny().orElse(null);
    }

    private Integer calculateWellNumber(Well well, PlateDTO plate){
        return WellNumberUtils.getWellNr(well.getRow(), well.getColumn(), plate.getColumns());
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

    private Well saveWell(WellDTO wellDTO) {
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        return wellRepository.save(well);
    }

    private void populateWellSubstance(WellDTO wellDTO, Well well) {
        wellDTO.setWellSubstance(wellSubstanceService.getWellSubstanceByWellId(well.getId()));
    }

    private void changeWellStatus(long plateId, long wellId, WellStatus wellStatus, String description) {
        projectAccessService.checkAccessLevel(
                plateService.getProjectIdByPlateId(plateId),
                ProjectAccessLevel.Write
        );
        Optional<Well> wellOptional = wellRepository.findById(wellId);
        wellOptional.ifPresent(well -> {
            well.setStatus(wellStatus);
            well.setDescription(description);
            wellRepository.save(well);
        });
    }
}
