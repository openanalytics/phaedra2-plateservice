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
package eu.openanalytics.phaedra.plateservice.client.config;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceClient;
import eu.openanalytics.phaedra.plateservice.client.PlateServiceGraphQLClient;
import eu.openanalytics.phaedra.plateservice.client.impl.HttpPlateServiceClient;
import eu.openanalytics.phaedra.plateservice.client.impl.PlateServiceGraphQLClientImpl;
import eu.openanalytics.phaedra.util.PhaedraRestTemplate;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PlateServiceClientAutoConfiguration {

    @Bean
    public PlateServiceClient plateServiceClient(PhaedraRestTemplate phaedraRestTemplate, IAuthorizationService authService, Environment environment) {
        return new HttpPlateServiceClient(phaedraRestTemplate, authService, environment);
    }

    @Bean
    public PlateServiceGraphQLClient plateServiceGraphQLClient(IAuthorizationService authService, Environment environment) {
        return new PlateServiceGraphQLClientImpl(authService, environment);
    }

}
