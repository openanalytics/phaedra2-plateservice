package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import org.junit.jupiter.api.Test;
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

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@DirtiesContext
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class ExperimentServiceTest {
    @Autowired
    private PlateRepository plateRepository;
    @Autowired
    private ExperimentService experimentService;

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
        assertThat(experimentService).isNotNull();
    }

    @Test
    void isInitialized() {
        assertThat(this.experimentService).isNotNull();
    }

    @Test
    void getExperimentSummaryByExperimentId() {
        long experimentId = 1000L;

        ExperimentSummaryDTO experimentSummaryDTO = experimentService.getExperimentSummaryByExperimentId(experimentId);

        assertThat(experimentSummaryDTO).isNotNull();
        assertThat(experimentSummaryDTO.nrPlates).isEqualTo(2);
    }

}
