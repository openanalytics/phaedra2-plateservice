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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.converter.BytesJsonMessageConverter;

@Configuration
@EnableKafka
public class KafkaConfig {
	
	public static final String GROUP_ID = "plate-service";
	
    public static final String TOPIC_PLATES = "plates";

    public static final String EVENT_REQ_PLATE_STATUS_UPDATE = "requestPlateCalculationStatusUpdate";
    public static final String EVENT_REQ_PLATE_MEAS_LINK = "requestPlateMeasurementLink";
    public static final String EVENT_REQ_PLATE_DEF_LINK = "requestPlateDefinitionLink";

    public static final String EVENT_NOTIFY_PLATE_MODIFIED = "notifyPlateModified";
    public static final String EVENT_NOTIFY_PLATE_MEAS_LINKED = "notifyPlateMeasLinked";
    public static final String EVENT_NOTIFY_PLATE_DEFINITION_LINKED = "notifyPlateDefinitionLinked";
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Bean
    public RecordFilterStrategy<String, Object> reqPlateCalculationStatusUpdateFilter() {
        return rec -> !(rec.key().equalsIgnoreCase(EVENT_REQ_PLATE_STATUS_UPDATE));
    }
    
    @Bean
    public RecordFilterStrategy<String, Object> reqPlateMeasLinkFilter() {
        return rec -> !(rec.key().equalsIgnoreCase(EVENT_REQ_PLATE_MEAS_LINK));
    }
    
    @Bean
    public RecordFilterStrategy<String, Object> reqPlateDefLinkFilter() {
        return rec -> !(rec.key().equalsIgnoreCase(EVENT_REQ_PLATE_DEF_LINK));
    }

    @Bean
    public KafkaListenerErrorHandler kafkaErrorHandler() {
    	return (msg, ex) -> {
    		logger.error(String.format("Error occured while handling event"), ex);
    		return null;
    	};
    }
    
    @Bean
    public BytesJsonMessageConverter messageConverter() {
    	return new BytesJsonMessageConverter();
    }
}
