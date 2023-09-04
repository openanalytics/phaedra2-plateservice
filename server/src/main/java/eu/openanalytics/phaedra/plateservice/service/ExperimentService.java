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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.repository.ExperimentRepository;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.ApprovalStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.enumeration.ValidationStatus;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class ExperimentService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ExperimentRepository experimentRepository;
	private final PlateService plateService;
	private final ProjectAccessService projectAccessService;
	private final IAuthorizationService authService;

	public ExperimentService(ExperimentRepository experimentRepository, @Lazy PlateService plateService,
			ProjectAccessService projectAccessService, IAuthorizationService authService) {

		this.experimentRepository = experimentRepository;
		this.plateService = plateService;
		this.projectAccessService = projectAccessService;
		this.authService = authService;
	}

	public ExperimentDTO createExperiment(ExperimentDTO experimentDTO) {
		Experiment experiment = new Experiment();
		modelMapper.typeMap(ExperimentDTO.class, Experiment.class)
				.map(experimentDTO, experiment);
		experiment.setCreatedBy(authService.getCurrentPrincipalName());
		experiment.setCreatedOn(new Date());

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

	public ExperimentDTO getExperimentById(long experimentId) {
		return experimentRepository.findById(experimentId)
				.filter(e -> projectAccessService.hasAccessLevel(e.getProjectId(), ProjectAccessLevel.Read))
				.map(this::mapToExperimentDTO)
				.orElse(null);
	}

	public List<ExperimentDTO> getAllExperiments() {
		List<Experiment> result = (List<Experiment>) experimentRepository.findAll();
		return result.stream()
				.filter(e -> projectAccessService.hasAccessLevel(e.getProjectId(), ProjectAccessLevel.Read))
				.map(this::mapToExperimentDTO)
				.toList();
	}

	public List<ExperimentDTO> getExperimentByProjectId(long projectId) {
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);
		List<Experiment> result = experimentRepository.findByProjectId(projectId);
		return result.stream().map(this::mapToExperimentDTO).collect(Collectors.toList());
	}

	public List<ExperimentSummaryDTO> getExperimentSummariesByProjectId(long projectId) {
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);
		return getExperimentByProjectId(projectId).stream().map(exp -> {
			List<PlateDTO> plates = plateService.getPlatesByExperimentId(exp.getId());
			ExperimentSummaryDTO summary = new ExperimentSummaryDTO();
			summary.experimentId = exp.getId();
			summary.nrPlates = plates.size();
			summary.nrPlatesCalculated = (int) plates.stream().filter(p -> p.getCalculationStatus() == CalculationStatus.CALCULATION_OK).count();
			summary.nrPlatesValidated = (int) plates.stream().filter(p -> p.getValidationStatus() == ValidationStatus.VALIDATED).count();
			summary.nrPlatesApproved = (int) plates.stream().filter(p -> p.getApprovalStatus() == ApprovalStatus.APPROVED).count();
			return summary;
		}).toList();
	}

	private ExperimentDTO mapToExperimentDTO(Experiment experiment) {
		ExperimentDTO experimentDTO = new ExperimentDTO();
		modelMapper.typeMap(Experiment.class, ExperimentDTO.class)
				.map(experiment, experimentDTO);
		return experimentDTO;
	}
}
