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

import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiments")
public class ExperimentController {

	@Autowired
	private ExperimentService experimentService;

	@Autowired
	private PlateService plateService;

	@PostMapping
	public ResponseEntity<ExperimentDTO> createExperiment(@RequestBody ExperimentDTO experimentDTO) {
		ExperimentDTO response = experimentService.createExperiment(experimentDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{experimentId}")
	public ResponseEntity<Void> updateExperiment(@PathVariable long experimentId, @RequestBody ExperimentDTO experimentDTO) {
		experimentDTO.setId(experimentId);
		experimentService.updateExperiment(experimentDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ExperimentDTO>> getAllExperiments() {
		List<ExperimentDTO> response = experimentService.getAllExperiments();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value="/{experimentId}")
	public ResponseEntity<Void> deleteExperiment(@PathVariable long experimentId) {
		experimentService.deleteExperiment(experimentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value="/{experimentId}")
	public ResponseEntity<ExperimentDTO> getExperiment(@PathVariable Long experimentId) {
		ExperimentDTO response = experimentService.getExperimentById(experimentId);
		if (response == null) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@GetMapping(value="/{experimentId}/plates", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlateDTO>> getPlates(@PathVariable long experimentId) {
		// Return 404 if the experiment was not found or not accessible.
		ExperimentDTO experiment = experimentService.getExperimentById(experimentId);
		if (experiment == null) return ResponseEntity.notFound().build();

		List<PlateDTO> plates = plateService.getPlatesByExperimentId(experimentId);
		return ResponseEntity.ok(plates);
	}
}
