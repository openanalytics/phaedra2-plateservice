package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.platservice.enumartion.WellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WellDTO {
	private Long id;
	private Long plateId;
	private Integer row;
	private Integer column;
	private String wellType;
	private WellStatus status;
	private Long compoundId; // TODO remove?
	private String description;
	private WellSubstanceDTO wellSubstance;
}
