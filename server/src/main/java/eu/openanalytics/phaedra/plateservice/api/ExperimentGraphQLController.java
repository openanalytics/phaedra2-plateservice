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
import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ExperimentGraphQLController {

	private final ExperimentService experimentService;

	public ExperimentGraphQLController(ExperimentService experimentService) {
		this.experimentService = experimentService;
	}

	@QueryMapping
	public List<ExperimentDTO> getExperiments() {
		return experimentService.getAllExperiments();
	}

	@QueryMapping
	public ExperimentDTO getExperimentById(@Argument long experimentId) {
		return experimentService.getExperimentById(experimentId);
	}

	@QueryMapping
	public List<ExperimentDTO> getExperimentsByProjectId(@Argument long projectId) {
		return experimentService.getExperimentByProjectId(projectId);
	}

	@QueryMapping
	public List<ExperimentSummaryDTO> getExperimentSummaries(@PathVariable long projectId) {
		return experimentService.getExperimentSummariesByProjectId(projectId);
	}
}
