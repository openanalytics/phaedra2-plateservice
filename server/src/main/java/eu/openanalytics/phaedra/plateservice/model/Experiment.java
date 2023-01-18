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
package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.plateservice.enumartion.ExperimentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Table("hca_experiment")
public class Experiment {
	@Id
	@NotNull
	private Long id;
	@NotNull
	private String name;
	private String description;
	@NotNull
	private ExperimentStatus status = ExperimentStatus.OPEN;
	@Column("project_id")
	@NotNull
	private Long projectId;
	@Column("multiplo_method")
	private String multiploMethod;
	@Column("multiplo_parameter")
	private String multiploParameter;
	@Column("created_on")
	private Date createdOn = new Date();
	@Column("created_by")
	private String createdBy;
	@Column("updated_on")
	private Date updatedOn = new Date();
	@Column("updated_by")
	private String updatedBy;
}
