package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.plateservice.enumeration.ExperimentStatus;
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
