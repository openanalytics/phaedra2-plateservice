package eu.openanalytics.phaedra.platservice.client.impl;

import eu.openanalytics.phaedra.platservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.platservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class HttpPlateServiceClient implements PlateServiceClient {

    private final RestTemplate restTemplate;

    private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow).thenComparing(WellDTO::getColumn);

    public HttpPlateServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PlateDTO getPlate(long plateId) throws PlateUnresolvableException {
        // 1. get plate
        try {
            var plate = restTemplate.getForObject(UrlFactory.plate(plateId), PlateDTO.class);
            if (plate == null) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            return plate;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }

    @Override
    public List<WellDTO> getWellsOfPlateSorted(long plateId) throws PlateUnresolvableException {
        // 1. get wells
        try {
            var wells = restTemplate.getForObject(UrlFactory.wells(plateId), WellDTO[].class);
            if (wells == null) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            // 2. sort wells
            return Arrays.stream(wells).sorted(WELL_COMPARATOR).toList();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }

}
