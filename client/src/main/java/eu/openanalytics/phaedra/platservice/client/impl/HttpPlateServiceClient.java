package eu.openanalytics.phaedra.platservice.client.impl;

import eu.openanalytics.phaedra.platservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.platservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpPlateServiceClient implements PlateServiceClient {

    private final RestTemplate restTemplate;

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

}
