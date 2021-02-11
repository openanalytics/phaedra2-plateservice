package eu.openanalytics.phaedra.plateservice.util;

import java.util.Comparator;

import eu.openanalytics.phaedra.plateservice.model.Plate;

public class PlateUtils {

	public static final Comparator<Plate> PLATE_SEQUENCE_SORTER = (p1, p2) -> {
		if (p1 == null && p2 == null) return 0;
		if (p1 == null) return -1;
		return Integer.compare(p1.getSequence(), p2.getSequence());
	};

}
