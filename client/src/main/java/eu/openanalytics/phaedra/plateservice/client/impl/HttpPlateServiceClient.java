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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.plateservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.resultdataservice.dto.ResultSetDTO;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Component
public class HttpPlateServiceClient implements PlateServiceClient {

    private final RestTemplate restTemplate;
    private final IAuthorizationService authService;
    
    public HttpPlateServiceClient(RestTemplate restTemplate, IAuthorizationService authService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
    }

    @Override
    public PlateDTO getPlate(long plateId) throws PlateUnresolvableException {
        try {
            var plate = restTemplate.exchange(UrlFactory.plate(plateId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), PlateDTO.class);
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
    public List<WellDTO> getWells(long plateId) throws PlateUnresolvableException {
    	try {
            var wells = restTemplate.exchange(UrlFactory.wells(plateId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), WellDTO[].class);
            if (wells.getStatusCode().isError()) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            return Arrays.stream(wells.getBody()).toList();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }
    
    @Override
    public PlateDTO updatePlateCalculationStatus(ResultSetDTO resultSetDTO) throws PlateUnresolvableException {
        try {
            PlateDTO plateDTO = getPlate(resultSetDTO.getPlateId());

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

            var response = restTemplate.exchange(UrlFactory.plate(null), HttpMethod.PUT, new HttpEntity<>(plateDTO, makeHttpHeaders()), PlateDTO.class);
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
    
    private HttpHeaders makeHttpHeaders() {
    	HttpHeaders httpHeaders = new HttpHeaders();
        String bearerToken = authService.getCurrentBearerToken();
    	if (bearerToken != null) httpHeaders.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken));
    	return httpHeaders;
    }
}
