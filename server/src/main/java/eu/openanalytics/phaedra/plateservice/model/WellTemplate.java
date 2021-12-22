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
    //Default EMPTY wellType.
    @Column("well_type")
    @NotNull
    private String wellType = "EMPTY";
    //Default true
    @NotNull
    private boolean skipped = true;
    @Column("substance_name")
    private String substanceName;
    @Column("substance_type")
    private String substanceType;
    private double concentration;
    public WellTemplate(Long plateTemplateId) { this.plateTemplateId = plateTemplateId; }
}