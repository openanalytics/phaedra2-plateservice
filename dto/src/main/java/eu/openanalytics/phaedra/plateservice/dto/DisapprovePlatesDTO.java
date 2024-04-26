package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class DisapprovePlatesDTO {
    List<Long> plateIds;
    String reason;
}
