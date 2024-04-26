package eu.openanalytics.phaedra.plateservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class AcceptWellsDTO {
    List<Long> wellIds;
}
