package eu.openanalytics.phaedra.plateservice.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import eu.openanalytics.phaedra.plateservice.enumartion.ProjectAccessLevel;
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
    @Column("project_id")
    private Long projectId;

    @NotNull
    @Column("team_name")
    private String teamName;

    @NotNull
    @Column("access_level")
	private ProjectAccessLevel accessLevel;
}
