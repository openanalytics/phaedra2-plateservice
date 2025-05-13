/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import eu.openanalytics.phaedra.plateservice.record.ExperimentProjection;
import eu.openanalytics.phaedra.plateservice.record.PlateProjection;
import eu.openanalytics.phaedra.plateservice.record.ProjectProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Table("hca_well")
public class Well {
	@Id
	@NotNull
	private Long id;
	@Column("plate_id")
	@NotNull
	private Long plateId;
	@NotNull
	private Integer row;
	@NotNull
	private Integer column;
	@Column("welltype")
	private String wellType;

	private WellStatus status = WellStatus.ACCEPTED_DEFAULT;

	@Column("compound_id")
	private Long compoundId;
	private String description;

	@Transient
	private PlateProjection plate;
	@Transient
	private ExperimentProjection experiment;
	@Transient
	private ProjectProjection project;

	public Well(Long plateId) {
		this.plateId = plateId;
	}
}
