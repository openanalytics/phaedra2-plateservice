package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlateTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private Integer rows;
    private Integer columns;
    private Date createdOn;
    private String createdBy;
    private Date updatedOn;
    private String updatedBy;
    private List<WellTemplateDTO> wells;
}
