package eu.openanalytics.phaedra.plateservice.api;

import java.util.List;

import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;

@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
@RestController
public class ExperimentController {

	@Autowired
	private ExperimentService experimentService;
	
	@Autowired
	private PlateService plateService;

	@PostMapping(value = "/experiment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExperimentDTO> createExperiment(@RequestBody ExperimentDTO experimentDTO) {
		ExperimentDTO response = experimentService.createExperiment(experimentDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping(value = "/experiment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateExperiment(@RequestBody ExperimentDTO experimentDTO) {
		experimentService.updateExperiment(experimentDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/experiment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExperimentDTO>> getAllExperiments() {
		List<ExperimentDTO> response = experimentService.getAllExperiments();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value="/experiment/{experimentId}")
	public ResponseEntity<Void> deleteExperiment(@PathVariable long experimentId) {
		experimentService.deleteExperiment(experimentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value="/experiment/{experimentId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExperimentDTO> getExperiment(@PathVariable Long experimentId) {
		ExperimentDTO response = experimentService.getExperimentById(experimentId);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		else
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
//	@GetMapping(value="/experiment/{experimentId}/plates", produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<PlateDTO>> getPlates(@PathVariable long experimentId) {
//		List<PlateDTO> response = plateService.getPlatesByExperimentId(experimentId);
//		return ResponseEntity.ok(plates);
//	}
}
