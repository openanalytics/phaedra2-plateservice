/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.client.impl;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.plateservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.resultdataservice.dto.ResultSetDTO;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Date;

@Component
public class HttpPlateServiceClient implements PlateServiceClient {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow).thenComparing(WellDTO::getColumn);

    public HttpPlateServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PlateDTO getPlate(long plateId, String... authToken) throws PlateUnresolvableException {
        // 1. get plate
        try {
            logger.info("Auth token: " + authToken[0]);
            var plate = restTemplate.exchange(UrlFactory.plate(plateId), HttpMethod.GET, new HttpEntity<String>(getAuthHeaters(authToken)), PlateDTO.class);
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

    @Override
    public PlateDTO updatePlateCalculationStatus(ResultSetDTO resultSetDTO, String... authToken) throws PlateUnresolvableException {
        try {
            logger.info("Auth token: " + authToken[0]);
            PlateDTO plateDTO = getPlate(resultSetDTO.getPlateId(), authToken);

            switch (resultSetDTO.getOutcome()) {
                case SUCCESS:
                    plateDTO.setCalculationStatus(CalculationStatus.CALCULATION_OK);
                    plateDTO.setCalculatedOn(new Date());
                    break;
                case FAILURE:
                    plateDTO.setCalculationStatus(CalculationStatus.CALCULATION_ERROR);
                    plateDTO.setCalculationError(resultSetDTO.getErrorsText());
                    plateDTO.setCalculatedOn(new Date());
                    break;
                default:
                    plateDTO.setCalculationStatus(CalculationStatus.CALCULATION_NEEDED);
                    break;
            };

            var response = restTemplate.exchange(UrlFactory.plate(null), HttpMethod.PUT, new HttpEntity<>(plateDTO, getAuthHeaters(authToken)), PlateDTO.class);
            if (response.getStatusCode().isError()) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException | PlateUnresolvableException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }

    private HttpHeaders getAuthHeaters(String... authToken) {
        String token = ArrayUtils.isNotEmpty(authToken) ? authToken[0] : null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
        return httpHeaders;
    }
}
