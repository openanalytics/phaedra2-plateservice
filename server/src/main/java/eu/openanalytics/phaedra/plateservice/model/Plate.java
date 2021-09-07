package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.platservice.enumartion.ApprovalStatus;
import eu.openanalytics.phaedra.platservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.platservice.enumartion.LinkStatus;
import eu.openanalytics.phaedra.platservice.enumartion.UploadStatus;
import eu.openanalytics.phaedra.platservice.enumartion.ValidationStatus;
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

	@Column("approval_status")
	private ApprovalStatus approvalStatus;
	@Column("approved_by")
	private String approvedBy;
	@Column("approved_on")
	private Date approvedOn;

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
