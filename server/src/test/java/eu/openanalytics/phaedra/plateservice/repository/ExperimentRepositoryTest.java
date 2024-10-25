package eu.openanalytics.phaedra.plateservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
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
public class ExperimentRepositoryTest {

  @Autowired
  private ExperimentRepository experimentRepository;

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
    assertThat(experimentRepository).isNotNull();
  }

  @Test
  public void testFindById_Exists() {
    Experiment experiment = experimentRepository.findById(1000L);

    assertThat(experiment).isNotNull();
    assertThat(experiment.getId()).isEqualTo(1000L);
    assertThat(experiment.getProject()).isNotNull();
  }

  @Test
  public void testFindAll_NotEmpty() {
    List<Experiment> experiments = experimentRepository.findAll();

    assertThat(experiments).isNotEmpty();
    assertThat(experiments).hasSize(2);
  }

  @Test
  public void testFindAllByIdIn_NotEmpty() {
    List<Experiment> experiments = experimentRepository.findAllByIdIn(List.of(1000L));

    assertThat(experiments).isNotEmpty();
    assertThat(experiments).hasSize(1);
  }

  @Test
  public void testFindByProjectId_NotEmpty() {
    List<Experiment> experiments = experimentRepository.findAllByProjectId(1000L);

    assertThat(experiments).isNotEmpty();
    assertThat(experiments).hasSize(2);
  }

  @Test
  public void testFindAllByProjectIdIn_NotEmpty() {
    List<Experiment> experiments = experimentRepository.findAllByProjectIdIn(List.of(1000L, 2000L));

    assertThat(experiments).isNotEmpty();
    assertThat(experiments).hasSize(2);
  }

}
