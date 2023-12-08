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
package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.plateservice.enumeration.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlateDTO {

	private Long id;
	private String barcode;
	private String description;
	private Long experimentId;

	private Integer rows;
	private Integer columns;
	private Integer sequence;

	private LinkStatus linkStatus;
	private String linkSource;
	private String linkTemplateId;
	private Date linkedOn;

	private CalculationStatus calculationStatus;
	private String calculationError;
	private String calculatedBy;
	private Date calculatedOn;

	private ValidationStatus validationStatus;
	private String validatedBy;
	private Date validatedOn;
	private String invalidatedReason;

	private ApprovalStatus approvalStatus;
	private String approvedBy;
	private Date approvedOn;
	private String disapprovedReason;

	private UploadStatus uploadStatus;
	private String uploadedBy;
	private Date uploadedOn;

	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;

	private List<String> tags;
	private List<PropertyDTO> properties;
}
