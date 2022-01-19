package eu.openanalytics.phaedra.platservice.enumartion;

import java.util.Arrays;

public enum ProjectAccessLevel {

	Read("Permission to view the contents of the project"),
	Write("Permission to modify the contents of the project"),
	Admin("Permission to administer the project");
	
	private String description;
	
	private ProjectAccessLevel(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ProjectAccessLevel getByName(String name) {
		return Arrays.stream(values()).filter(level -> level.name().equals(name)).findAny().orElse(null);
	}
}
