package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private Date createdOn = new Date();
    private String createdBy;
    private Date updatedOn = new Date();
    private String updatedBy;
}
