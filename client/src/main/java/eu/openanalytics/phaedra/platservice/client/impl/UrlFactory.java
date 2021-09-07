package eu.openanalytics.phaedra.platservice.client.impl;

public class UrlFactory {

    private static final String PLATE_SERVICE = "http://phaedra-plate-service/phaedra/plate-service";

    public static String plate(long plateId) {
        return String.format("%s/plate/%s", PLATE_SERVICE, plateId);
    }

}
