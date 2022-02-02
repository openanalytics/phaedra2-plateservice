package eu.openanalytics.phaedra.plateservice.client;

import eu.openanalytics.phaedra.plateservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;

public interface PlateServiceClient {

    PlateDTO getPlate(long plateId, String... authToken) throws PlateUnresolvableException;

}
