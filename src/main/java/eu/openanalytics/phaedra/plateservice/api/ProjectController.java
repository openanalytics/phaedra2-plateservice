package eu.openanalytics.phaedra.plateservice.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ExperimentService experimentService;
	
	@RequestMapping(value="/projects", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Project> getProjects() {
		return projectService.getAllProjects();
	}
	
	@RequestMapping(value="/project/{projectId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> getProject(@PathVariable long projectId) {
		Optional<Project> project = projectService.getProjectById(projectId);
		return ResponseEntity.of(project);
	}
	
	@RequestMapping(value="/project", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProject(@RequestBody Project project) {
		Project newProject = projectService.createProject(project);
		return new ResponseEntity<>(newProject, HttpStatus.CREATED);
	}

	@RequestMapping(value="/project/{projectId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateProject(@PathVariable long projectId, @RequestBody Project project) {
		if (!projectService.projectExists(projectId)) return ResponseEntity.notFound().build();
		project.setId(projectId);
		projectService.updateProject(project);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/project/{projectId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {
		if (!projectService.projectExists(projectId)) return ResponseEntity.notFound().build();
		projectService.deleteProject(projectId);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/project/{projectId}/experiments", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Experiment>> getExperiments(@PathVariable long projectId) {
		if (!projectService.projectExists(projectId)) return ResponseEntity.notFound().build();
		List<Experiment> experiments = experimentService.getExperimentByProjectId(projectId);
		return ResponseEntity.ok(experiments);
	}
	
	@RequestMapping(value="/project/{projectId}/experiment", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createExperiment(@PathVariable long projectId, @RequestBody Experiment experiment) {
		if (!experimentService.experimentExists(projectId)) return ResponseEntity.notFound().build();
		experiment.setProjectId(projectId);
		Experiment newExperiment = experimentService.createExperiment(experiment);
		return new ResponseEntity<>(newExperiment, HttpStatus.CREATED);
	}
}
