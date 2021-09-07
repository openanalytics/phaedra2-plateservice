package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.ProjectService;
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.platservice.dto.ProjectDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
@RestController
public class ProjectController {

	private final ProjectService projectService;
	
	private final ExperimentService experimentService;

	public ProjectController(ProjectService projectService, ExperimentService experimentService) {
		this.projectService = projectService;
		this.experimentService = experimentService;
	}

	@PostMapping(value="/project", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
		ProjectDTO response = projectService.createProject(projectDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping(value="/project", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateProject(@RequestBody ProjectDTO projectDTO) {
		projectService.updateProject(projectDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value="/projects", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectDTO>> getProjects() {
		List<ProjectDTO> response = projectService.getAllProjects();
		if (CollectionUtils.isNotEmpty(response))
			return new ResponseEntity<>(response, HttpStatus.OK);
		else
			return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value="/project/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {
		projectService.deleteProject(projectId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value="/project/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectDTO> getProject(@PathVariable long projectId) {
		ProjectDTO response = projectService.getProjectById(projectId);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value="/project/{projectId}/experiments", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExperimentDTO>> getExperiments(@PathVariable long projectId) {
		List<ExperimentDTO> experiments = experimentService.getExperimentByProjectId(projectId);
		return ResponseEntity.ok(experiments);
	}
	
//	@PostMapping(value="/project/{projectId}/experiment", produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<ExperimentDTO> createExperiment(@PathVariable Long projectId, @RequestBody ExperimentDTO newExperiment) {
////		if (!experimentService.experimentExists(projectId)) return ResponseEntity.notFound().build();
////		experiment.setProjectId(projectId);
//		Experiment newExperiment = experimentService.createExperiment(experiment);
//		return new ResponseEntity<>(newExperiment, HttpStatus.CREATED);
//	}
}