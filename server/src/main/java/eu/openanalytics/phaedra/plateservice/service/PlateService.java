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

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceGraphQlClient;
import eu.openanalytics.phaedra.metadataservice.dto.MetadataDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.event.LinkOutcome;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateDefinitionLinkEvent;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateModificationEvent;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateModificationEventType;
import eu.openanalytics.phaedra.plateservice.enumeration.ApprovalStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.LinkStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.enumeration.UploadStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.ValidationStatus;
import eu.openanalytics.phaedra.plateservice.exceptions.ApprovalException;
import eu.openanalytics.phaedra.plateservice.exceptions.ClonePlateException;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.exceptions.ValidationException;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	private final MetadataServiceGraphQlClient metadataServiceGraphQlClient;

	private final ModelMapper modelMapper = new ModelMapper();
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public PlateService(PlateRepository plateRepository, @Lazy WellService wellService,
			ExperimentService experimentService,
			ProjectAccessService projectAccessService,
			IAuthorizationService authService,
			PlateTemplateService plateTemplateService,
			WellTemplateService wellTemplateService,
			WellSubstanceService wellSubstanceService,
			KafkaProducerService kafkaProducerService,
			MetadataServiceGraphQlClient metadataServiceGraphQlClient) {

		this.plateRepository = plateRepository;
		this.wellService = wellService;
		this.experimentService = experimentService;
		this.projectAccessService = projectAccessService;
		this.authService = authService;
		this.plateTemplateService = plateTemplateService;
		this.wellTemplateService = wellTemplateService;
		this.wellSubstanceService = wellSubstanceService;
		this.kafkaProducerService = kafkaProducerService;
    this.metadataServiceGraphQlClient = metadataServiceGraphQlClient;

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

		if (plate.getSequence() == null) plate.setSequence(1);
		if (plate.getLinkStatus() == null) plate.setLinkStatus(LinkStatus.NOT_LINKED);
		if (plate.getCalculationStatus() == null) plate.setCalculationStatus(CalculationStatus.CALCULATION_NEEDED);
		if (plate.getValidationStatus() == null) plate.setValidationStatus(ValidationStatus.VALIDATION_NOT_SET);
		if (plate.getApprovalStatus() == null) plate.setApprovalStatus(ApprovalStatus.APPROVAL_NOT_SET);
		if (plate.getUploadStatus() == null) plate.setUploadStatus(UploadStatus.UPLOAD_NOT_SET);

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

	public void validatePlate(Long plateId) throws ValidationException, PlateNotFoundException {
		PlateDTO plateDTO = this.getPlateById(plateId);
		if (plateDTO == null) throw new ValidationException(String.format("Plate with id %s does not exist!", plateId));

		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateDTO.getId()), ProjectAccessLevel.Write);

		if (plateDTO.getApprovalStatus().equals(ApprovalStatus.APPROVAL_NOT_SET) &&
				plateDTO.getValidationStatus().equals(ValidationStatus.VALIDATION_NOT_SET)) {
			plateDTO.setValidationStatus(ValidationStatus.VALIDATED);
			plateDTO.setValidatedBy(authService.getCurrentPrincipalName());
			plateDTO.setValidatedOn(new Date());
		} else {
			throw new ValidationException(String.format("The plate with plate id %s is already validated!", plateId));
		}

		this.updatePlate(plateDTO);
	}

	public void invalidatePlate(Long plateId, String reason) throws ValidationException, PlateNotFoundException {
		PlateDTO plateDTO = this.getPlateById(plateId);
		if (plateDTO == null) throw new ValidationException(String.format("Plate with id %s does not exist!", plateId));

		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateDTO.getId()), ProjectAccessLevel.Write);

		if (plateDTO.getApprovalStatus().equals(ApprovalStatus.APPROVAL_NOT_SET)
				&& plateDTO.getValidationStatus().equals(ValidationStatus.VALIDATION_NOT_SET)) {
			plateDTO.setValidationStatus(ValidationStatus.INVALIDATED);
			plateDTO.setDescription(reason);
			plateDTO.setValidatedBy(authService.getCurrentPrincipalName());
			plateDTO.setValidatedOn(new Date());
		} else {
			throw new ValidationException(String.format("The plate with plate id %s is already validated!", plateId));
		}

		this.updatePlate(plateDTO);
	}

	public void resetPlateValidation(Long plateId) throws ValidationException, PlateNotFoundException {
		PlateDTO plateDTO = getPlateById(plateId);
		if (plateDTO == null) throw new ValidationException(String.format("Plate with id %s does not exist!", plateId));

		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateDTO.getId()), ProjectAccessLevel.Write);

		if (plateDTO.getApprovalStatus().equals(ApprovalStatus.APPROVAL_NOT_SET) &&
				!plateDTO.getValidationStatus().equals(ValidationStatus.VALIDATION_NOT_SET)) {
			plateDTO.setValidationStatus(ValidationStatus.VALIDATION_NOT_SET);
			plateDTO.setValidatedBy(authService.getCurrentPrincipalName());
			plateDTO.setValidatedOn(new Date());
		} else {
			throw new ValidationException(String.format("The plate with plate id %s is already validated!", plateId));
		}

		this.updatePlate(plateDTO);
	}

	public void approvePlate(Long plateId) throws ApprovalException, PlateNotFoundException {
		PlateDTO plateDTO = getPlateById(plateId);
		if (plateDTO == null) throw new ApprovalException(String.format("Plate with id %s does not exist!", plateId));

		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateDTO.getId()), ProjectAccessLevel.Write);

		if (plateDTO.getValidationStatus().equals(ValidationStatus.VALIDATED) &&
				plateDTO.getApprovalStatus().equals(ApprovalStatus.APPROVAL_NOT_SET)) {
			plateDTO.setApprovalStatus(ApprovalStatus.APPROVED);
			plateDTO.setApprovedBy(authService.getCurrentPrincipalName());
			plateDTO.setApprovedOn(new Date());
		} else {
			throw new ApprovalException(String.format("Cannot approve the plate (%s)! The approval status for plate (%s) has already been set!", plateId));
		}

		this.updatePlate(plateDTO);
	}

	public void disapprovePlate(Long plateId, String reason) throws ApprovalException, PlateNotFoundException {
		PlateDTO plateDTO = getPlateById(plateId);
		if (plateDTO == null) throw new ApprovalException(String.format("Plate with id %s does not exist!", plateId));

		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateDTO.getId()), ProjectAccessLevel.Write);

		if (plateDTO.getValidationStatus().equals(ValidationStatus.VALIDATED) &&
				!plateDTO.getApprovalStatus().equals(ApprovalStatus.DISAPPROVED)) {
			plateDTO.setApprovalStatus(ApprovalStatus.DISAPPROVED);
			plateDTO.setDisapprovedReason(reason);
			plateDTO.setApprovedBy(authService.getCurrentPrincipalName());
			plateDTO.setApprovedOn(new Date());
		} else {
			throw new ApprovalException(String.format("Cannot approve the plate (%s)! The approval status for plate (%s) has already been set!", plateId));
		}

		this.updatePlate(plateDTO);
	}

	public PlateDTO clonePlateById(long plateId) throws PlateNotFoundException, ClonePlateException {
		PlateDTO plateDTO = getPlateById(plateId);
		if (plateDTO == null) {
			throw new PlateNotFoundException(plateId);
		}
		return clonePlate(plateDTO);
	}

	public PlateDTO clonePlate(PlateDTO plateDTO) throws ClonePlateException, PlateNotFoundException {
		PlateDTO plateCloneDTO = createClonedPlate(plateDTO);
		if (plateCloneDTO.getId() != null && !plateCloneDTO.getId().equals(plateDTO.getId())) {
			List<WellDTO> originalWells = wellService.getWellsByPlateId(plateDTO.getId());
			cloneWells(originalWells, plateCloneDTO.getId());
			return plateCloneDTO;
		} else {
			throw new ClonePlateException("An error occurred while trying to clone a plate!");
		}
	}

	public void deletePlate(long plateId) {
		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateId), ProjectAccessLevel.Write);
		plateRepository.deleteById(plateId);
		kafkaProducerService.notifyPlateModified(new PlateModificationEvent(PlateDTO.builder().id(plateId).build(), PlateModificationEventType.Deleted));
	}

	public void deletePlates(List<Long> plateIds) {
		for (Long plateId : plateIds) {
			deletePlate(plateId);
		}
	}

	public List<PlateDTO> getPlates(List<Long> plateIds) {
		List<Plate> plates = new ArrayList<>();
		if (CollectionUtils.isEmpty(plateIds)) {
			plates.addAll((List<Plate>) plateRepository.findAll());
		} else {
			plates.addAll((List<Plate>) plateRepository.findAllById(plateIds));
		}

		List<PlateDTO> plateDTOs = plates.stream()
				.filter(p -> projectAccessService.hasAccessLevel(p.getId(), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.collect(Collectors.toList());

		enrichWithMetadata(plateDTOs);

		return plateDTOs;
	}


	public List<PlateDTO> getPlatesByExperimentId(long experimentId) {
		List<Plate> plates = plateRepository.findByExperimentId(experimentId);
		List<PlateDTO> plateDTOs = plates.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.toList();

		enrichWithMetadata(plateDTOs);

		return plateDTOs;
	}

	public List<PlateDTO> getPlatesByExperimentIds(List<Long> experimentIds) {
		List<PlateDTO> plateDTOs = Optional.ofNullable(plateRepository.findByExperimentIds(experimentIds))
				.orElseGet(Collections::emptyList)
				.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.toList();

		enrichWithMetadata(plateDTOs);

		return plateDTOs;
	}

	public List<PlateDTO> getPlatesByBarcode(String barcode) {
		List<Plate> plates = plateRepository.findByBarcode(barcode);
		List<PlateDTO> plateDTOs = plates.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.toList();

		enrichWithMetadata(plateDTOs);
		return plateDTOs;
	}

	public List<PlateDTO> getPlatesByBarcodeAndExperiment(String barcode, long experimentId) {
		List<Plate> result = plateRepository.findByBarcodeAndExperimentId(barcode, experimentId);
		return result.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.toList();
	}

	@Cacheable("plate_id")
	public PlateDTO getPlateById(long plateId) throws PlateNotFoundException {
		Optional<Plate> result = plateRepository.findById(plateId);
		return result
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(p -> modelMapper.map(p, PlateDTO.class))
				.orElseThrow(() -> new PlateNotFoundException(plateId));
	}

	@Cacheable("plate_project_id")
	public Long getProjectIdByPlateId(long plateId) {
		return plateRepository.findProjectIdByPlateId(plateId);
	}

	@Cacheable("plate_project_id")
	public Long getProjectIdByExperimentId(long experimentId) {
		return plateRepository.findProjectIdByExperimentId(experimentId);
	}

	public PlateDTO linkPlateTemplate(long plateId, long plateTemplateId) throws PlateNotFoundException {
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

	public List<PlateDTO> linkPlates(long experimentId, long plateTemplateId) {
		List<PlateDTO> plates = getPlatesByExperimentId(experimentId);
		return plates.stream().map(plate -> tryLinkingPlate(plate, plateTemplateId))
				.collect(Collectors.toList());
	}

	private PlateDTO tryLinkingPlate(PlateDTO plate, long plateTemplateId) {
		try {
			return linkPlateTemplate(plate.getId(), plateTemplateId);
		} catch (PlateNotFoundException e) {
			throw new IllegalStateException("Plate could not be linked", e);
		}
	}

	private void linkWithPlateTemplate(long plateId, long plateTemplateId) throws PlateNotFoundException {
		List<WellDTO> wells = wellService.getWellsByPlateId(plateId);
		List<WellSubstanceDTO> wellSubstances = wellSubstanceService.getWellSubstancesByPlateId(plateId);
		List<WellTemplateDTO> wellTemplates  = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);

		for (int i = 0; i < wells.size(); i++) {
			// Update wellType
			if (!wellTemplates.get(i).isSkipped())
				wells.get(i).setWellType(wellTemplates.get(i).getWellType());
			else
				wells.get(i).setWellType("EMPTY");

			List<WellSubstanceDTO> existing = wellSubstanceService.getWellSubstancesByWellId(wells.get(i).getId());
			if (CollectionUtils.isEmpty(existing)) {
				createNewWellSubstance(wells.get(i), wellTemplates.get(i));
			} else {
				//ToDO: Check if there are calculations available with previous template, if so handle this issue correctly?
				updateExistingWellSubstance(existing.get(0), wellTemplates.get(i));
			}
		}

		wellService.updateWells(wells);
	}

	private void createNewWellSubstance(WellDTO wellDTO, WellTemplateDTO wellTemplateDTO) {
		logger.info("Create new well substance %s", wellTemplateDTO.getSubstanceName());
		WellSubstanceDTO wellSubstanceDTO = new WellSubstanceDTO();
		wellSubstanceDTO.setWellId(wellDTO.getId());
		wellSubstanceDTO.setType(StringUtils.isBlank(wellTemplateDTO.getSubstanceType()) ? SubstanceType.COMPOUND.name() : wellTemplateDTO.getSubstanceType());
		wellSubstanceDTO.setName(wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setConcentration(wellTemplateDTO.getConcentration());
		wellSubstanceService.createWellSubstance(wellSubstanceDTO);
	}

	private void updateExistingWellSubstance(WellSubstanceDTO wellSubstanceDTO, WellTemplateDTO wellTemplateDTO) {
		logger.info("Update existing well substance %s to %s", wellSubstanceDTO.getName(), wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setType(wellTemplateDTO.getSubstanceType());
		wellSubstanceDTO.setName(wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setConcentration(wellTemplateDTO.getConcentration());
		wellSubstanceService.updateWellSubstance(wellSubstanceDTO);
	}

	private PlateDTO createClonedPlate(PlateDTO plateDTO) {
		PlateDTO pClone = new PlateDTO();
		modelMapper.map(plateDTO, pClone);
		pClone.setId(null);
		pClone.setDescription("Copy of plate " + plateDTO.getBarcode() + " (" + plateDTO.getId() + ")");
		return createPlate(pClone);
	}

	private void cloneWells(List<WellDTO> originalWells, Long plateCloneId) throws PlateNotFoundException {
		List<WellDTO> clonedWells = wellService.getWellsByPlateId(plateCloneId);
		IntStream.range(0, originalWells.size()).forEach(i -> {
			cloneWellProperties(originalWells.get(i), clonedWells.get(i));
			if (originalWells.get(i).getWellSubstance() != null)
				cloneWellSubstance(originalWells.get(i), clonedWells.get(i));
		});
		wellService.updateWells(clonedWells);
	}

	private void cloneWellProperties(WellDTO originalWell, WellDTO clonedWell) {
		clonedWell.setWellType(originalWell.getWellType());
		clonedWell.setStatus(originalWell.getStatus());
		clonedWell.setDescription(originalWell.getDescription());
		clonedWell.setTags(originalWell.getTags());
	}

	private void cloneWellSubstance(WellDTO originalWell, WellDTO clonedWell) {
		WellSubstanceDTO wsClone = new WellSubstanceDTO();
		modelMapper.map(originalWell.getWellSubstance(), wsClone);
		wsClone.setId(null);
		wsClone.setWellId(clonedWell.getId());
		WellSubstanceDTO clonedSubstance = wellSubstanceService.createWellSubstance(wsClone);
		clonedWell.setWellSubstance(clonedSubstance);
	}

	public PlateDTO moveByPlateId(Long plateId, Long experimentId) throws PlateNotFoundException {
		Long projectId = getProjectIdByExperimentId(experimentId);
		if (projectId != null) projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
		PlateDTO plateDTO = getPlateById(plateId);
		plateDTO.setExperimentId(experimentId);
		return updatePlate(plateDTO);
	}

	private void enrichWithMetadata(List<PlateDTO> plates) {
		if (CollectionUtils.isNotEmpty(plates)) {
			// Create a map of plate ID to PlateDTO for quick lookup
			Map<Long, PlateDTO> plateMap = new HashMap<>();
			List<Long> plateIds = new ArrayList<>(plates.size());
			for (PlateDTO plate : plates) {
				plateMap.put(plate.getId(), plate);
				plateIds.add(plate.getId());
			}

			// Retrieve the metadata using the list of plate IDs
			List<MetadataDTO> plateMetadataList = metadataServiceGraphQlClient
					.getMetadata(plateIds, ObjectClass.PLATE);

			for (MetadataDTO metadata : plateMetadataList) {
				PlateDTO plate = plateMap.get(metadata.getObjectId());
				if (plate != null) {
					plate.setTags(metadata.getTags().stream()
							.map(TagDTO::getTag)
							.toList());
					List<PropertyDTO> propertyDTOs = new ArrayList<>(metadata.getProperties().size());
					for (eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO property : metadata.getProperties()) {
						propertyDTOs.add(new PropertyDTO(property.getPropertyName(), property.getPropertyValue()));
					}
					plate.setProperties(propertyDTOs);
				}
			}
		}
	}
}
