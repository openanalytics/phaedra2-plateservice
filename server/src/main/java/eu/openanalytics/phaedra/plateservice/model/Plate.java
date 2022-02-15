/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.enumartion.ApprovalStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.LinkStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.UploadStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.ValidationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Table("hca_plate")
public class Plate {
	@Id
	@NotNull
	private Long id;
	private String barcode;
	private String description;
	@Column("experiment_id")
	@NotNull
	private Long experimentId;

	@NotNull
	private Integer rows;
	@NotNull
	private Integer columns;
	@NotNull
	private Integer sequence;


	@Column("link_status")
	private LinkStatus linkStatus;
	@Column("link_source")
	private String linkSource;
	@Column("link_template_id")
	private String linkTemplateId;
	@Column("linked_on")
	private Date linkedOn;

	@Column("calculation_status")
	private CalculationStatus calculationStatus;
	@Column("calculation_error")
	private String calculationError;
	@Column("calculated_by")
	private String calculatedBy;
	@Column("calculated_on")
	private Date calculatedOn;

	@Column("validation_status")
	private ValidationStatus validationStatus;
	@Column("validated_by")
	private String validatedBy;
	@Column("validated_on")
	private Date validatedOn;
	@Column("invalidated_reason")
	private String invalidatedReason;

	@Column("approval_status")
	private ApprovalStatus approvalStatus;
	@Column("approved_by")
	private String approvedBy;
	@Column("approved_on")
	private Date approvedOn;
	@Column("disapproved_reason")
	private String disapprovedReason;

	@Column("upload_status")
	private UploadStatus uploadStatus;
	@Column("uploaded_by")
	private String uploadedBy;
	@Column("uploaded_on")
	private Date uploadedOn;

	@Column("created_on")
	private Date createdOn;
	@Column("created_by")
	private String createdBy;
	@Column("updated_on")
	private Date updatedOn;
	@Column("updated_by")
	private String updatedBy;

}
