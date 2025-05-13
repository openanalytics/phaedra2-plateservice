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
package eu.openanalytics.phaedra.plateservice;

import eu.openanalytics.phaedra.util.auth.ClientCredentialsTokenGenerator;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import eu.openanalytics.phaedra.measurementservice.client.config.MeasurementServiceClientAutoConfiguration;
import eu.openanalytics.phaedra.metadataservice.client.config.MetadataServiceClientAutoConfiguration;
import eu.openanalytics.phaedra.util.PhaedraRestTemplate;
import eu.openanalytics.phaedra.util.auth.AuthenticationConfigHelper;
import eu.openanalytics.phaedra.util.auth.AuthorizationServiceFactory;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import eu.openanalytics.phaedra.util.jdbc.JDBCUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@EnableDiscoveryClient
@EnableScheduling
@EnableCaching
@EnableWebSecurity
@SpringBootApplication
@EnableKafka
@Import({MeasurementServiceClientAutoConfiguration.class,
		MetadataServiceClientAutoConfiguration.class,})
public class PlateServiceApplication {

	private final Environment environment;

	public PlateServiceApplication(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(PlateServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public PhaedraRestTemplate restTemplate() {
		return new PhaedraRestTemplate();
	}

	@Bean
	public DataSource dataSource() {
		return JDBCUtils.createDataSource(environment);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		Server server = new Server().url((environment.getProperty("API_URL"))).description("Default Server URL");
		return new OpenAPI().addServersItem(server);
	}

	@Bean
	public IAuthorizationService authService(ClientCredentialsTokenGenerator ccTokenGenerator) {
		return AuthorizationServiceFactory.create(ccTokenGenerator);
	}

	@Bean
	public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		return AuthenticationConfigHelper.configure(http);
	}

	@Bean
	public ClientCredentialsTokenGenerator ccTokenGenerator(
			ClientRegistrationRepository clientRegistrationRepository) {
		return new ClientCredentialsTokenGenerator("keycloak", clientRegistrationRepository);
	}
}
