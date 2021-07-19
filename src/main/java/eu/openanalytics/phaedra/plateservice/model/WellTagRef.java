package eu.openanalytics.phaedra.plateservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("well_tag")
@Data
@AllArgsConstructor
public class WellTagRef {
    @Column("tag_id")
    private Long tagId;
}
