/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
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
import eu.openanalytics.phaedra.plateservice.dto.event.PlateDefinitionLinkEvent;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateMeasurementLinkEvent;
import eu.openanalytics.phaedra.plateservice.dto.event.PlateModificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notifyPlateModified(PlateModificationEvent event) {
        kafkaTemplate.send(KafkaConfig.TOPIC_PLATES, KafkaConfig.EVENT_NOTIFY_PLATE_MODIFIED, event);
    }

    public void notifyPlateMeasLinked(PlateMeasurementLinkEvent plateMeasLink) {
        kafkaTemplate.send(KafkaConfig.TOPIC_PLATES, KafkaConfig.EVENT_NOTIFY_PLATE_MEAS_LINKED, plateMeasLink);
    }

    public void notifyPlateDefinitionLinked(PlateDefinitionLinkEvent plateDefinitionLink) {
        kafkaTemplate.send(KafkaConfig.TOPIC_PLATES, KafkaConfig.EVENT_NOTIFY_PLATE_DEFINITION_LINKED, plateDefinitionLink);
    }
}
