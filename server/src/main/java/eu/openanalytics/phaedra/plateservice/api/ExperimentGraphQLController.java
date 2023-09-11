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
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ExperimentGraphQLController {

	private final ExperimentService experimentService;
	private final MetadataServiceClient metadataServiceClient;

	public ExperimentGraphQLController(ExperimentService experimentService, MetadataServiceClient metadataServiceClient) {
		this.experimentService = experimentService;
		this.metadataServiceClient = metadataServiceClient;
	}

	@QueryMapping
	public List<ExperimentDTO> getExperiments() {
		List<ExperimentDTO> result = experimentService.getAllExperiments();
		if (CollectionUtils.isNotEmpty(result)) {
			// Add tags
			result.stream().forEach(experimentDTO -> {
				List<TagDTO> experimentTags = metadataServiceClient.getTags(ObjectClass.EXPERIMENT, experimentDTO.getId());
				experimentDTO.setTags(experimentTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));
			});
		}
		return result;
	}

	@QueryMapping
	public ExperimentDTO getExperimentById(@Argument long experimentId) {
		ExperimentDTO result = experimentService.getExperimentById(experimentId);
		if (result != null) {
			// Add tags
			List<TagDTO> experimentTags = metadataServiceClient.getTags(ObjectClass.EXPERIMENT, result.getId());
			result.setTags(experimentTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));
		}

		return result;
	}

	@QueryMapping
	public List<ExperimentDTO> getExperimentsByProjectId(@Argument long projectId) {
		List<ExperimentDTO> result = experimentService.getExperimentByProjectId(projectId);
		if (CollectionUtils.isNotEmpty(result)) {
			// Add tags
			result.stream().forEach(experimentDTO -> {
				List<TagDTO> experimentTags = metadataServiceClient.getTags(ObjectClass.EXPERIMENT, experimentDTO.getId());
				experimentDTO.setTags(experimentTags.stream().map(tagDTO -> tagDTO.getTag()).collect(Collectors.toList()));
			});
		}
		return result;
	}

	@QueryMapping
	public List<ExperimentSummaryDTO> getExperimentSummaries() {
		return experimentService.getExperimentSummaries();
	}

	@QueryMapping
	public List<ExperimentSummaryDTO> getExperimentSummariesByProjectId(@PathVariable long projectId) {
		return experimentService.getExperimentSummariesByProjectId(projectId);
	}

	@QueryMapping
	public List<ExperimentSummaryDTO> getExperimentSummaryByExperimentId(@Argument long experimentId) {
		return experimentService.getExperimentSummaryInExperimentIds(Set.of(experimentId));
	}
}
