package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WellSubstanceDTO {
    private Long id;
    private String type;
    private String name;
    private Double concentration = 0.0;
    private Long wellId;
}
