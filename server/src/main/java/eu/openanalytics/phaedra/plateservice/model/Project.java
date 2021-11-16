package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Table("hca_project")
public class Project {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private String name;
    private String description;
    @Column("created_on")
    private Date createdOn = new Date();
    @Column("created_by")
    private String createdBy;
    @Column("updated_on")
    private Date updatedOn = new Date();
    @Column("updated_by")
    private String updatedBy;
}
