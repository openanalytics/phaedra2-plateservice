package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.measurementservice.client.MeasurementServiceClient;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.repository.PlateMeasurementRepository;
import eu.openanalytics.phaedra.plateservice.support.Containers;
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

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlateMeasurementServiceTest {

    @Autowired
    private PlateMeasurementRepository plateMeasurementRepository;
    @Autowired
    private MeasurementServiceClient measurementServiceClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PlateService plateService;
    @Autowired
    private ProjectAccessService projectAccessService;

    PlateMeasurementService plateMeasurementService;

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
        this.plateMeasurementService = new PlateMeasurementService(plateMeasurementRepository, measurementServiceClient,
                modelMapper, plateService, projectAccessService);
    }

    @Test
    public void contextLoads() {
        assertThat(plateMeasurementRepository).isNotNull();
        assertThat(measurementServiceClient).isNotNull();
        assertThat(modelMapper).isNotNull();
        assertThat(plateService).isNotNull();
        assertThat(projectAccessService).isNotNull();
    }

    @Test
    public void isInitialized() {
        assertThat(this.plateMeasurementService).isNotNull();
    }

    @Test
    public void getActivePlateMeasurement() {
        PlateMeasurementDTO activePlateMeasurementDTO = plateMeasurementService.getActivePlateMeasurement(2000L);
        assertThat(activePlateMeasurementDTO).isNotNull();
        assertThat(activePlateMeasurementDTO.getActive()).isTrue();
    }

    @Test
    public void getActivePlateMeasurementFail() {
        PlateMeasurementDTO activePlateMeasurementDTO = plateMeasurementService.getActivePlateMeasurement(1000L);
        assertThat(activePlateMeasurementDTO).isNull();
    }
}
