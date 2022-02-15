/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import eu.openanalytics.phaedra.plateservice.model.Well;

public class WellUtils {

	public static final Comparator<Well> WELL_POSITION_SORTER = (w1, w2) -> {
		if (w1 == null && w2 == null) return 0;
		if (w1 == null) return -1;
		if (w1.getRow() == w2.getRow()) return Integer.compare(w1.getColumn(), w2.getColumn());
		return Integer.compare(w1.getRow(), w2.getRow());
	};
	
	public static <T extends Well> Optional<T> findWell(Collection<T> wells, int row, int column) {
		return wells.stream().filter(w -> w.getRow() == row && w.getColumn() == column).findAny();
	}

}
