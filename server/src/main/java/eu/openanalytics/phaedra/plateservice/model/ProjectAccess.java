package eu.openanalytics.phaedra.plateservice.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("hca_project_access")
public class ProjectAccess {

	@Id
    @NotNull
    private Long id;
	
    @NotNull
    private Long projectId;
	
    @NotNull
    private String teamName;
	
    @NotNull
	private ProjectAccessLevel accessLevel;
}
