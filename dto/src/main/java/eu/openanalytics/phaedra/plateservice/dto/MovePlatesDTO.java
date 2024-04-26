package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovePlatesDTO {
    private List<Long> plateIds;
    private Long experimentId;
}
