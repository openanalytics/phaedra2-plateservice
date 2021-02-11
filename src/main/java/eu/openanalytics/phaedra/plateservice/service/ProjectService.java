package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ExperimentService experimentService;
	
	public Project createProject(Project project) {
		return projectRepo.save(project);
	}
	
	public boolean projectExists(long projectId) {
		return projectRepo.existsById(projectId);
	}
	
	public List<Project> getAllProjects() {
		return StreamSupport.stream(projectRepo.findAll().spliterator(), false).collect(Collectors.toList());
	}
	
	public Optional<Project> getProjectById(long projectId) {
		return projectRepo.findById(projectId);
	}
	
	public void updateProject(Project project) {
		projectRepo.save(project);
	}
	
	public void deleteProject(long projectId) {
		experimentService.deleteExperimentsByProjectId(projectId);
		projectRepo.deleteById(projectId);
	}

}
