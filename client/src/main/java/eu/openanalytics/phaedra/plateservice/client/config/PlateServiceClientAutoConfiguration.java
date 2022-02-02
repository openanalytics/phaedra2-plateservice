package eu.openanalytics.phaedra.plateservice.client.config;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.plateservice.client.impl.HttpPlateServiceClient;
import eu.openanalytics.phaedra.util.PhaedraRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlateServiceClientAutoConfiguration {

    @Bean
    public PlateServiceClient plateServiceClient(PhaedraRestTemplate phaedraRestTemplate) {
        return new HttpPlateServiceClient(phaedraRestTemplate);
    }

}
