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
package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.repository.WellSubstanceRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellSubstanceServiceTest {

    private final org.modelmapper.ModelMapper modelMapper = new ModelMapper();
    private final IAuthorizationService authService = AuthorizationServiceFactory.create();


    @Autowired
    private WellSubstanceRepository wellSubstanceRepository;
    @Autowired
    private WellSubstanceService wellSubstanceService;

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

    @Test
    public void contextLoads() {
        assertThat(wellSubstanceRepository).isNotNull();
        assertThat(wellSubstanceService).isNotNull();
        assertThat(modelMapper).isNotNull();
    }

    @Test
    public void testGetWellSubstanceByWellId() {
        long wellId = 38587L;

        WellSubstanceDTO result = wellSubstanceService.getWellSubstanceByWellId(wellId);
        assertThat(result).isNotNull();
        assertThat(result.getWellId()).isEqualTo(wellId);
    }

    @Test
    public void testGetWellSubstancesByPlateId() {
        long plateId = 2000;

        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateId(plateId);
        assertThat(results).isNotNull().isNotEmpty().hasSize(384);
    }

    @Test
    public void testGetWellSubstancesByName() {
        long plateId = 2000L;
        String substanceName = "000702-1";

        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndName(plateId, substanceName);
        assertThat(results).isNotNull().isNotEmpty().hasSize(32);
    }

    @Test
    public void testGetWellSubstancesByType() {
        long plateId = 2000L;

        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndType(plateId, SubstanceType.COMPOUND);
        assertThat(results).isNotNull().isNotEmpty().hasSize(384);
    }

    @Test
    public void testGetWellSubstanceByPlateIdAndWellTypes() {
        long plateId = 2000;

        List<WellSubstanceDTO> allWells = wellSubstanceService.getWellSubstanceByPlateIdAndWellTypes(plateId, List.of("HC", "LC", "SAMPLE"));
        assertThat(allWells).isNotNull().isNotEmpty().hasSize(384);

        List<WellSubstanceDTO> allControlWells = wellSubstanceService.getWellSubstanceByPlateIdAndWellTypes(plateId, List.of("HC", "LC"));
        assertThat(allControlWells).isNotNull().isNotEmpty().hasSize(64);

        List<WellSubstanceDTO> allSampleWells = wellSubstanceService.getWellSubstanceByPlateIdAndWellTypes(plateId, List.of("SAMPLE"));
        assertThat(allSampleWells).isNotNull().isNotEmpty().hasSize(320);
    }
}
