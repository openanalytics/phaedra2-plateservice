package eu.openanalytics.phaedra.plateservice.model;

import java.util.Date;

import eu.openanalytics.phaedra.plateservice.enumeration.LinkStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@NoArgsConstructor
public class Plate {
	@Id
	private Long id;
	private String barcode;
	private String description;
	@Column("experiment_id")
	private Long experimentId;

	private Integer rows;
	private Integer columns;
	private Integer sequence;
	
	@Column("link_status")
	private LinkStatus linkStatus;
	@Column("link_date")
	private Date linkDate;
	@Column("link_source")
	private String linkSource;
	@Column("link_template_id")
	private String linkTemplateId;

	@Column("created_on")
	private Date createdOn;
	@Column("created_by")
	private String createdBy;
	@Column("updated_on")
	private Date updatedOn;
	@Column("updated_by")
	private String updatedBy;

}
