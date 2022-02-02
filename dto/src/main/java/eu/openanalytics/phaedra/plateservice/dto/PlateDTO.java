package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.plateservice.enumartion.ApprovalStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.LinkStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.UploadStatus;
import eu.openanalytics.phaedra.plateservice.enumartion.ValidationStatus;
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

	private List<WellDTO> wells;
}
