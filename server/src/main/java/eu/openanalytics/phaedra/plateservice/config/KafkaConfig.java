package eu.openanalytics.phaedra.plateservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
@EnableKafka
public class KafkaConfig {
    public static final String CALCULATIONS_TOPIC = "calculations";
    public static final String PLATE_TOPIC = "plate-topic";
    public static final String UPDATE_PLATE_STATUS_EVENT = "updatePlateCalculationStatus";
    public static final String PLATE_CALCULATION_EVENT = "plateCalculationEvent";

    @Bean
    public RecordFilterStrategy<String, Object> keyFilterStrategy() {
        RecordFilterStrategy<String, Object> recordFilterStrategy = consumerRecord -> !(consumerRecord.key().equalsIgnoreCase(UPDATE_PLATE_STATUS_EVENT));
        return recordFilterStrategy;
    }

}
