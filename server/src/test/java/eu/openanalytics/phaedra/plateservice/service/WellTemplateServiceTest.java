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

import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import eu.openanalytics.phaedra.plateservice.repository.PlateTemplateRepository;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.util.auth.AuthorizationServiceFactory;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellTemplateServiceTest {

    @Autowired
    private WellTemplateRepository wellTemplateRepository;

    @Autowired
    private PlateTemplateRepository plateTemplateRepository;

    @Autowired
    private PlateTemplateService plateTemplateService;

    private IAuthorizationService authService = AuthorizationServiceFactory.create();

    private WellTemplateService wellTemplateService;

    private static final ModelMapper modelMapper = new ModelMapper();


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

    @BeforeEach
    void before() {
        this.wellTemplateService = new WellTemplateService(this.wellTemplateRepository, this.plateTemplateRepository, this.authService);
    }

    @Test
    public void contextLoads() {
        assertThat(wellTemplateRepository).isNotNull();
    }

    @Test
    public void isInitialized() {
        assertThat(this.wellTemplateService).isNotNull();
    }

    @Test
    public void createWellTemplateTest() {
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);
        WellTemplateDTO wellTemplateDTO = mapToWellTemplateDTO(wellTemplate);
        WellTemplateDTO res = wellTemplateService.createWellTemplate(wellTemplateDTO);
        //Check if returned value is the desired one
        assertThat(res.getRow()).isEqualTo(wellTemplateDTO.getRow());
        assertThat(res.getColumn()).isEqualTo(wellTemplateDTO.getColumn());
        assertThat(res.getWellType()).isEqualTo("EMPTY");
        assertThat(res.isSkipped()).isTrue();
        assertThat(res.getPlateTemplateId()).isEqualTo(wellTemplateDTO.getPlateTemplateId());

        //Check if value is correctly stored in the database
        Optional<WellTemplate> res2 = wellTemplateRepository.findById(res.getId());
        WellTemplateDTO converted = res2.map(this::mapToWellTemplateDTO).orElse(null);
        assertThat(converted.getRow()).isEqualTo(wellTemplateDTO.getRow());
        assertThat(converted.getColumn()).isEqualTo(wellTemplateDTO.getColumn());
        assertThat(converted.getWellType()).isEqualTo("EMPTY");
        assertThat(converted.isSkipped()).isTrue();
        assertThat(converted.getPlateTemplateId()).isEqualTo(wellTemplateDTO.getPlateTemplateId());
    }

    @Test
    public void createWellTemplatesTest(){
        //This test also tests getWelltemplateByPlateTemplateId
        //Check if the plateTemplate has no wellTemplates assigned
        long plateTemplateId = 1001L;

        List<WellTemplateDTO> wellTemplateDTOS = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);
        assertThat(wellTemplateDTOS.size()).isEqualTo(0);
        //Find the plateTemplate in the repo
        PlateTemplate plateTemplate = plateTemplateRepository.findById(plateTemplateId).orElse(null);
        assertThat(plateTemplate).isNotNull();
        //Create all well templates
        List<WellTemplateDTO> ret = wellTemplateService.createEmptyWellTemplates(plateTemplate);
        assertThat(ret.size()).isEqualTo(6);
        List<WellTemplateDTO> wellTemplateDTOS2 = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);
        assertThat(wellTemplateDTOS2.size()).isEqualTo(6);
    }

    @Test
    public void updateWellTemplateTest(){
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);
        WellTemplateDTO wellTemplateDTO = mapToWellTemplateDTO(wellTemplate);
        WellTemplateDTO res = wellTemplateService.createWellTemplate(wellTemplateDTO);

        //Check if value is correctly stored in the database
        Optional<WellTemplate> res2 = wellTemplateRepository.findById(res.getId());
        WellTemplateDTO converted = res2.map(this::mapToWellTemplateDTO).orElse(null);
        assertThat(converted.getRow()).isEqualTo(wellTemplateDTO.getRow());
        assertThat(converted.getColumn()).isEqualTo(wellTemplateDTO.getColumn());
        assertThat(converted.getWellType()).isEqualTo("EMPTY");
        assertThat(converted.isSkipped()).isTrue();
        assertThat(converted.getPlateTemplateId()).isEqualTo(wellTemplateDTO.getPlateTemplateId());

        //UpdateWellTemplate
        converted.setColumn(2);
        assertThat(converted.getColumn()).isEqualTo(2);
        wellTemplateService.updateWellTemplate(converted);
        Optional<WellTemplate> res3 = wellTemplateRepository.findById(converted.getId());
        WellTemplateDTO convertedUpdated = res3.map(this::mapToWellTemplateDTO).orElse(null);
        assertThat(convertedUpdated.getColumn()).isEqualTo(2);
    }

    @Test
    public void getWellTemplateByIdTest(){
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setRow(1);
        wellTemplate.setColumn(1);
        wellTemplate.setPlateTemplateId(1000L);
        WellTemplateDTO wellTemplateDTO = mapToWellTemplateDTO(wellTemplate);
        WellTemplateDTO res = wellTemplateService.createWellTemplate(wellTemplateDTO);

        WellTemplateDTO wellTemplateDTOGet = wellTemplateService.getWellTemplateById(res.getId());
        assertThat(wellTemplateDTOGet.getColumn()).isEqualTo(1);
    }


    private WellTemplateDTO mapToWellTemplateDTO(WellTemplate wellTemplate) {
        WellTemplateDTO wellTemplateDTO = new WellTemplateDTO();
        modelMapper.typeMap(WellTemplate.class, WellTemplateDTO.class)
                .map(wellTemplate, wellTemplateDTO);
        return wellTemplateDTO;
    }
}
