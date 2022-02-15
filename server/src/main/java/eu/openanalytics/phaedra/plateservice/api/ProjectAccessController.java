/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.service.ProjectAccessService;
import eu.openanalytics.phaedra.plateservice.dto.ProjectAccessDTO;

@RestController
public class ProjectAccessController {

	private final ProjectAccessService projectAccessService;

	public ProjectAccessController(ProjectAccessService projectAccessService) {
		this.projectAccessService = projectAccessService;
	}

	@PostMapping(value="/project-access", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProjectAccess(@RequestBody ProjectAccessDTO projectAccessDTO) {
		ProjectAccessDTO response = projectAccessService.createProjectAccess(projectAccessDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping(value="/project-access/{projectAccessId}")
	public ResponseEntity<Void> deleteProjectAccess(@PathVariable long projectAccessId) {
		projectAccessService.deleteProjectAccess(projectAccessId);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value="/project-access/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectAccessDTO>> getProjectAccess(@PathVariable long projectId) {
		List<ProjectAccessDTO> response = projectAccessService.getProjectAccessForProject(projectId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
