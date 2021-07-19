package eu.openanalytics.phaedra.plateservice.model;

import java.util.Date;
import java.util.Set;

import eu.openanalytics.phaedra.plateservice.enumeration.ExperimentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@NoArgsConstructor
@Table("hca_experiment")
public class Experiment {
	@Id
	private Long id;
	private String name;
	private String description;
	@Column("project_id")
	private Long projectId;
	private ExperimentStatus status = ExperimentStatus.OPEN;
	@Column("multiplo_method")
	private String multiploMethod;
	@Column("multiplo_parameter")
	private String multiploParameter;
	@Column("created_on")
	private Date createdOn;
	@Column("created_by")
	private String createdBy;
	@Column("updated_on")
	private Date updatedOn;
	@Column("updated_by")
	private String updatedBy;

	private Set<ExperimentProperty> properties;
}
