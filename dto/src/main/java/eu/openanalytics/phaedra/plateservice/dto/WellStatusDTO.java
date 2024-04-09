package eu.openanalytics.phaedra.plateservice.dto;

import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import lombok.Data;

@Data
public class WellStatusDTO {
    private WellStatus status;
    private String description;
}
