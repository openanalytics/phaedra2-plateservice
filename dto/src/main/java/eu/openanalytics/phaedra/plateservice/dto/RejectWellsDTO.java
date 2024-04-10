package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class RejectWellsDTO {
    List<Long> wellIds;
    WellStatusDTO wellStatusDTO;
}
