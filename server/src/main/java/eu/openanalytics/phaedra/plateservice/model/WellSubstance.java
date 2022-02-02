package eu.openanalytics.phaedra.plateservice.model;

import eu.openanalytics.phaedra.plateservice.enumartion.SubstanceType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Data
@Table("hca_well_substance")
public class WellSubstance {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private SubstanceType type;
    @NotNull
    private String name;
    private Double concentration = 0.0;
    @Column("well_id")
    private Long wellId;
}
