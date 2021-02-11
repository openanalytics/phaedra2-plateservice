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
