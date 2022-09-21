/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.util.auth.AuthorizationServiceFactory;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlateServiceTest {
    private final org.modelmapper.ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PlateRepository plateRepository;
    @Autowired
    private WellService wellService;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ProjectAccessService projectAccessService;
    private IAuthorizationService authService = AuthorizationServiceFactory.create();

    @Autowired
    private PlateTemplateService plateTemplateService;
    @Autowired
    private WellTemplateService wellTemplateService;
    @Autowired
    private WellSubstanceService wellSubstanceService;

    @Autowired
    private PlateService plateService;

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13-alpine")
            .withDatabaseName("phaedra2")
            .withUrlParam("currentSchema", "plates")
            .withPassword("inmemory")
            .withUsername("inmemory");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", Containers.postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USER", Containers.postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", Containers.postgreSQLContainer::getPassword);
    }

//    @BeforeClass
//    void before() {
//        plateService = new PlateService(plateRepository, wellService, experimentService,
//                projectAccessService, authService, plateTemplateService, wellTemplateService, wellSubstanceService);
//
//        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
//                .setDestinationNameTransformer(NameTransformers.builder())
//                .setDestinationNamingConvention(NamingConventions.builder());
//        modelMapper.createTypeMap(Plate.class, PlateDTO.PlateDTOBuilder.class, builderConfiguration)
//                .setPropertyCondition(Conditions.isNotNull());
//    }

    @Test
    public void contextLoads() {
        assertThat(plateRepository).isNotNull();
        assertThat(wellService).isNotNull();
        assertThat(experimentService).isNotNull();
        assertThat(projectAccessService).isNotNull();
        assertThat(plateTemplateService).isNotNull();
        assertThat(wellTemplateService).isNotNull();
        assertThat(wellSubstanceService).isNotNull();
        assertThat(modelMapper).isNotNull();
    }

    @Test
    public void isInitialized() {
        assertThat(this.plateService).isNotNull();
    }

    @Test
    public void linkPlate() {
        PlateDTO plateDTO = plateService.linkPlate(1000L, 56L);
        assertThat(plateDTO).isNotNull();
    }
}
