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
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
public class ExperimentGraphQLController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

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
			result.stream().forEach(experimentDTO -> {
				addExperimentMetadata(experimentDTO);
			});
		}
		return result;
	}

	@QueryMapping
	public List<ExperimentDTO> getNMostRecentExperiments(@Argument int n) {
		List<ExperimentDTO> result = experimentService.getNMostRecentExperiments(n);
		if (CollectionUtils.isNotEmpty(result)) {
			result.stream().forEach(experimentDTO -> {
				addExperimentMetadata(experimentDTO);
			});
		}
		return result;
	}

	@QueryMapping
	public ExperimentDTO getExperimentById(@Argument Long experimentId) {
		ExperimentDTO result = experimentService.getExperimentById(experimentId);
		if (result != null) {
			addExperimentMetadata(result);
		}

		return result;
	}

	@QueryMapping
	public List<ExperimentDTO> getExperimentsByProjectId(@Argument Long projectId) {
		List<ExperimentDTO> result = experimentService.getExperimentByProjectId(projectId);
		if (CollectionUtils.isNotEmpty(result)) {
			result.stream().forEach(experimentDTO -> {
				addExperimentMetadata(experimentDTO);
			});
		}
		return result;
	}

	@QueryMapping
	public List<ExperimentSummaryDTO> getExperimentSummaries() {
		return experimentService.getExperimentSummaries();
	}

	@QueryMapping
	public List<ExperimentSummaryDTO> getExperimentSummariesByProjectId(@Argument Long projectId) {
		List<ExperimentSummaryDTO> result = experimentService.getExperimentSummariesByProjectId(projectId);
		return result;
	}

	@QueryMapping
	public ExperimentSummaryDTO getExperimentSummaryByExperimentId(@Argument Long experimentId) {
		List<ExperimentSummaryDTO> result = experimentService.getExperimentSummaryInExperimentIds(Set.of(experimentId));
		return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
	}

	private void addExperimentMetadata(ExperimentDTO experimentDTO) {
		List<TagDTO> tags = metadataServiceClient.getTags(ObjectClass.EXPERIMENT, experimentDTO.getId());
		experimentDTO.setTags(tags.stream().map(tagDTO -> tagDTO.getTag()).toList());

		List<PropertyDTO> porperties = metadataServiceClient.getPorperties(ObjectClass.EXPERIMENT, experimentDTO.getId());
		experimentDTO.setProperties(porperties.stream().map(prop -> new eu.openanalytics.phaedra.plateservice.dto.PropertyDTO(prop.getPropertyName(), prop.getPropertyValue())).toList());

		ExperimentSummaryDTO experimentSummaryDTO = getExperimentSummaryByExperimentId(experimentDTO.getId());
		experimentDTO.setSummary(experimentSummaryDTO);
	}
}
