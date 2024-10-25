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
package eu.openanalytics.phaedra.plateservice.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.plateservice.dto.PlateCalculationStatusDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.CustomPlateRepository;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.GROUP_ID;
import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.TOPIC_PLATES;

@Service
public class KafkaConsumerService {

    private final PlateRepository plateRepository;
    private final PlateService plateService;
    private final PlateMeasurementService plateMeasurementService;
    private final IAuthorizationService authService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KafkaConsumerService(PlateRepository plateRepository, PlateService plateService,
    		PlateMeasurementService plateMeasurementService, IAuthorizationService authService) {
        this.plateRepository = plateRepository;
        this.plateService = plateService;
        this.plateMeasurementService = plateMeasurementService;
        this.authService = authService;
    }

    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID + "_reqPlateCalculationStatusUpdate", filter = "reqPlateCalculationStatusUpdateFilter")
    public void reqPlateCalculationStatusUpdate(PlateCalculationStatusDTO plateCalcStatusDTO) {
      Plate plate = ((CustomPlateRepository) plateRepository).findById(plateCalcStatusDTO.getPlateId());
        if (plate != null) {
        	logger.debug(String.format("Setting plate calculation status to %s for plateId %d",
        			plateCalcStatusDTO.getCalculationStatus().name(), plateCalcStatusDTO.getPlateId()));

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

    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID + "_reqPlateMeasLink", filter = "reqPlateMeasLinkFilter", errorHandler = "kafkaErrorHandler")
    public void reqPlateMeasLink(ReqPlateMeasLinkDTO req) {
    	PlateMeasurementDTO linkRequest = PlateMeasurementDTO.builder()
    			.active(true)
    			.plateId(req.getPlateId())
    			.measurementId(req.getMeasurementId())
    			.build();
    	authService.runInKafkaContext(() -> plateMeasurementService.addPlateMeasurement(linkRequest, true));
    }

    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID + "_reqPlateDefLink", filter = "reqPlateDefLinkFilter", errorHandler = "kafkaErrorHandler")
    public void reqPlateDefLink(ReqPlateDefLinkDTO req) {
    	authService.runInKafkaContext(() -> {
            try {
                plateService.linkPlateTemplate(req.getPlateId(), req.getTemplateId());
            } catch (PlateNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReqPlateMeasLinkDTO {
    	private Long plateId;
    	private Long measurementId;
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReqPlateDefLinkDTO {
    	private Long plateId;
    	private Long templateId;
    }
}
