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
import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.ExperimentStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.repository.ExperimentRepository;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ExperimentService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ExperimentRepository experimentRepository;
	private final PlateService plateService;
	private final PlateRepository plateRepository;
	private final ProjectAccessService projectAccessService;
	private final IAuthorizationService authService;
	private final MetadataServiceGraphQlClient metadataServiceGraphQlClient;

	public ExperimentService(ExperimentRepository experimentRepository,
			@Lazy PlateService plateService,
			PlateRepository plateRepository,
			ProjectAccessService projectAccessService,
			IAuthorizationService authService,
      MetadataServiceGraphQlClient metadataServiceGraphQlClient) {

		this.experimentRepository = experimentRepository;
		this.plateService = plateService;
		this.plateRepository = plateRepository;
		this.projectAccessService = projectAccessService;
		this.authService = authService;
    this.metadataServiceGraphQlClient = metadataServiceGraphQlClient;
  }

	public ExperimentDTO createExperiment(ExperimentDTO experimentDTO) {
		Experiment experiment = new Experiment();
		modelMapper.typeMap(ExperimentDTO.class, Experiment.class)
				.map(experimentDTO, experiment);
		experiment.setCreatedBy(authService.getCurrentPrincipalName());
		experiment.setCreatedOn(new Date());
		experiment.setStatus(ExperimentStatus.OPEN);

		projectAccessService.checkAccessLevel(experiment.getProjectId(), ProjectAccessLevel.Write);
		experiment = experimentRepository.save(experiment);
		return mapToExperimentDTO(experiment);
	}

	public void updateExperiment(ExperimentDTO experimentDTO) {
		Optional<Experiment> experiment = experimentRepository.findById(experimentDTO.getId());
		experiment.ifPresent(e -> {
			projectAccessService.checkAccessLevel(e.getProjectId(), ProjectAccessLevel.Write);
			modelMapper.typeMap(ExperimentDTO.class, Experiment.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(experimentDTO, e);
			e.setUpdatedBy(authService.getCurrentPrincipalName());
			e.setUpdatedOn(new Date());
			experimentRepository.save(e);
		});
	}

	public void deleteExperiment(long experimentId) {
		experimentRepository.findById(experimentId).ifPresent(e -> {
			projectAccessService.checkAccessLevel(e.getProjectId(), ProjectAccessLevel.Write);
			experimentRepository.deleteById(experimentId);
		});
	}

	public void deleteExperiments(List<Long> experimentIds) {
		for (Long experimentId : experimentIds) {
			experimentRepository.findById(experimentId).ifPresent(e -> {
				projectAccessService.checkAccessLevel(e.getProjectId(), ProjectAccessLevel.Write);
			});
		}
		experimentRepository.deleteAllById(experimentIds);
	}

	public ExperimentDTO getExperimentById(long experimentId) {
		return experimentRepository.findById(experimentId)
				.filter(e -> projectAccessService.hasAccessLevel(e.getProjectId(), ProjectAccessLevel.Read))
				.map(this::mapToExperimentDTO)
				.orElse(null);
	}

	public List<ExperimentDTO> getExperiments(List<Long> experimentIds) {
		List<Experiment> experiments = new ArrayList<>();
		if (CollectionUtils.isEmpty(experimentIds)) {
			experiments.addAll(experimentRepository.findAll());
		} else {
			experiments.addAll((List<Experiment>) experimentRepository.findAllById(experimentIds));
		}

		List<ExperimentDTO> experimentDTOs = experiments.stream()
				.filter(e -> projectAccessService.hasAccessLevel(e.getProjectId(), ProjectAccessLevel.Read))
				.map(this::mapToExperimentDTO)
				.toList();

		enrichWithMetadata(experimentDTOs);

		return experimentDTOs;
	}

	/**
	 * Get n most recently created experiments
	 * @param n number of experiments
	 * @return n most recently created experiments
	 */
	public List<ExperimentDTO> getNMostRecentExperiments(int n) {
		List<Experiment> result = experimentRepository.findNMostRecentExperiments(n);
		return result.stream()
				.filter(e -> projectAccessService.hasAccessLevel(e.getProjectId(), ProjectAccessLevel.Read))
				.map(this::mapToExperimentDTO)
				.toList();
	}

	public List<ExperimentDTO> getExperimentByProjectId(long projectId) {
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);
		List<Experiment> experiments = experimentRepository.findByProjectId(projectId);
		List<ExperimentDTO> experimentDTOs = experiments.stream()
				.map(this::mapToExperimentDTO)
				.toList();

		enrichWithMetadata(experimentDTOs);

		return experimentDTOs;
	}

	public List<ExperimentDTO> getExperimentByProjectIds(List<Long> projectIds) {
		List<ExperimentDTO> experimentDTOs = Optional.ofNullable(experimentRepository.findByProjectIds(projectIds))
				.orElseGet(Collections::emptyList)
				.stream()
				.filter(e -> projectAccessService.hasAccessLevel(e.getProjectId(), ProjectAccessLevel.Read))
				.map(this::mapToExperimentDTO)
				.toList();

		enrichWithMetadata(experimentDTOs);

		return experimentDTOs;
	}

	public List<ExperimentSummaryDTO> getExperimentSummaries() {
		List<ExperimentSummaryDTO> result = plateRepository.findExperimentSummaries();
		return result;
	}

	public List<ExperimentSummaryDTO> getExperimentSummaryInExperimentIds(Set<Long> experimentIds) {
		List<ExperimentSummaryDTO> result = plateRepository.findExperimentSummariesInExperimentIds(experimentIds);
		return result;
	}

	public List<ExperimentSummaryDTO> getExperimentSummariesByProjectId(long projectId) {
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);
		List<ExperimentDTO> experiments = getExperimentByProjectId(projectId);
		Set<Long> experimentIds = experiments.stream().map(e -> e.getId()).collect(Collectors.toSet());
		List<ExperimentSummaryDTO> result = plateRepository.findExperimentSummariesInExperimentIds(experimentIds);
		return result;
	}

	public ExperimentSummaryDTO getExperimentSummaryByExperimentId(Long experimentId) {
		return plateRepository.findExperimentSummaryByExperimentId(experimentId);
	}

	private ExperimentDTO mapToExperimentDTO(Experiment experiment) {
		ExperimentDTO experimentDTO = new ExperimentDTO();
		modelMapper.typeMap(Experiment.class, ExperimentDTO.class)
				.map(experiment, experimentDTO);
		return experimentDTO;
	}

	private void enrichWithMetadata(List<ExperimentDTO> experiments) {
		if (CollectionUtils.isNotEmpty(experiments)) {
			// Create a map of experiment ID to ExperimentDTO for quick lookup
			Map<Long, ExperimentDTO> experimentMap = new HashMap<>();
			List<Long> experimentIds = new ArrayList<>(experiments.size());
			for (ExperimentDTO experiment : experiments) {
				experimentMap.put(experiment.getId(), experiment);
				experimentIds.add(experiment.getId());
			}

			// Retrieve the metadata using the list of experiment IDs
			List<MetadataDTO> experimentsMetadata = metadataServiceGraphQlClient.getMetadata(experimentIds, ObjectClass.EXPERIMENT);
			for (MetadataDTO metadata : experimentsMetadata) {
				ExperimentDTO experiment = experimentMap.get(metadata.getObjectId());
				if (experiment != null) {
					experiment.setTags(metadata.getTags().stream().map(TagDTO::getTag).collect(Collectors.toList()));
					List<eu.openanalytics.phaedra.plateservice.dto.PropertyDTO> propertyDTOs = new ArrayList<>(metadata.getProperties().size());
					for (eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO property : metadata.getProperties()) {
						propertyDTOs.add(new eu.openanalytics.phaedra.plateservice.dto.PropertyDTO(property.getPropertyName(), property.getPropertyValue()));
					}
					experiment.setProperties(propertyDTOs);
				}
			}
		}
	}
}
