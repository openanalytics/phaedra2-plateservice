package eu.openanalytics.phaedra.platservice.client.impl;

import eu.openanalytics.phaedra.platservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.platservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    public PlateDTO getPlate(long plateId, String... authToken) throws PlateUnresolvableException {
        // 1. get plate
        try {
            String token = ArrayUtils.isNotEmpty(authToken) ? authToken[0] : null;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
            HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

            var plate = restTemplate.exchange(UrlFactory.plate(plateId), HttpMethod.GET, httpEntity, PlateDTO.class);
//            var plate = restTemplate.getForObject(UrlFactory.plate(plateId), PlateDTO.class);
            if (plate.getStatusCode().isError()) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            return plate.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }

}
