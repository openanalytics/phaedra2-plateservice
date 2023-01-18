/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;

//@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
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
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value="/experiment/{experimentId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExperimentDTO> getExperiment(@PathVariable Long experimentId) {
		ExperimentDTO response = experimentService.getExperimentById(experimentId);
		if (response != null)
			return new ResponseEntity<>(response, HttpStatus.OK);
		else
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value="/experiment/{experimentId}/plates", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlateDTO>> getPlates(@PathVariable long experimentId) {
		// Return 404 if the experiment was not found or not accessible.
		ExperimentDTO experiment = experimentService.getExperimentById(experimentId);
		if (experiment == null) return ResponseEntity.notFound().build();

		List<PlateDTO> plates = plateService.getPlatesByExperimentId(experimentId);
		return ResponseEntity.ok(plates);
	}
}
