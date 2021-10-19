package eu.openanalytics.phaedra.platservice.client;

import eu.openanalytics.phaedra.platservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;

import java.util.List;

public interface PlateServiceClient {

    PlateDTO getPlate(long plateId) throws PlateUnresolvableException;

    // TODO this method has become obsolete
    List<WellDTO> getWellsOfPlateSorted(long plateId) throws PlateUnresolvableException;

}