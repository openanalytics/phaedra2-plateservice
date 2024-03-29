package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class LinkPlateMeasurementDTO {
    List<Long> plateIds;
    Long measurementId;
}
