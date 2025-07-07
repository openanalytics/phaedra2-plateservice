package eu.openanalytics.phaedra.plateservice.enumeration;

public enum LinkType {

	Template,
	PlateDefinition;

	public static LinkType findByName(String name, boolean caseSensitive) {
		for (LinkType type: values()) {
			if (caseSensitive && type.name().equals(name)) {
				return type;
			}
			if (!caseSensitive && type.name().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
}
