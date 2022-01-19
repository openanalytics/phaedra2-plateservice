package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectAccessDTO {
	
    private Long id;
    private Long projectId;
    private String teamName;
	private ProjectAccessLevel accessLevel;

}
