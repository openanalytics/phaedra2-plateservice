package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.repository.ExperimentRepository;
import eu.openanalytics.phaedra.util.ObjectCopyUtils;

@Service
public class ExperimentService {

	@Autowired
	private ExperimentRepository experimentRepo;
	
	@Autowired
	private PlateService plateService;
	
	public Experiment createExperiment(Experiment experiment) {
		return experimentRepo.save(experiment);
	}
	
	public boolean experimentExists(long experimentId) {
		return experimentRepo.existsById(experimentId);
	}
	
	public Optional<Experiment> getExperimentById(long experimentId) {
		return experimentRepo.findById(experimentId);
	}
	
	public List<Experiment> getExperimentByProjectId(long projectId) {
		return experimentRepo.findByProjectId(projectId);
	}
	
	public void updateExperiment(Experiment updatedExperiment) {
		Experiment experiment = getExperimentById(updatedExperiment.getId()).get();
		ObjectCopyUtils.copyNonNullValues(updatedExperiment, experiment);
		experimentRepo.save(experiment);
	}
	
	public void deleteExperiment(long experimentId) {
		plateService.deletePlatesByExperimentId(experimentId);
		experimentRepo.deleteById(experimentId);
	}
	
	public void deleteExperimentsByProjectId(long projectId) {
		plateService.deletePlatesByProjectId(projectId);
		experimentRepo.deleteByProjectId(projectId);
	}
}
