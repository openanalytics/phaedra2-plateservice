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

import eu.openanalytics.phaedra.plateservice.dto.*;
import eu.openanalytics.phaedra.plateservice.dto.event.LinkOutcome;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateDefinitionLinkEvent;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateModificationEvent;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateModificationEventType;
import eu.openanalytics.phaedra.plateservice.enumeration.LinkStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlateService {

	private final PlateRepository plateRepository;
	private final WellService wellService;
	private final ExperimentService experimentService;
	private final ProjectAccessService projectAccessService;
	private final IAuthorizationService authService;

	private final PlateTemplateService plateTemplateService;
	private final WellTemplateService wellTemplateService;
	private final WellSubstanceService wellSubstanceService;

	private final KafkaProducerService kafkaProducerService;

	private final ModelMapper modelMapper = new ModelMapper();

	public PlateService(PlateRepository plateRepository, @Lazy WellService wellService, ExperimentService experimentService,
						ProjectAccessService projectAccessService, IAuthorizationService authService,
						PlateTemplateService plateTemplateService, WellTemplateService wellTemplateService, WellSubstanceService wellSubstanceService,
						KafkaProducerService kafkaProducerService) {

		this.plateRepository = plateRepository;
		this.wellService = wellService;
		this.experimentService = experimentService;
		this.projectAccessService = projectAccessService;
		this.authService = authService;
		this.plateTemplateService = plateTemplateService;
		this.wellTemplateService = wellTemplateService;
		this.wellSubstanceService = wellSubstanceService;
		this.kafkaProducerService = kafkaProducerService;

		// TODO move to dedicated ModelMapper service
		Configuration builderConfiguration = modelMapper.getConfiguration().copy()
				.setDestinationNameTransformer(NameTransformers.builder())
				.setDestinationNamingConvention(NamingConventions.builder());
		modelMapper.createTypeMap(Plate.class, PlateDTO.PlateDTOBuilder.class, builderConfiguration)
				.setPropertyCondition(Conditions.isNotNull());
	}

	public PlateDTO createPlate(PlateDTO plateDTO) {
		long projectId = Optional.ofNullable(experimentService.getExperimentById(plateDTO.getExperimentId()))
				.map(ExperimentDTO::getProjectId)
				.orElse(0L);
		if (projectId == 0L) return null; // Experiment not found or not accessible.

		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

		Plate plate = new Plate();
		modelMapper.typeMap(PlateDTO.class, Plate.class).map(plateDTO, plate);
		plate.setCreatedBy(authService.getCurrentPrincipalName());
		plate.setCreatedOn(new Date());
		plate = plateRepository.save(plate);

		// Automatically create the corresponding wells
		wellService.createWells(plate);

		PlateDTO newPlate = modelMapper.map(plate, PlateDTO.class);
		kafkaProducerService.notifyPlateModified(new PlateModificationEvent(newPlate, PlateModificationEventType.Created));
		return newPlate;
	}

	public PlateDTO updatePlate(PlateDTO plateDTO) {
		Plate plate = plateRepository.findById(plateDTO.getId()).orElse(null);
		if (plate == null) return null;

		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plate.getId()), ProjectAccessLevel.Write);

		modelMapper.typeMap(PlateDTO.class, Plate.class)
				.setPropertyCondition(Conditions.isNotNull())
				.map(plateDTO, plate);
		plate.setUpdatedBy(authService.getCurrentPrincipalName());
		plate.setUpdatedOn(new Date());
		plateRepository.save(plate);

		PlateDTO modifiedPlate = modelMapper.map(plate, PlateDTO.class);
		kafkaProducerService.notifyPlateModified(new PlateModificationEvent(modifiedPlate, PlateModificationEventType.Updated));
		return modifiedPlate;
	}

	public void deletePlate(long plateId) {
		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateId), ProjectAccessLevel.Write);
		plateRepository.deleteById(plateId);
		kafkaProducerService.notifyPlateModified(new PlateModificationEvent(PlateDTO.builder().id(plateId).build(), PlateModificationEventType.Deleted));
	}

	public List<PlateDTO> getPlatesByExperimentId(long experimentId) {
		List<Plate> result = plateRepository.findByExperimentId(experimentId);
		return result.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.toList();
	}

	public List<PlateDTO> getPlatesByBarcode(String barcode) {
		List<Plate> result = plateRepository.findByBarcode(barcode);
		return result.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.toList();
	}

	public PlateDTO getPlateById(long plateId) {
		Optional<Plate> result = plateRepository.findById(plateId);
		return result
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.orElse(null);
	}

	@Cacheable("plate_project_id")
	public Long getProjectIdByPlateId(long plateId) {
		return plateRepository.findProjectIdByPlateId(plateId);
	}

	public PlateDTO linkPlate(long plateId, long plateTemplateId) {
		Long projectId = getProjectIdByPlateId(plateId);
		if (projectId != null) projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

		PlateDTO plateDTO = getPlateById(plateId);
		PlateTemplateDTO plateTemplateDTO = plateTemplateService.getPlateTemplateById(plateTemplateId);

		// Validate the plate and template objects.
		if (plateDTO == null || plateTemplateDTO == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plate or template can not be found for these ids.");
		if (plateDTO.getRows() != plateTemplateDTO.getRows() || plateDTO.getColumns() != plateTemplateDTO.getColumns())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plate or template have different dimensions.");

		// Perform the actual link by copying template information into the wells.
		linkWithPlateTemplate(plateId, plateTemplateId);

		// Update the linkage information of plate
		plateDTO.setLinkTemplateId(plateTemplateDTO.getId().toString());
		plateDTO.setLinkSource("layout-template");
		plateDTO.setLinkStatus(LinkStatus.LINKED);
		plateDTO.setLinkedOn(new Date());

		PlateDTO updatedPlate = updatePlate(plateDTO);
		kafkaProducerService.notifyPlateDefinitionLinked(new PlateDefinitionLinkEvent(plateId, plateTemplateId, LinkOutcome.OK));
		return updatedPlate;
	}

	private void linkWithPlateTemplate(long plateId, long plateTemplateId) {
		List<WellDTO> wells = wellService.getWellsByPlateId(plateId);
		List<WellSubstanceDTO> wellSubstances = wellSubstanceService.getWellSubstancesByPlateId(plateId);
		List<WellTemplateDTO> wellTemplates  = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);

		for (int i = 0; i < wells.size(); i++) {
			WellDTO well = wells.get(i);
			WellSubstanceDTO previousSubstance = wellSubstances.stream()
					.filter(w -> w.getWellId() == well.getId())
					.findAny().orElse(null);

			// Update wellType
			if (!wellTemplates.get(i).isSkipped())
				wells.get(i).setWellType(wellTemplates.get(i).getWellType());
			else
				wells.get(i).setWellType("EMPTY");

			// Update substance (if needed)
			String newSubstanceType = wellTemplates.get(i).getSubstanceType();
			if (newSubstanceType != null && !newSubstanceType.isEmpty()) {
				if (previousSubstance == null) {
					createNewWellSubstance(wells.get(i), wellTemplates.get(i));
				} else {
					updateExistingWellSubstance(previousSubstance, wellTemplates.get(i));
				}
			} else if (previousSubstance != null) {
				wellSubstanceService.deleteWellSubstance(previousSubstance.getId());
			}
		}

		wellService.updateWells(wells);
	}

	private void createNewWellSubstance(WellDTO wellDTO, WellTemplateDTO wellTemplateDTO) {
		WellSubstanceDTO wellSubstanceDTO = new WellSubstanceDTO();
		wellSubstanceDTO.setWellId(wellDTO.getId());
		wellSubstanceDTO.setType(StringUtils.isBlank(wellTemplateDTO.getSubstanceType()) ? SubstanceType.COMPOUND.name() : wellTemplateDTO.getSubstanceType());
		wellSubstanceDTO.setName(wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setConcentration(wellTemplateDTO.getConcentration());
		wellSubstanceService.createWellSubstance(wellSubstanceDTO);
	}

	private void updateExistingWellSubstance(WellSubstanceDTO wellSubstanceDTO, WellTemplateDTO wellTemplateDTO) {
		wellSubstanceDTO.setType(wellTemplateDTO.getSubstanceType());
		wellSubstanceDTO.setName(wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setConcentration(wellTemplateDTO.getConcentration());
		wellSubstanceService.updateWellSubstance(wellSubstanceDTO);
	}
}
