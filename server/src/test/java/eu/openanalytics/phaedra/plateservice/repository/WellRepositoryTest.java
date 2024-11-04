package eu.openanalytics.phaedra.plateservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import eu.openanalytics.phaedra.plateservice.model.Well;
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

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellRepositoryTest {

  @Autowired
  private WellRepository wellRepository;

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
    assertThat(wellRepository).isNotNull();
  }

  @Test
  public void testFindByIdExists() {
    Well foundWell = wellRepository.findById(9494L);

    assertThat(foundWell).isNotNull();
    assertThat(foundWell.getId()).isEqualTo(9494L);
    assertThat(foundWell.getPlate()).isNotNull();
    assertThat(foundWell.getExperiment()).isNotNull();
    assertThat(foundWell.getProject()).isNotNull();
  }
}
