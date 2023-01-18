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
package eu.openanalytics.phaedra.plateservice.enumartion;

public enum ValidationStatus {
    VALIDATION_NOT_SET(0),
    VALIDATION_NOT_NEEDED(1),
    VALIDATED(2),
    INVALIDATED(-1),
    ;

    private int code;

    ValidationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ValidationStatus getByCode(int code) {
        for (ValidationStatus s: ValidationStatus.values()) {
            if (s.getCode() == code) return s;
        }
        return null;
    }

//    public boolean matches(Plate plate) {
//        return getCode() == plate.getValidationStatus();
//    }
}
