package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvalidatePlatesDTO {
    List<Long> plateIds;
    String reason;
}
