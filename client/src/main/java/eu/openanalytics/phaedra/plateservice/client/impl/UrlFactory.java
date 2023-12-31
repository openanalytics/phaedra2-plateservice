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
package eu.openanalytics.phaedra.plateservice.client.impl;

public class UrlFactory {

    private static final String PLATE_SERVICE = "http://phaedra-plate-service:8080/phaedra/plate-service";

    public static String plate(Long plateId) {
        if (plateId != null)
            return String.format("%s/plates/%s", PLATE_SERVICE, plateId);
        else
            return String.format("%s/plates", PLATE_SERVICE);
    }

    public static String wells(Long plateId) {
        return String.format("%s/plates/%s/wells", PLATE_SERVICE, plateId);
    }

    public static String wellSubstances(Long plateId) {
        return String.format("%s/wellsubstances?plateId=%s", PLATE_SERVICE, plateId);
    }

    public static String plateMeasurements(Long plateId) {
        return String.format("%s/plates/%s/measurements", PLATE_SERVICE, plateId);
    }

    public static String experiments(Long projectId) {
    	return String.format("%s/projects/%s/experiments", PLATE_SERVICE, projectId);
    }

    public static String platesByBarcode(String barcode) {
    	return String.format("%s/plates?barcode=%s", PLATE_SERVICE, barcode);
    }

    public static String experimentPlates(long eperimentId) {
        return String.format("%s/experiments/%s/plates", PLATE_SERVICE, eperimentId);
    }

    public static String plateTemplatesByName(String name) {
    	return String.format("%s/platetemplates?name=%s", PLATE_SERVICE, name);
    }
}
