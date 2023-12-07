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

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.ProjectAccessDTO;
import eu.openanalytics.phaedra.plateservice.dto.ProjectDTO;
import eu.openanalytics.phaedra.plateservice.service.ProjectAccessService;
import eu.openanalytics.phaedra.plateservice.service.ProjectService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectGraphQLController {

    private final ProjectService projectService;
    private final ProjectAccessService projectAccessService;
    private final MetadataServiceClient metadataServiceClient;

    public ProjectGraphQLController(ProjectService projectService, ProjectAccessService projectAccessService,
                                    MetadataServiceClient metadataServiceClient) {
        this.projectService = projectService;
        this.projectAccessService = projectAccessService;
        this.metadataServiceClient = metadataServiceClient;
    }

    @QueryMapping
    public List<ProjectDTO> getProjects() {
        List<ProjectDTO> result = projectService.getAllProjects();
        if (CollectionUtils.isNotEmpty(result)) {
			result.stream().forEach(projectDTO -> {
                addProjectMetadata(projectDTO);
            });
        }
        return result;
    }

    @QueryMapping
    public List<ProjectDTO> getNMostRecentlyUpdatedProjects(@Argument int n) {
        List<ProjectDTO> result = projectService.getNMostRecentlyUpdatedProjects(n);
        if (CollectionUtils.isNotEmpty(result)) {
            result.stream().forEach(projectDTO -> {
                addProjectMetadata(projectDTO);
            });
        }
        return result;
    }

    @QueryMapping
    public ProjectDTO getProjectById(@Argument long projectId) {
        ProjectDTO result = projectService.getProjectById(projectId);
        if (result != null) {
            addProjectMetadata(result);
        }
        return result;
    }

    @QueryMapping
    public List<ProjectAccessDTO> getProjectAccess(@Argument long projectId) {
        return projectAccessService.getProjectAccessForProject(projectId);
    }

    private void addProjectMetadata(ProjectDTO projectDTO) {
        List<TagDTO> projectTags = metadataServiceClient.getTags(ObjectClass.PROJECT, projectDTO.getId());
        projectDTO.setTags(projectTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));

        List<PropertyDTO> projectProperties = metadataServiceClient.getPorperties(ObjectClass.PROJECT, projectDTO.getId());
        projectDTO.setProperties(projectProperties.stream().map(prop -> new eu.openanalytics.phaedra.plateservice.dto.PropertyDTO(prop.getPropertyName(), prop.getPropertyValue())).toList());

        List<ProjectAccessDTO> projectAccess = projectAccessService.getProjectAccessForProject(projectDTO.getId());
        projectDTO.setAccess(projectAccess);
    }
}
