package eu.openanalytics.phaedra.platservice.client;

import eu.openanalytics.phaedra.platservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;

public interface PlateServiceClient {

    PlateDTO getPlate(long plateId) throws PlateUnresolvableException;

}