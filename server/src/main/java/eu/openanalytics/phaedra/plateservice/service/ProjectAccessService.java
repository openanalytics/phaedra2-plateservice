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
package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.ProjectAccess;
import eu.openanalytics.phaedra.plateservice.repository.ProjectAccessRepository;
import eu.openanalytics.phaedra.plateservice.dto.ProjectAccessDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class ProjectAccessService {

	private static final ModelMapper modelMapper = new ModelMapper();

	private final ProjectAccessRepository projectAccessRepository;
	private final IAuthorizationService authService;

	public ProjectAccessService(ProjectAccessRepository projectAccessRepository, IAuthorizationService authService) {
		this.projectAccessRepository = projectAccessRepository;
		this.authService = authService;
	}

	public void checkCanCreateProjects() {
		authService.performAccessCheck(p -> authService.hasUserAccess(), e -> "Not authorized to create new projects");
	}

	/**
	 * Test if the current principal has an access level on the specified project that
	 * is at least equal to requiredLevel.
	 * If this is not the case, a ResponseStatusException will be thrown.
	 *
	 * @param projectId The ID of the project to check.
	 * @param requiredLevel The minimum access level that is needed.
	 */
	public void checkAccessLevel(long projectId, ProjectAccessLevel requiredLevel) {
		authService.performAccessCheck(principal -> {
			ProjectAccessLevel level = getAccessLevel(projectId);
			return (level != null && level.compareTo(requiredLevel) >= 0);
		}, e -> String.format("Not authorized: requires access level %s on project %d", requiredLevel, projectId));
	}

	/**
	 * Test if the current principal has an access level on the specified project that
	 * is at least equal to requiredLevel.
	 *
	 * @param projectId The ID of the project to check.
	 * @param requiredLevel The minimum access level that is needed.
	 * @return True if the principal has the required access level, false otherwise.
	 */
	public boolean hasAccessLevel(long projectId, ProjectAccessLevel requiredLevel) {
		ProjectAccessLevel level = getAccessLevel(projectId);
		return (level != null && level.compareTo(requiredLevel) >= 0);
	}

	/**
	 * Test if the current principal has access to the specified project, and if so,
	 * what their highest access level is.
	 *
	 * @param projectId The ID of the project to check.
	 * @return The highest access level, or null if the project is not accessible.
	 */
	public ProjectAccessLevel getAccessLevel(long projectId) {
		// Note: administrators automatically have admin-level access to all projects.
		if (authService.hasAdminAccess()) return ProjectAccessLevel.Admin;

		return getProjectAccessForProject(projectId)
			.stream()
			.filter(pa -> authService.hasTeamAccess(pa.getTeamName()))
			.collect(Collectors.reducing(BinaryOperator.maxBy((pa1, pa2) -> pa1.getAccessLevel().compareTo(pa2.getAccessLevel()))))
			.map(ProjectAccessDTO::getAccessLevel)
			.orElse(null);
	}

	/* Basic CRUD */

	@CacheEvict(value = "project_access", key = "#projectAccess?.projectId")
	public ProjectAccessDTO createProjectAccess(ProjectAccessDTO projectAccess) {
		ProjectAccess newAccess = projectAccessRepository.save(modelMapper.map(projectAccess, ProjectAccess.class));
		return modelMapper.map(newAccess, ProjectAccessDTO.class);
	}

	@CacheEvict(value = "project_access", allEntries = true)
	public void deleteProjectAccess(long projectAccessId) {
		projectAccessRepository.deleteById(projectAccessId);
	}

	@Cacheable("project_access")
	public List<ProjectAccessDTO> getProjectAccessForProject(long projectId) {
		return projectAccessRepository.findByProjectId(projectId)
				.stream()
				.map(pa -> modelMapper.map(pa, ProjectAccessDTO.class))
				.toList();
	}
}
