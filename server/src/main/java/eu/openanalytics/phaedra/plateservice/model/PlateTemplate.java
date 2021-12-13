package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table("hca_plate_template")
public class PlateTemplate {
    @Id
    @NotNull
    private long id;
    private String name;
    private String description;
    @NotNull
    private Integer rows;
    @NotNull
    private Integer columns;
    @Column("created_on")
    private Date createdOn;
    @Column("created_by")
    private String createdBy;
    @Column("updated_on")
    private Date updatedOn;
    @Column("updated_by")
    private String updatedBy;
}
