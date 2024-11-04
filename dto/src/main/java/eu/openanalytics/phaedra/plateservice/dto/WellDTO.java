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
package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import eu.openanalytics.phaedra.plateservice.record.ExperimentProjection;
import eu.openanalytics.phaedra.plateservice.record.PlateProjection;
import eu.openanalytics.phaedra.plateservice.record.ProjectProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import lombok.With;

@Builder
@With
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WellDTO {
	private Long id;
	private Long plateId;
	private Integer row;
	private Integer column;
	private Integer wellNr;
	private String wellType;
	private WellStatus status;
	private String description;

	private List<String> tags;
	private List<PropertyDTO> properties;

	private WellSubstanceDTO wellSubstance;
	private Map<Long, Float> featureValues;

	private PlateProjection plate;
	private ExperimentProjection experiment;
	private ProjectProjection project;
}
