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

public enum CalculationStatus {
    CALCULATION_NEEDED(0),
    CALCULATION_OK(1),
    CALCULATION_IN_PROGRESS(2),
    CALCULATION_NOT_POSSIBLE(-1),
    CALCULATION_ERROR(-2),
    ;

    private int code;

    CalculationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CalculationStatus getByCode(int code) {
        for (CalculationStatus s: CalculationStatus.values()) {
            if (s.getCode() == code) return s;
        }
        return null;
    }

}
