package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.repository.ExperimentRepository;
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.platservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ApprovalStatus;
import eu.openanalytics.phaedra.platservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.platservice.enumartion.ValidationStatus;

@Service
public class ExperimentService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ExperimentRepository experimentRepository;
	private final PlateService plateService;

	public ExperimentService(ExperimentRepository experimentRepository, PlateService plateService) {
		this.experimentRepository = experimentRepository;
		this.plateService = plateService;
	}

	public ExperimentDTO createExperiment(ExperimentDTO experimentDTO) {
		Experiment experiment = new Experiment();
		modelMapper.typeMap(ExperimentDTO.class, Experiment.class)
				.map(experimentDTO, experiment);

		experiment = experimentRepository.save(experiment);
		return mapToExperimentDTO(experiment);
	}

	public void updateExperiment(ExperimentDTO experimentDTO) {
		Optional<Experiment> experiment = experimentRepository.findById(experimentDTO.getId());
		experiment.ifPresent(e -> {
			modelMapper.typeMap(ExperimentDTO.class, Experiment.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(experimentDTO, e);
			experimentRepository.save(e);
		});
	}
	
	public void deleteExperiment(long experimentId) {
		experimentRepository.deleteById(experimentId);
	}

	public ExperimentDTO getExperimentById(long experimentId) {
		Optional<Experiment> experiment = experimentRepository.findById(experimentId);
		return experiment.map(this::mapToExperimentDTO).orElse(null);
	}

	public List<ExperimentDTO> getAllExperiments() {
		List<Experiment> result = (List<Experiment>) experimentRepository.findAll();
		return result.stream().map(this::mapToExperimentDTO).collect(Collectors.toList());
	}

	public List<ExperimentDTO> getExperimentByProjectId(long projectId) {
		List<Experiment> result = experimentRepository.findByProjectId(projectId);
		return result.stream().map(this::mapToExperimentDTO).collect(Collectors.toList());
	}
	
	public List<ExperimentSummaryDTO> getExperimentSummariesByProjectId(long projectId) {
		return getExperimentByProjectId(projectId).stream().map(exp -> {
			List<PlateDTO> plates = plateService.getPlatesByExperimentId(exp.getId());
			ExperimentSummaryDTO summary = new ExperimentSummaryDTO();
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
