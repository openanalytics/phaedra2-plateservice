/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.service;

import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.EVENT_REQ_PLATE_DEF_LINK;
import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.EVENT_REQ_PLATE_MEAS_LINK;
import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.GROUP_ID;
import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.TOPIC_PLATES;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.JsonPath;

import eu.openanalytics.phaedra.plateservice.dto.PlateCalculationStatusDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;

@Service
public class KafkaConsumerService {
	
    private final PlateRepository plateRepository;
    private final PlateService plateService;
    private final PlateMeasurementService plateMeasurementService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KafkaConsumerService(PlateRepository plateRepository, PlateService plateService, PlateMeasurementService plateMeasurementService) {
        this.plateRepository = plateRepository;
        this.plateService = plateService;
        this.plateMeasurementService = plateMeasurementService;
    }
    
    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID + "_reqPlateCalculationStatusUpdate", filter = "reqPlateCalculationStatusUpdateFilter")
    public void reqPlateCalculationStatusUpdate(PlateCalculationStatusDTO plateCalcStatusDTO) {
        Optional<Plate> result = plateRepository.findById(plateCalcStatusDTO.getPlateId());
        if (result.isPresent()) {
        	logger.debug(String.format("Setting plate calculation status to %s for plateId %d", 
        			plateCalcStatusDTO.getCalculationStatus().name(), plateCalcStatusDTO.getPlateId()));
        	
            Plate plate = result.get();
            plate.setCalculationStatus(plateCalcStatusDTO.getCalculationStatus());
            if (plateCalcStatusDTO.getDetails() != null) {
            	plate.setCalculationError(plateCalcStatusDTO.getDetails());
            }
            plate.setCalculatedOn(new Date());
            plateRepository.save(plate);
        } else {
            logger.error("No plate found with plateId  " + plateCalcStatusDTO.getPlateId());
        }
    }
    
    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID + "_reqPlateMeasLink", filter = "reqPlateMeasLinkFilter")
    public void reqPlateMeasLink(String message) {
    	logger.debug("Received kafka event: " + EVENT_REQ_PLATE_MEAS_LINK);
    	Long plateId = JsonPath.read(message, "$.plateId");
    	Long measId = JsonPath.read(message, "$.measurementId");
    	
    	PlateMeasurementDTO linkRequest = PlateMeasurementDTO.builder()
    			.active(true)
    			.plateId(plateId)
    			.measurementId(measId)
    			.build();
    	
    	PlateMeasurementDTO plateMeasLink = plateMeasurementService.addPlateMeasurement(linkRequest);
        plateMeasurementService.setActivePlateMeasurement(plateMeasLink);
    }
    
    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID + "_reqPlateDefLink", filter = "reqPlateDefLinkFilter")
    public void reqPlateDefLink(String message) {
    	logger.debug("Received kafka event: " + EVENT_REQ_PLATE_DEF_LINK);
    	Long plateId = JsonPath.read(message, "$.plateId");
    	Long templateId = JsonPath.read(message, "$.templateId");
    	plateService.linkPlate(plateId, templateId);
    }
}
