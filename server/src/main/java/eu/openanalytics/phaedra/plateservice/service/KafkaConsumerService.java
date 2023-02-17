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

import eu.openanalytics.phaedra.plateservice.config.KafkaConfig;
import eu.openanalytics.phaedra.plateservice.dto.PlateCalculationStatusDTO;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class KafkaConsumerService {
    @Autowired
    private PlateRepository plateRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    //TODO: Configure kafka consumer security
    @KafkaListener(topics = KafkaConfig.PLATE_TOPIC, groupId = "plate-service", filter = "keyFilterStrategy")
    public void onUpdatePlateCalculationStatus(PlateCalculationStatusDTO plateCalcStatusDTO, @Header(KafkaHeaders.RECEIVED_KEY) String msgKey) {
        if (msgKey.equals(KafkaConfig.PLATE_CALCULATION_EVENT)) {
            Optional<Plate> result = plateRepository.findById(plateCalcStatusDTO.getPlateId());
            if (result.isPresent()) {
                logger.info("Set plate calculation status to " + plateCalcStatusDTO.getCalculationStatus().name() + " for plateId " + plateCalcStatusDTO.getPlateId());
                Plate plate =  result.get();
                plate.setCalculationStatus(plateCalcStatusDTO.getCalculationStatus());
                if (plateCalcStatusDTO.getDetails() != null)
                    plate.setCalculationError(plateCalcStatusDTO.getDetails());
                plate.setCalculatedOn(new Date());
                plateRepository.save(plate);
            } else {
                logger.error("No plate found with plateId  " + plateCalcStatusDTO.getPlateId());
            }
        }
    }
}
