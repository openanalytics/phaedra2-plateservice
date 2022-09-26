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
package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.enumartion.SubstanceType;
import eu.openanalytics.phaedra.plateservice.model.WellSubstance;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import org.junit.jupiter.api.Test;
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
public class WellSubstanceRepositoryTest {

    @Autowired
    WellSubstanceRepository wellSubstanceRepository;

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
    }

    @Test
    public void testFindByWellId() {
        long wellId = 38587L;

        WellSubstance result = wellSubstanceRepository.findByWellId(wellId);
        assertThat(result).isNotNull();
        assertThat(result.getWellId()).isEqualTo(wellId);
    }

    @Test
    public void testFindByPlateId() {
        long plateId = 2000L;

        List<WellSubstance> results = wellSubstanceRepository.findByPlateId(plateId);
        assertThat(results).isNotNull().isNotEmpty().hasSize(384);
    }

    @Test
    public void testFindBySubstanceName() {
        String substanceName = "000702-1";

        List<WellSubstance> results = wellSubstanceRepository.findWellSubstanceByName(substanceName);
        assertThat(results).isNotNull().isNotEmpty().hasSize(32);
    }

    @Test
    public void testFindBySubstanceType() {
        List<WellSubstance> results = wellSubstanceRepository.findWellSubstanceByType(SubstanceType.COMPOUND);
        assertThat(results).isNotNull().isNotEmpty().hasSize(384);
    }

    @Test
    public void testFindByPlateIdAndControleWellTypes() {
        long plateId = 2000L;
        List<String> wellTypes = List.of("HC", "LC");

        List<WellSubstance> results = wellSubstanceRepository.findByPlateIdAndWellType(plateId, wellTypes);
        assertThat(results).isNotNull().isNotEmpty().hasSize(64);
    }

    @Test
    public void testFindByPlateIdAndSampleWellTypes() {
        long plateId = 2000L;
        List<String> wellTypes = List.of("SAMPLE");

        List<WellSubstance> results = wellSubstanceRepository.findByPlateIdAndWellType(plateId, wellTypes);
        assertThat(results).isNotNull().isNotEmpty().hasSize(320);
    }
}
