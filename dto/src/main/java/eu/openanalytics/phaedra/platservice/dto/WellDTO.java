package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.platservice.enumartion.WellStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WellDTO {
	private Long id;
	private Long plateId;
	private Integer row;
	private Integer column;
	private String welltype;
	private WellStatus status;
	private Long compoundId;
	private String description;
}
