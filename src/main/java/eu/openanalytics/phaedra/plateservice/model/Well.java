package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.WellType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Well {
	
	@Id
	private Long id;
	@Column("plate_id")
	private Long plateId;

	private Integer row;
	private Integer column;

	private String description;

	private WellStatus status;
	
	@Column("well_type")
	private WellType wellType;

	@MappedCollection(idColumn = "well_id")
	private Set<WellTagRef> tags = new HashSet<>();

	public Well(Long plateId) {
		this.plateId = plateId;
	}

	public void addTag(Tag tag) {
		this.tags.add(new WellTagRef(tag.getTagId()));
	}
}
