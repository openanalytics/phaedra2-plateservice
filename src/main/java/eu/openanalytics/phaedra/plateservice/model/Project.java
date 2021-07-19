package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

@Data
@NoArgsConstructor
@Table("hca_project")
public class Project {

    @Id
    private Long id;
    private String name;
    private String description;
    @Column("created_on")
    private Date createdOn;
    @Column("created_by")
    private String createBy;
    @Column("updated_on")
    private Date updatedOn;
    @Column("updated_by")
    private String updatedBy;
}
