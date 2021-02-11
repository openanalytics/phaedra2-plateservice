package eu.openanalytics.phaedra.plateservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table("hca_project")
@JsonInclude(Include.NON_NULL)
public class Project implements IValueObject {

    @Id
    @Column("project_id")
	private Long id;
	
	private String name;
	private String description;
	
	private String owner;
	private String teamCode;
	private String accessScope;
	
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getAccessScope() {
		return accessScope;
	}
	public void setAccessScope(String accessScope) {
		this.accessScope = accessScope;
	}
}
