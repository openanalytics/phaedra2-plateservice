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

import eu.openanalytics.phaedra.plateservice.dao.ExperimentDAO;
import eu.openanalytics.phaedra.plateservice.dao.ProjectDAO;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.model.Project;

@RestController
public class ProjectController {

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private ExperimentDAO experimentDAO;
	
	@RequestMapping(value="/project/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> getProject(@PathVariable long id) {
		Optional<Project> project = projectDAO.getProject(id);
		return ResponseEntity.of(project);
	}
	
	@RequestMapping(value="/project", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> createProject(@RequestBody Project project) {
		projectDAO.createProject(project);
		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}

	@RequestMapping(value="/project/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> updateProject(@PathVariable long id, @RequestBody Project project) {
		projectDAO.updateProject(id, project);
		return ResponseEntity.ok(project);
	}
	
	@RequestMapping(value="/project/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProject(@PathVariable long id) {
		projectDAO.deleteProject(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/project/{id}/experiments", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Experiment> getExperiments(@PathVariable long id) {
		return experimentDAO.getExperimentsForProject(id);
	}
	
	@RequestMapping(value="/project/{id}/experiment", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Experiment> createExperiment(@PathVariable long id, @RequestBody Experiment experiment) {
		experimentDAO.createExperiment(experiment, id);
		return new ResponseEntity<>(experiment, HttpStatus.CREATED);
	}
}
