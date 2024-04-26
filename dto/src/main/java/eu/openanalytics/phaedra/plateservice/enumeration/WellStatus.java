/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

public enum WellStatus {
    ACCEPTED_DEFAULT(0, "Accepted (default)",
            "The data point is accepted, and will be included in all calculations and uploads"),
    ACCEPTED(1, "Accepted",
            "The data point is accepted, and will be included in all calculations and uploads"),
    REJECTED_PLATEPREP(-1, "Rejected (plate preparation)",
            "The data point will be excluded from all calculations and will not be visible in other systems"),
    REJECTED_DATACAPTURE(-2, "Rejected (data capture)",
            "The data point will be excluded from all calculations and will not be visible in other systems"),
    REJECTED_PHAEDRA(-4, "Rejected (biological outlier)",
            "The data point will be excluded from all calculations and will be marked as an outlier in other systems"),
    REJECTED_OUTLIER_PHAEDRA_MANUAL(-8, "Rejected (technical issue)",
            "The data point will be excluded from all calculations and will not be visible in other systems"),
    REJECTED_OUTLIER_PHAEDRA_AUTO(-9, "Rejected (technical issue)",
            "The data point will be excluded from all calculations and will not be visible in other systems")
    ;

    private int code;
    private String label;
    private String description;

    WellStatus(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public static WellStatus getByCode(int code) {
        for (WellStatus s: WellStatus.values()) {
            if (s.getCode() == code) return s;
        }
        return null;
    }

    public static boolean isRejected(WellStatus wellStatus) {
        return wellStatus.getCode() < 0;
    }
}
