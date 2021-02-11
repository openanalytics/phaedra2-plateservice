package eu.openanalytics.phaedra.plateservice.api;

import java.util.List;

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
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;

@RestController
public class ExperimentController {

	@Autowired
	private ExperimentService experimentService;
	
	@Autowired
	private PlateService plateService;

	@RequestMapping(value="/experiment/{experimentId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Experiment> getExperiment(@PathVariable long experimentId) {
		return ResponseEntity.of(experimentService.getExperimentById(experimentId));
	}
	
	@RequestMapping(value="/experiment/{experimentId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateExperiment(@PathVariable long experimentId, @RequestBody Experiment experiment) {
		if (!experimentService.experimentExists(experimentId)) return ResponseEntity.notFound().build();
		experiment.setId(experimentId);
		experimentService.updateExperiment(experiment);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/experiment/{experimentId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteExperiment(@PathVariable long experimentId) {
		if (experimentId <= 0) return ResponseEntity.notFound().build();
		experimentService.deleteExperiment(experimentId);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/experiment/{experimentId}/plates", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Plate>> getPlates(@PathVariable long experimentId) {
		List<Plate> plates = plateService.getPlatesByExperimentId(experimentId);
		if (plates.isEmpty()) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(plates);
	}

	@RequestMapping(value="/experiment/{experimentId}/plate", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPlate(@PathVariable long experimentId, @RequestBody Plate plate) {
		if (!experimentService.experimentExists(experimentId)) return ResponseEntity.notFound().build();
		plate.setExperimentId(experimentId);
		Plate newPlate = plateService.createPlate(plate);
		return new ResponseEntity<>(newPlate, HttpStatus.CREATED);
	}
}
