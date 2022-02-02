package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WellTemplateDTO {
    private Long id;
    private String description;
    private Integer row;
    private Integer column;
    private Long plateTemplateId;
    private String wellType = "EMPTY";
    private boolean skipped = true;
    private String substanceName;
    private String substanceType;
    private double concentration;
}