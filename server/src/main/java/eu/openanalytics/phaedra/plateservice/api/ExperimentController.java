/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experiments")
public class ExperimentController {

	private final ExperimentService experimentService;
	private final PlateService plateService;
	private final MetadataServiceClient metadataServiceClient;

	public ExperimentController(ExperimentService experimentService, PlateService plateService, MetadataServiceClient metadataServiceClient) {
		this.experimentService = experimentService;
		this.plateService = plateService;
		this.metadataServiceClient = metadataServiceClient;
	}

	@PostMapping
	public ResponseEntity<ExperimentDTO> createExperiment(@RequestBody ExperimentDTO experimentDTO) {
		ExperimentDTO response = experimentService.createExperiment(experimentDTO);

		if (CollectionUtils.isNotEmpty(experimentDTO.getTags())) {
			metadataServiceClient.addTags(ObjectClass.EXPERIMENT.name(), response.getId(), experimentDTO.getTags());
		}

		if (CollectionUtils.isNotEmpty(experimentDTO.getProperties())) {
			Map<String, String> properties = experimentDTO.getProperties().stream().collect(Collectors.toMap(PropertyDTO::propertyName, PropertyDTO::propertyValue));
			metadataServiceClient.addProperties(ObjectClass.EXPERIMENT.name(), response.getId(), properties);
		}

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
		List<ExperimentDTO> response = experimentService.getExperiments(Collections.emptyList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value="/{experimentId}")
	public ResponseEntity<Void> deleteExperiment(@PathVariable long experimentId) {
		experimentService.deleteExperiment(experimentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<Void> deleteExperiments(@RequestBody List<Long> experimentIds) {
		experimentService.deleteExperiments(experimentIds);
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

	@PutMapping(value="/{experimentId}/plates/link/{plateTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlateDTO>> setPlateTemplate(@PathVariable long experimentId, @PathVariable long plateTemplateId) {
		// Return 404 if the experiment was not found or not accessible.
		ExperimentDTO experiment = experimentService.getExperimentById(experimentId);
		if (experiment == null) return ResponseEntity.notFound().build();

		List<PlateDTO> plates = plateService.linkPlates(experimentId, plateTemplateId);
		return ResponseEntity.ok(plates);
	}
}
