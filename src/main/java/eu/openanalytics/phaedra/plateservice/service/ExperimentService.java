package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.repository.ExperimentRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	private ExperimentDTO mapToExperimentDTO(Experiment experiment) {
		ExperimentDTO experimentDTO = new ExperimentDTO();
		modelMapper.typeMap(Experiment.class, ExperimentDTO.class)
				.map(experiment, experimentDTO);
		return experimentDTO;
	}
}
