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

import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.EVENT_UPDATE_PLATE_STATUS;
import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.GROUP_ID;
import static eu.openanalytics.phaedra.plateservice.config.KafkaConfig.TOPIC_PLATES;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.dto.PlateCalculationStatusDTO;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;

@Service
public class KafkaConsumerService {
	
    private final PlateRepository plateRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KafkaConsumerService(PlateRepository plateRepository) {
        this.plateRepository = plateRepository;
    }

    @KafkaListener(topics = TOPIC_PLATES, groupId = GROUP_ID, filter = "keyFilterStrategy")
    public void onUpdatePlateCalculationStatus(PlateCalculationStatusDTO plateCalcStatusDTO, @Header(KafkaHeaders.RECEIVED_KEY) String msgKey) {
    	if (!EVENT_UPDATE_PLATE_STATUS.equals(msgKey)) return;
    	
        logger.info("plate-service: receiving message on event " + msgKey);
        Optional<Plate> result = plateRepository.findById(plateCalcStatusDTO.getPlateId());
        if (result.isPresent()) {
            logger.info("Set plate calculation status to " + plateCalcStatusDTO.getCalculationStatus().name() + " for plateId " + plateCalcStatusDTO.getPlateId());
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
}
