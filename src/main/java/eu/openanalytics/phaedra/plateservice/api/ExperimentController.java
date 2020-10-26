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
import eu.openanalytics.phaedra.plateservice.dao.PlateDAO;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.model.Plate;

@RestController
public class ExperimentController {

	@Autowired
	private PlateDAO plateDAO;
	
	@Autowired
	private ExperimentDAO experimentDAO;
	
	@RequestMapping(value="/experiment/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Experiment> getExperiment(@PathVariable long id) {
		Optional<Experiment> exp = experimentDAO.getExperiment(id);
		return ResponseEntity.of(exp);
	}
	
	@RequestMapping(value="/experiment/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Experiment> updateExperiment(@PathVariable long id, @RequestBody Experiment experiment) {
		experimentDAO.updateExperiment(id, experiment);
		return ResponseEntity.ok(experiment);
	}
	
	@RequestMapping(value="/experiment/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteExperiment(@PathVariable long id) {
		experimentDAO.deleteExperiment(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/experiment/{id}/plates", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Plate> getPlates(@PathVariable long id) {
		return plateDAO.getPlatesForExperiment(id);
	}
	
	@RequestMapping(value="/experiment/{id}/plate", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Plate> createPlate(@PathVariable long id, @RequestBody Plate plate) {
		plateDAO.createPlate(plate, id);
		return new ResponseEntity<>(plate, HttpStatus.CREATED);
	}

}
