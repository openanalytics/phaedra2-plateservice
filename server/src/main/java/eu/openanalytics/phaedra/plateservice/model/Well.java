package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.platservice.enumartion.WellStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

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

	public Well(Long plateId) {
		this.plateId = plateId;
	}
}
