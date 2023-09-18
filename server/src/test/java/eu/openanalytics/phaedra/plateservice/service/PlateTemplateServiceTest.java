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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
import org.junit.jupiter.api.BeforeEach;
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

import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import eu.openanalytics.phaedra.plateservice.repository.PlateTemplateRepository;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.util.auth.AuthorizationServiceFactory;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlateTemplateServiceTest {
    @Autowired
    private PlateTemplateRepository plateTemplateRepository;
    @Autowired
    private WellTemplateRepository wellTemplateRepository;

    private IAuthorizationService authService = AuthorizationServiceFactory.create();

    private PlateTemplateService plateTemplateService;
    private WellTemplateService wellTemplateService;

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
        this.plateTemplateService = new PlateTemplateService(this.plateTemplateRepository, wellTemplateService, this.authService);
    }

    @Test
    public void contextLoads() {
        assertThat(plateTemplateRepository).isNotNull();
    }

    @Test
    public void isInitialized() {
        assertThat(this.plateTemplateService).isNotNull();
    }

    @Test
    public void createPlateTemplateTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").build();
        PlateTemplateDTO res = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        assertThat(res).isNotNull();
        assertThat(res.getRows()).isEqualTo(plateTemplateDTO.getRows());
        assertThat(res.getColumns()).isEqualTo(plateTemplateDTO.getColumns());
        assertThat(res.getCreatedBy()).isEqualTo(plateTemplateDTO.getCreatedBy());
        assertThat(res.getCreatedOn()).isEqualTo(plateTemplateDTO.getCreatedOn());

        //Check if plateTemplate is added to database
        PlateTemplateDTO res2 = plateTemplateService.getPlateTemplateById(res.getId());
        //Returned by repository is Timestamp, convert it to Date and compare
        assertThat(new Date(res2.getCreatedOn().getTime())).isEqualTo(res.getCreatedOn());

        //Check if wellTemplates are created
        List<WellTemplate> wellTemplateList = wellTemplateRepository.findByPlateTemplateId(res.getId());
        assertThat(wellTemplateList.isEmpty()).isFalse();
        assertThat(wellTemplateList.size()).isEqualTo(6);
    }

    @Test
    public void updatePlateTemplateTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").name("Test").build();
        PlateTemplateDTO res = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        assertThat(res).isNotNull();

        //Check if plateTemplate is added to database
        PlateTemplateDTO res2 = plateTemplateService.getPlateTemplateById(res.getId());
        //Returned by repository is Timestamp, convert it to Date and compare
        assertThat(new Date(res2.getCreatedOn().getTime())).isEqualTo(res.getCreatedOn());
        assertThat(res2.getName()).isEqualTo("Test");

//        PlateTemplateDTO updatedPlateTemplateDTO = PlateTemplateDTO.builder().id(res.getId()).rows(2).columns(3).createdOn(new Date()).createdBy("smarien").name("Test2").wells(res.getWells()).build();
//        PlateTemplateDTO updatedPlateTemplateDTO = PlateTemplateDTO.builder().id(res.getId()).rows(2).columns(3).createdOn(new Date()).createdBy("smarien").name("Test2").build();
//        plateTemplateService.updatePlateTemplate(updatedPlateTemplateDTO);

//        PlateTemplateDTO res3 = plateTemplateService.getPlateTemplateById(res.getId());
//        assertThat(res3.getName()).isEqualTo("Test2");
    }

    @Test
    public void deletePlateTemplateTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").name("Test").build();
        PlateTemplateDTO res = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        assertThat(res).isNotNull();

        //Check if plateTemplate is added to database
        PlateTemplateDTO res2 = plateTemplateService.getPlateTemplateById(res.getId());
        //Returned by repository is Timestamp, convert it to Date and compare
        assertThat(new Date(res2.getCreatedOn().getTime())).isEqualTo(res.getCreatedOn());

        plateTemplateService.deletePlateTemplate(res.getId());
        PlateTemplateDTO res3 = plateTemplateService.getPlateTemplateById(res.getId());
        assertThat(res3).isNull();
    }

    @Test
    public void getAllPlateTemplatesTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").name("Test").build();
        PlateTemplateDTO res = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        assertThat(res).isNotNull();

        //Check if plateTemplate is added to database
        PlateTemplateDTO res2 = plateTemplateService.getPlateTemplateById(res.getId());
        //Returned by repository is Timestamp, convert it to Date and compare
        assertThat(new Date(res2.getCreatedOn().getTime())).isEqualTo(res.getCreatedOn());

        List<PlateTemplateDTO> plateTemplateDTOS = plateTemplateService.getAllPlateTemplates();
        PlateTemplateDTO res3 = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        List<PlateTemplateDTO> plateTemplateDTOS2 = plateTemplateService.getAllPlateTemplates();
        //Is list before new plateTemplate one smaller than new list
        assertThat(plateTemplateDTOS.size() + 1).isEqualTo(plateTemplateDTOS2.size());
    }

}
