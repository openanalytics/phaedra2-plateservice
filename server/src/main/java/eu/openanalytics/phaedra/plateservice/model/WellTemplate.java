package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Table("hca_well_template")
public class WellTemplate {
    @Id
    @NotNull
    private Long id;
    private String description;
    @NotNull
    private Integer row;
    @NotNull
    private Integer column;
    @Column("plate_template_id")
    @NotNull
    private Long plateTemplateId;
    //Standard EMPTY wellType.
    @Column("well_type")
    @NotNull
    private String wellType = "EMPTY";

    @NotNull
    private boolean skipped = true;

    public WellTemplate(Long plateTemplateId) { this.plateTemplateId = plateTemplateId; }
}