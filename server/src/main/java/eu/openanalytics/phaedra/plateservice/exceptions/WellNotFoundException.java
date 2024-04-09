package eu.openanalytics.phaedra.plateservice.exceptions;

import eu.openanalytics.phaedra.util.exceptionhandling.EntityNotFoundException;

public class WellNotFoundException extends EntityNotFoundException {
    public WellNotFoundException(Long wellId) {
        super(String.format("Well with id %s not found!", wellId));
    }
}
