/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.util;

import eu.openanalytics.phaedra.plateservice.model.Plate;

import java.util.Comparator;

public class PlateUtils {

	public static final Comparator<Plate> PLATE_SEQUENCE_SORTER = (p1, p2) -> {
		if (p1 == null && p2 == null) return 0;
		if (p1 == null) return -1;
		return Integer.compare(p1.getSequence(), p2.getSequence());
	};

}
