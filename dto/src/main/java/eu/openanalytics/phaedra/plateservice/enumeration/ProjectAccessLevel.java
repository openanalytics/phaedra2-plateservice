/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.enumeration;

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
