package eu.openanalytics.phaedra.plateservice.exceptions;

import eu.openanalytics.phaedra.util.exceptionhandling.EntityNotFoundException;

public class PlateNotFoundException extends EntityNotFoundException {
    public PlateNotFoundException(long plateId) {
        super(String.format("Plate with id %s not found!", plateId));
    }
}
