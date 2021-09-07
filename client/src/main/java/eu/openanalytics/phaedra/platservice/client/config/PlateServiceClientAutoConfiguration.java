package eu.openanalytics.phaedra.platservice.client.config;

import eu.openanalytics.phaedra.platservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.platservice.client.impl.HttpPlateServiceClient;
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
