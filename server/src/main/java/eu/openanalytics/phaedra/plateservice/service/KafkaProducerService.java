package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.commons.dto.CalculationRequestDTO;
import eu.openanalytics.phaedra.plateservice.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void initiateCalculation(CalculationRequestDTO calculationRequestDTO) {
        kafkaTemplate.send(KafkaConfig.CALCULATIONS_TOPIC, KafkaConfig.PLATE_CALCULATION_EVENT, calculationRequestDTO);
    }
}
