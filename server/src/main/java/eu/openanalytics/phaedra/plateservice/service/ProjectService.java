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
package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.plateservice.dto.ProjectDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.ProjectAccessLevel;
import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.repository.ProjectRepository;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ProjectRepository projectRepository;

	private final ExperimentService experimentService;
	private final ProjectAccessService projectAccessService;
	private final IAuthorizationService authService;

	private final MetadataServiceClient metadataServiceClient;


	public ProjectService(ProjectRepository projectRepository, ExperimentService experimentService,
						  ProjectAccessService projectAccessService, IAuthorizationService authService, MetadataServiceClient metadataServiceClient) {

		this.projectRepository = projectRepository;
		this.experimentService = experimentService;
		this.projectAccessService = projectAccessService;
		this.authService = authService;
		this.metadataServiceClient = metadataServiceClient;
	}

	public ProjectDTO createProject(ProjectDTO projectDTO) {
		projectAccessService.checkCanCreateProjects();

		Project project = new Project();
		modelMapper.typeMap(ProjectDTO.class, Project.class).map(projectDTO, project);
		project.setCreatedBy(authService.getCurrentPrincipalName());
		project.setCreatedOn(new Date());
		project = projectRepository.save(project);

		return mapToProjectDTO(project);
	}

	public void updateProject(ProjectDTO projectDTO) {
		Optional<Project> project = projectRepository.findById(projectDTO.getId());
		project.ifPresent(p -> {
			projectAccessService.checkAccessLevel(p.getId(), ProjectAccessLevel.Write);
			modelMapper.typeMap(ProjectDTO.class, Project.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(projectDTO, p);
			p.setUpdatedBy(authService.getCurrentPrincipalName());
			p.setUpdatedOn(new Date());
			projectRepository.save(p);
		});
	}

	public void deleteProject(long projectId) {
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Admin);
//		experimentService.deleteExperimentsByProjectId(projectId);
		projectRepository.deleteById(projectId);
	}

	public List<ProjectDTO> getAllProjects() {
		List<Project> projects = (List<Project>) projectRepository.findAll();
		return projects.stream()
				.filter(p -> projectAccessService.hasAccessLevel(p.getId(), ProjectAccessLevel.Read))
				.map(this::mapToProjectDTO)
				.collect(Collectors.toList());
	}

	public List<ProjectDTO> getNMostRecentlyUpdatedProjects(int n) {
		List<Project> projects = projectRepository.findNMostRecentlyUpdatedProjects(n);
		return projects.stream()
				.filter(p -> projectAccessService.hasAccessLevel(p.getId(), ProjectAccessLevel.Read))
				.map(this::mapToProjectDTO)
				.collect(Collectors.toList());
	}

	public ProjectDTO getProjectById(long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		return result
				.map(this::mapToProjectDTO)
				.filter(p -> projectAccessService.hasAccessLevel(p.getId(), ProjectAccessLevel.Read))
				.orElse(null);
	}

	private ProjectDTO mapToProjectDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		modelMapper.typeMap(Project.class, ProjectDTO.class).map(project, projectDTO);
		return projectDTO;
	}
}
