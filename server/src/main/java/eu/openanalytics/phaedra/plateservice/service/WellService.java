/**
 * Phaedra II
 * <p>
 * Copyright (C) 2016-2025 Open Analytics
 * <p>
 * ===========================================================================
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * Apache License as published by The Apache Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Apache
 * License for more details.
 * <p>
 * You should have received a copy of the Apache License along with this program.  If not, see
 * <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceGraphQlClient;
import eu.openanalytics.phaedra.metadataservice.dto.MetadataDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.PropertyDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class WellService {

  private final ModelMapper modelMapper;

  private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow)
      .thenComparing(WellDTO::getColumn);

  private final WellRepository wellRepository;
  private final PlateService plateService;
  private final ProjectAccessService projectAccessService;
  private final WellSubstanceService wellSubstanceService;
  private final MetadataServiceClient metadataServiceClient;
  private final MetadataServiceGraphQlClient metadataServiceGraphQlClient;

  public WellService(WellRepository wellRepository, PlateService plateService,
      ProjectAccessService projectAccessService, WellSubstanceService wellSubstanceService,
      MetadataServiceClient metadataServiceClient,
      MetadataServiceGraphQlClient metadataServiceGraphQlClient) {
    this.wellRepository = wellRepository;
    this.plateService = plateService;
    this.projectAccessService = projectAccessService;
    this.wellSubstanceService = wellSubstanceService;
    this.metadataServiceClient = metadataServiceClient;
    this.metadataServiceGraphQlClient = metadataServiceGraphQlClient;

    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    this.modelMapper.typeMap(Well.class, WellDTO.class).addMappings(mapper -> {
      mapper.map(src -> src.getPlateId(), WellDTO::setPlateId);
      mapper.map(src -> src.getPlate(), WellDTO::setPlate);
      mapper.map(src -> src.getExperiment(), WellDTO::setExperiment);
      mapper.map(src -> src.getProject(), WellDTO::setProject);
    });
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
    Iterable<Well> savedWells = wellRepository.saveAll(wells);
    List<Well> result = IteratorUtils.toList(savedWells.iterator());
    return result.stream()
        .map(well -> modelMapper.map(well, WellDTO.class))
        .collect(Collectors.toList());
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
    List<Well> wells = wellRepository.findAllByIdIn(wellIds);
    if (CollectionUtils.isNotEmpty(wells)) {
      return wells.stream().map(well -> modelMapper.map(well, WellDTO.class)
              .withWellSubstance(wellSubstanceService.getWellSubstanceByWellId(well.getId()))
              .withWellNr(calculateWellNumber(well)))
          .toList();
    } else {
      return Collections.emptyList();
    }
  }

  public WellDTO getWellById(long wellId) throws WellNotFoundException, PlateNotFoundException {
    Well well = Optional.ofNullable(wellRepository.findById(wellId)).orElseThrow(
        () -> new WellNotFoundException(String.format("Well with id %d not found!", wellId)));

    List<WellSubstanceDTO> wellSubstances = wellSubstanceService.getWellSubstancesByPlateId(
        well.getPlateId());
    Map<Long, MetadataDTO> wellMetadata = retrieveWellMetadata(Collections.singletonList(wellId));

    return enrichWellDTO(well, wellSubstances, wellMetadata);
  }

  public List<WellDTO> getWellsByPlateId(long plateId) throws PlateNotFoundException {
    long projectId = Optional.ofNullable(plateService.getProjectIdByPlateId(plateId)).orElse(0L);
    projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);

    List<Well> wells = wellRepository.findAllByPlateId(plateId);
    if (wells.isEmpty()) {
      return Collections.emptyList();
    }

    return wellToWellDTO(wells,
        wellSubstanceService.getWellSubstancesByPlateId(plateId));
  }

  public List<WellDTO> getWellsByPlateIds(List<Long> plateIds) {
    List<Well> wells = wellRepository.findAllByPlateIdIn(plateIds);
    if (wells.isEmpty()) {
      return Collections.emptyList();
    }

    return wellToWellDTO(wells,
        wellSubstanceService.getWellSubstancesByPlateIds(plateIds));
  }

  public List<WellDTO> getWellsbyExperimentId(Long experimentId) {
    List<Long> plateIds = plateService.getPlatesByExperimentId(experimentId).stream()
        .map(plate -> plate.getId())
        .toList();

    return getWellsByPlateIds(plateIds);
  }

  public List<WellDTO> getWellsbyExperimentIds(List<Long> experimentIds) {
    List<Long> plateIds = plateService.getPlatesByExperimentIds(experimentIds).stream()
        .map(plate -> plate.getId())
        .toList();

    return getWellsByPlateIds(plateIds);
  }

  public List<WellDTO> updateWells(List<WellDTO> wellDTOS) {
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

  private List<WellDTO> wellToWellDTO(List<Well> wells,
      List<WellSubstanceDTO> wellSubstances) {
    List<Long> wellIds = wells.stream().map(Well::getId).toList();
    Map<Long, MetadataDTO> wellMetadata = retrieveWellMetadata(wellIds);

    List<WellDTO> result = new ArrayList<>(wells.size());
    for (Well well : wells) {
      result.add(enrichWellDTO(well, wellSubstances, wellMetadata));
    }

    result.sort(WELL_COMPARATOR);
    return result;
  }

  private WellDTO enrichWellDTO(Well well, List<WellSubstanceDTO> wellSubstances,
      Map<Long, MetadataDTO> wellMetadata) {
    MetadataDTO metadata = wellMetadata.get(well.getId());
    return modelMapper.map(well, WellDTO.class)
        .withWellNr(calculateWellNumber(well))
        .withWellSubstance(findWellSubstanceForWell(well, wellSubstances))
        .withTags(metadata.getTags().stream().map(TagDTO::getTag).toList())
        .withProperties(metadata.getProperties().stream()
            .map(propertyDTO -> new PropertyDTO(propertyDTO.getPropertyName(),
                propertyDTO.getPropertyValue()))
            .toList());
  }

  private Map<Long, MetadataDTO> retrieveWellMetadata(List<Long> wellIds) {
    if (wellIds.isEmpty()) {
      return Collections.emptyMap();
    }

    List<MetadataDTO> allMetadata = metadataServiceGraphQlClient.getMetadata(wellIds,
        ObjectClass.WELL);
    Map<Long, MetadataDTO> metadataByObjectId = new HashMap<>(allMetadata.size());
    for (MetadataDTO metadata : allMetadata) {
      metadataByObjectId.put(metadata.getObjectId(), metadata);
    }

    return metadataByObjectId;
  }

  private WellSubstanceDTO findWellSubstanceForWell(Well well, List<WellSubstanceDTO> substances) {
    return substances.stream()
        .filter(s -> s.getWellId().longValue() == well.getId().longValue())
        .findAny().orElse(null);
  }

  private Integer calculateWellNumber(Well well) {
    return WellNumberUtils.getWellNr(well.getRow(), well.getColumn(), well.getPlate().columns());
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

  private void changeWellStatus(long plateId, long wellId, WellStatus wellStatus,
      String description) {
    projectAccessService.checkAccessLevel(
        plateService.getProjectIdByPlateId(plateId),
        ProjectAccessLevel.Write
    );
    Well well = wellRepository.findById(wellId);
    if (well != null) {
      well.setStatus(wellStatus);
      well.setDescription(description);
      wellRepository.save(well);
    }
  }
}
