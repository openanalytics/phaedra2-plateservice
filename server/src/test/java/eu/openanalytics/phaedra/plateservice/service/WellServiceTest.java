package eu.openanalytics.phaedra.plateservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellServiceTest {
  private ModelMapper modelMapper;

  @Autowired
  private WellRepository wellRepository;
  @Autowired
  private PlateService plateService;
  @Autowired
  private ProjectAccessService projectAccessService;
  @Autowired
  private WellSubstanceService wellSubstanceService;
  @Autowired
  private MetadataServiceClient metadataServiceClient;
  @Autowired
  private WellService wellService;

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
    assertThat(wellRepository).isNotNull();
    assertThat(plateService).isNotNull();
    assertThat(projectAccessService).isNotNull();
    assertThat(wellSubstanceService).isNotNull();
    assertThat(metadataServiceClient).isNotNull();
    assertThat(wellService).isNotNull();
  }

  @Test
  void getWellsNotEmpty() {
    List<Long> wellIds = Arrays.asList(9494L, 9499L, 9500L, 9502L);
    List<WellDTO> wellDTOS = wellService.getWells(wellIds);
    assertThat(wellDTOS).isNotNull();
    assertThat(wellDTOS).hasSize(4);
  }
}
