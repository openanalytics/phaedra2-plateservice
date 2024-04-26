package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class LinkPlateTemplateDTO {
    List<Long> plateIds;
    Long plateTemplateId;
}
