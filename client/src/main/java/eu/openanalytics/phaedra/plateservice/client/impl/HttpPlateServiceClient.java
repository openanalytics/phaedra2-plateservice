/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.plateservice.client.exception.PlateUnresolvableException;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Component
public class HttpPlateServiceClient implements PlateServiceClient {

    private final RestTemplate restTemplate;
    private final IAuthorizationService authService;
    private final UrlFactory urlFactory;
    
    private static final String PROP_BASE_URL = "phaedra.plate-service.base-url";
    private static final String DEFAULT_BASE_URL = "http://phaedra-plate-service:8080/phaedra/plate-service";
    
    public HttpPlateServiceClient(RestTemplate restTemplate, IAuthorizationService authService, Environment environment) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.urlFactory = new UrlFactory(environment.getProperty(PROP_BASE_URL, DEFAULT_BASE_URL));
    }

    @Override
    public PlateDTO getPlate(long plateId) throws PlateUnresolvableException {
        try {
            var plate = restTemplate.exchange(urlFactory.plate(plateId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), PlateDTO.class);
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
            var wells = restTemplate.exchange(urlFactory.wells(plateId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), WellDTO[].class);
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
    public PlateDTO updatePlateCalculationStatus(long plateId, CalculationStatus status, String details) throws PlateUnresolvableException {
        try {
            PlateDTO plateDTO = getPlate(plateId);
            plateDTO.setCalculationStatus(status);
            if (details != null) plateDTO.setCalculationError(details);
            plateDTO.setCalculatedOn(new Date());

            var response = restTemplate.exchange(urlFactory.plate(null), HttpMethod.PUT, new HttpEntity<>(plateDTO, makeHttpHeaders()), PlateDTO.class);
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

    @Override
    public List<PlateMeasurementDTO> getPlateMeasurements(long plateId, String... authToken) throws PlateUnresolvableException {
        try {
            var plateMeasurements = restTemplate.exchange(urlFactory.plateMeasurements(plateId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()),PlateMeasurementDTO[].class);
            if (plateMeasurements.getStatusCode().isError()) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            return Arrays.stream(plateMeasurements.getBody()).toList();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }

    @Override
    public List<WellSubstanceDTO> getWellSubstances(long plateId) throws PlateUnresolvableException {
        try {
            var wellSubstances = restTemplate.exchange(urlFactory.wellSubstances(plateId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), WellSubstanceDTO[].class);
            if (wellSubstances.getStatusCode().isError()) {
                throw new PlateUnresolvableException("Plate could not be converted");
            }

            return Arrays.stream(wellSubstances.getBody()).toList();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PlateUnresolvableException("Plate not found");
        } catch (HttpClientErrorException ex) {
            throw new PlateUnresolvableException("Error while fetching plate");
        }
    }

    @Override
    public List<ExperimentDTO> getExperiments(long projectId) {
    	var response = restTemplate.exchange(urlFactory.experiments(projectId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), ExperimentDTO[].class);
    	return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public List<PlateDTO> getPlatesByBarcode(String barcode) {
    	var response = restTemplate.exchange(urlFactory.platesByBarcode(barcode), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), PlateDTO[].class);
    	return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public List<PlateDTO> getPlatesByExperiment(long experimentId) {
        var response = restTemplate.exchange(urlFactory.experimentPlates(experimentId), HttpMethod.GET, new HttpEntity<>(makeHttpHeaders()), PlateDTO[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public List<PlateTemplateDTO> getPlateTemplatesByName(String name) {
    	var response = restTemplate.exchange(urlFactory.plateTemplatesByName(name), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), PlateTemplateDTO[].class);
    	return Arrays.stream(response.getBody()).toList();
    }
    
    @Override
    public ExperimentDTO createExperiment(String name, long projectId) {
    	try {
    		ExperimentDTO experiment = new ExperimentDTO();
    		experiment.setName(name);
    		experiment.setProjectId(projectId);

            var response = restTemplate.exchange(urlFactory.experiment(null), HttpMethod.POST, new HttpEntity<>(experiment, makeHttpHeaders()), ExperimentDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error while creating experiment");
        }
    }

    @Override
    public PlateDTO createPlate(String barcode, long experimentId, int rows, int columns) {
    	try {
    		PlateDTO plate = new PlateDTO();
    		plate.setBarcode(barcode);
    		plate.setRows(rows);
    		plate.setColumns(columns);
    		plate.setExperimentId(experimentId);

            var response = restTemplate.exchange(urlFactory.plate(null), HttpMethod.POST, new HttpEntity<>(plate, makeHttpHeaders()), PlateDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error while creating plate");
        }
    }
    
    private HttpHeaders makeHttpHeaders() {
    	HttpHeaders httpHeaders = new HttpHeaders();
        String bearerToken = authService.getCurrentBearerToken();
    	if (bearerToken != null) httpHeaders.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken));
    	return httpHeaders;
    }
}
