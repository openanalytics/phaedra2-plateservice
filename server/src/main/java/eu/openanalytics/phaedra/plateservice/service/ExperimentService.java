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
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.platservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ApprovalStatus;
import eu.openanalytics.phaedra.platservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import eu.openanalytics.phaedra.platservice.enumartion.ValidationStatus;
import eu.openanalytics.phaedra.util.auth.AuthorizationHelper;

@Service
public class ExperimentService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ExperimentRepository experimentRepository;
	private final PlateService plateService;
	private final ProjectAccessService projectAccessService;
	
	public ExperimentService(ExperimentRepository experimentRepository, @Lazy PlateService plateService, ProjectAccessService projectAccessService) {
		this.experimentRepository = experimentRepository;
		this.plateService = plateService;
		this.projectAccessService = projectAccessService;
	}

	public ExperimentDTO createExperiment(ExperimentDTO experimentDTO) {
		Experiment experiment = new Experiment();
		modelMapper.typeMap(ExperimentDTO.class, Experiment.class)
				.map(experimentDTO, experiment);
		experiment.setCreatedBy(AuthorizationHelper.getCurrentPrincipalName());
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
			e.setUpdatedBy(AuthorizationHelper.getCurrentPrincipalName());
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
