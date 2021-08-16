package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.plateservice.enumeration.ExperimentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperimentDTO {
	private Long id;
	private String name;
	private String description;
	private ExperimentStatus status;
	private Long projectId;
	private String multiploMethod;
	private String multiploParameter;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
}
