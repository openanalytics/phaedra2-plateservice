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
package eu.openanalytics.phaedra.plateservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
@EnableKafka
public class KafkaConfig {
    // Kafka topics
    public static final String CALCULATIONS_TOPIC = "calculations";
    public static final String PLATE_TOPIC = "plate-topic";

    // Kafka events
    public static final String UPDATE_PLATE_STATUS_EVENT = "updatePlateCalculationStatus";
    public static final String PLATE_CALCULATION_EVENT = "plateCalculationEvent";

    @Bean
    public RecordFilterStrategy<String, Object> keyFilterStrategy() {
        RecordFilterStrategy<String, Object> recordFilterStrategy = consumerRecord -> !(consumerRecord.key().equalsIgnoreCase(UPDATE_PLATE_STATUS_EVENT));
        return recordFilterStrategy;
    }

}
