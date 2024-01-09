/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellTemplateRepositoryTest {
    @Autowired
    private WellTemplateRepository wellTemplateRepository;

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
        assertThat(wellTemplateRepository).isNotNull();
    }

    @Test
    public void getWellTemplates() {
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setDescription("Test");
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);
        wellTemplateRepository.save(wellTemplate);

        Iterable<WellTemplate> allWellTemplates = wellTemplateRepository.findAll();
        assertThat(allWellTemplates).isNotNull();
        assertThat(allWellTemplates).isNotEmpty();
    }

    @Test
    public void createNewWellTemplate() {
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setDescription("Test");
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);
        wellTemplate.setSubstanceName("substance_name");
        wellTemplate.setSubstanceType("substance_type");
        wellTemplate.setConcentration(1.52);

        WellTemplate savedWellTemplate = wellTemplateRepository.save(wellTemplate);
        assertThat(savedWellTemplate).isNotNull();
        assertThat(savedWellTemplate.getId()).isNotNull();
        assertThat(savedWellTemplate.getDescription()).isEqualTo(wellTemplate.getDescription());
        assertThat(savedWellTemplate.getRow()).isEqualTo(wellTemplate.getRow());
        assertThat(savedWellTemplate.getColumn()).isEqualTo(wellTemplate.getColumn());
        assertThat(savedWellTemplate.getWellType()).isEqualTo("EMPTY");
        assertThat(savedWellTemplate.isSkipped()).isTrue();
        assertThat(savedWellTemplate.getPlateTemplateId()).isEqualTo(1000L);
        assertThat(savedWellTemplate.getSubstanceName()).isEqualTo("substance_name");
        assertThat(savedWellTemplate.getSubstanceType()).isEqualTo("substance_type");
        assertThat(savedWellTemplate.getConcentration()).isEqualTo(1.52);
    }

    @Test
    public void deleteWellTemplate() {
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setDescription("Test");
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);

        WellTemplate savedWellTemplate = wellTemplateRepository.save(wellTemplate);
        assertThat(savedWellTemplate).isNotNull();
        assertThat(savedWellTemplate.getId()).isNotNull();
        assertThat(savedWellTemplate.getDescription()).isEqualTo(wellTemplate.getDescription());
        assertThat(savedWellTemplate.getRow()).isEqualTo(wellTemplate.getRow());
        assertThat(savedWellTemplate.getColumn()).isEqualTo(wellTemplate.getColumn());
        wellTemplateRepository.deleteById(savedWellTemplate.getId());

        Optional<WellTemplate> deletedWellTemplate = wellTemplateRepository.findById(savedWellTemplate.getId());
        assertThat(deletedWellTemplate.isEmpty()).isTrue();
    }

    @Test
    public void findWellTemplateByPlateTemplateId() {
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setDescription("Test");
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);

        WellTemplate savedWellTemplate = wellTemplateRepository.save(wellTemplate);
        assertThat(savedWellTemplate).isNotNull();
        assertThat(savedWellTemplate.getId()).isNotNull();
        assertThat(savedWellTemplate.getDescription()).isEqualTo(wellTemplate.getDescription());
        assertThat(savedWellTemplate.getRow()).isEqualTo(wellTemplate.getRow());
        assertThat(savedWellTemplate.getColumn()).isEqualTo(wellTemplate.getColumn());

        List<WellTemplate> deletedWellTemplate = wellTemplateRepository.findByPlateTemplateId(savedWellTemplate.getPlateTemplateId());
        assertThat(deletedWellTemplate.isEmpty()).isFalse();
    }
}
