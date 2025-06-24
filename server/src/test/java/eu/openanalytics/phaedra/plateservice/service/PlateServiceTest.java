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
package eu.openanalytics.phaedra.plateservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceGraphQlClient;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.LinkStatus;
import eu.openanalytics.phaedra.plateservice.exceptions.ClonePlateException;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.util.auth.AuthorizationServiceFactory;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Testcontainers
@SpringBootTest
@DirtiesContext
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ExtendWith(MockitoExtension.class)
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
    private WellSubstanceService wellSubstanceService;
    @Autowired
    private PlateMeasurementService plateMeasurementService;

    @Autowired
    @InjectMocks
    private PlateService plateService;

    @Mock
    private MetadataServiceGraphQlClient metadataServiceGraphQlClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${test.topic}")
    private String topic;

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
    void contextLoads() {
        assertThat(plateRepository).isNotNull();
        assertThat(wellService).isNotNull();
        assertThat(experimentService).isNotNull();
        assertThat(projectAccessService).isNotNull();
        assertThat(wellSubstanceService).isNotNull();
        assertThat(plateMeasurementService).isNotNull();
        assertThat(modelMapper).isNotNull();
    }

    @Test
    void isInitialized() {
        assertThat(this.plateService).isNotNull();
    }

//    @Test
    void clonePlateById() throws PlateNotFoundException, ClonePlateException {
        PlateDTO original = new PlateDTO();
        original.setBarcode("ORIGINAL");
        original.setRows(16);
        original.setColumns(24);
        original.setTags(List.of("Test", "Clone"));
        original.setExperimentId(1000L);
        original.setLinkTemplateId("1000");
        original.setLinkStatus(LinkStatus.LINKED);
        original.setCalculationStatus(CalculationStatus.CALCULATION_OK);

        PlateDTO originalCreated = plateService.createPlate(original);

        if (originalCreated != null) {
            List<WellDTO> originalWells = wellService.getWellsByPlateId(originalCreated.getId());

            WellSubstanceDTO wellSubstanceDTO = new WellSubstanceDTO();
            wellSubstanceDTO.setWellId(originalWells.get(0).getId());
            wellSubstanceDTO.setType("COMPOUND");
            wellSubstanceDTO.setName("UnitTest");
            wellSubstanceDTO.setConcentration(0.0005);
            wellSubstanceDTO = wellSubstanceService.createWellSubstance(wellSubstanceDTO);

            originalWells.get(0).setWellSubstance(wellSubstanceDTO);
            wellService.updateWells(originalWells);

            PlateDTO clonedPlate = plateService.clonePlateById(originalCreated.getId());
            List<WellDTO> clonedWells = wellService.getWellsByPlateId(clonedPlate.getId());

            assertThat(clonedPlate.getId()).isNotEqualTo(originalCreated.getId());
            assertThat(clonedPlate.getBarcode()).isEqualTo(originalCreated.getBarcode());
            assertThat(clonedPlate.getRows()).isEqualTo(originalCreated.getRows());
            assertThat(clonedPlate.getColumns()).isEqualTo(originalCreated.getColumns());
            assertThat(clonedWells).hasSameSizeAs(originalWells);
            assertThat(clonedWells.get(0).getWellType()).isEqualTo(originalWells.get(0).getWellType());
            assertThat(clonedWells.get(0).getWellSubstance().getId()).isNotEqualTo(originalWells.get(0).getWellSubstance().getId());
            assertThat(clonedWells.get(0).getWellSubstance().getWellId()).isNotEqualTo(originalWells.get(0).getWellSubstance().getWellId());
            assertThat(clonedWells.get(0).getWellSubstance().getName()).isEqualTo(originalWells.get(0).getWellSubstance().getName());
            assertThat(clonedWells.get(0).getWellSubstance().getType()).isEqualTo(originalWells.get(0).getWellSubstance().getType());
        }
    }

//    @Test
    void movePlateById() throws PlateNotFoundException {
        PlateDTO original = new PlateDTO();
        original.setBarcode("ORIGINAL");
        original.setRows(16);
        original.setColumns(24);
        original.setTags(List.of("Test", "Clone"));
        original.setExperimentId(1000L);
        original.setLinkTemplateId("1000");
        original.setLinkStatus(LinkStatus.LINKED);
        original.setCalculationStatus(CalculationStatus.CALCULATION_OK);

        PlateDTO created = plateService.createPlate(original);
        PlateDTO moved = plateService.moveByPlateId(created.getId(), 2000L);

        assertThat(created.getId()).isEqualTo(moved.getId());
        assertThat(created.getBarcode()).isEqualTo(moved.getBarcode());
        assertThat(created.getExperimentId()).isNotEqualTo(moved.getExperimentId());
    }

//    @Test
    void getPlateById() throws PlateNotFoundException {
        PlateDTO plateDTO = plateService.getPlateById(1000L);

        assertThat(plateDTO).isNotNull();
        assertThat(plateDTO.getId()).isEqualTo(1000L);
        assertThat(plateDTO.getExperiment()).isNotNull();
        assertThat(plateDTO.getProject()).isNotNull();
    }
}
