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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExperimentGraphQLController {

	private final ExperimentService experimentService;
	private final PlateService plateService;

	public ExperimentGraphQLController(ExperimentService experimentService, PlateService plateService) {
		this.experimentService = experimentService;
		this.plateService = plateService;
	}

//	@QueryMapping
//	public ResponseEntity<ExperimentDTO> createExperiment(@RequestBody ExperimentDTO experimentDTO) {
//		ExperimentDTO response = experimentService.createExperiment(experimentDTO);
//		return new ResponseEntity<>(response, HttpStatus.CREATED);
//	}

//	@PutMapping(value = "/{experimentId}")
//	public ResponseEntity<Void> updateExperiment(@PathVariable long experimentId, @RequestBody ExperimentDTO experimentDTO) {
//		experimentDTO.setId(experimentId);
//		experimentService.updateExperiment(experimentDTO);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	@QueryMapping
	public List<ExperimentDTO> experiments() {
		return experimentService.getAllExperiments();
	}

//	@DeleteMapping(value="/{experimentId}")
//	public ResponseEntity<Void> deleteExperiment(@PathVariable long experimentId) {
//		experimentService.deleteExperiment(experimentId);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	@QueryMapping
	public ExperimentDTO experimentById(@Argument long experimentId) {
		return experimentService.getExperimentById(experimentId);
	}

	@QueryMapping
	public List<PlateDTO> plates(@Argument long experimentId) {
		return plateService.getPlatesByExperimentId(experimentId);
	}

	@QueryMapping
	public PlateDTO plateById(@Argument long plateId) {
		return plateService.getPlateById(plateId);
	}
}
