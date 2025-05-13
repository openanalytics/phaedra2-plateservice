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
package eu.openanalytics.phaedra.plateservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import java.util.List;
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

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlateRepositoryTest {

  @Autowired
  private PlateRepository plateRepository;

  @Container
  private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer(
      "postgres:13-alpine")
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
    assertThat(plateRepository).isNotNull();
  }

  @Test
  public void testFindById_Exists() {
    Plate plate = plateRepository.findById(1000L);

    assertThat(plate).isNotNull();
    assertThat(plate.getId()).isEqualTo(1000L);
    assertThat(plate.getExperiment()).isNotNull();
    assertThat(plate.getProject()).isNotNull();
  }

  @Test
  public void testFindAll_NotEmpty() {
    List<Plate> plate = plateRepository.findAll();

    assertThat(plate).isNotEmpty();
    assertThat(plate).hasSize(2);
  }

  @Test
  public void testFindAllByIdIn_NotEmpty() {
    List<Plate> plate = plateRepository.findAllByIdIn(List.of(1000L));

    assertThat(plate).isNotEmpty();
    assertThat(plate).hasSize(1);
  }

  @Test
  public void testFindByExperimentId_NotEmpty() {
    List<Plate> plate = plateRepository.findByExperimentId(1000L);

    assertThat(plate).isNotEmpty();
    assertThat(plate).hasSize(2);
  }

  @Test
  public void testFindAllByExperimentIdIn_NotEmpty() {
    List<Plate> plate = plateRepository.findAllByExperimentIdIn(List.of(1000L, 2000L));

    assertThat(plate).isNotEmpty();
    assertThat(plate).hasSize(2);
  }

  @Test
  public void testFindByProjectId_NotEmpty() {
    List<Plate> plate = plateRepository.findByProjectId(1000L);

    assertThat(plate).isNotEmpty();
    assertThat(plate).hasSize(2);
  }

  @Test
  public void testFindAllByProjectIdIn_NotEmpty() {
    List<Plate> plate = plateRepository.findAllByProjectIdIn(List.of(1000L, 2000L));

    assertThat(plate).isNotEmpty();
    assertThat(plate).hasSize(2);
  }

}
