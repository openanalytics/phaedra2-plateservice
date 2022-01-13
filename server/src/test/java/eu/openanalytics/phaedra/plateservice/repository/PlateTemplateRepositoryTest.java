package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
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

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlateTemplateRepositoryTest {
    @Autowired
    private PlateTemplateRepository plateTemplateRepository;

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13-alpine")
            .withDatabaseName("phaedra2")
            .withUrlParam("currentSchema","plates")
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
        assertThat(plateTemplateRepository).isNotNull();
    }

    @Test
    public void getPlateTemplates() {
        Iterable<PlateTemplate> allPlateTemplates = plateTemplateRepository.findAll();
        assertThat(allPlateTemplates).isNotNull();
        assertThat(allPlateTemplates).isNotEmpty();
    }

    @Test
    public void getPlateTemplateById() {
        Optional<PlateTemplate> plateTemplate = plateTemplateRepository.findById(1000L);
        assertThat(plateTemplate.isPresent()).isTrue();
    }

    @Test
    public void createNewPlateTemplate() {
        PlateTemplate plateTemplate = new PlateTemplate();
        plateTemplate.setName("Test");
        plateTemplate.setDescription("Test");
        plateTemplate.setRows(2);
        plateTemplate.setColumns(3);
        plateTemplate.setCreatedOn(new Date());
        plateTemplate.setCreatedBy("smarien");

        PlateTemplate savedPlateTemplate = plateTemplateRepository.save(plateTemplate);

        assertThat(savedPlateTemplate).isNotNull();
        assertThat(savedPlateTemplate.getId()).isNotNull();
        assertThat(savedPlateTemplate.getName()).isEqualTo(plateTemplate.getName());
    }

    @Test
    public void deletePlateTemplate() {
        PlateTemplate plateTemplate = new PlateTemplate();
        plateTemplate.setName("Test");
        plateTemplate.setDescription("Test");
        plateTemplate.setRows(2);
        plateTemplate.setColumns(3);
        plateTemplate.setCreatedOn(new Date());
        plateTemplate.setCreatedBy("smarien");

        PlateTemplate savedPlateTemplate = plateTemplateRepository.save(plateTemplate);
        assertThat(savedPlateTemplate).isNotNull();
        assertThat(savedPlateTemplate.getId()).isNotNull();
        assertThat(savedPlateTemplate.getName()).isEqualTo(plateTemplate.getName());
        plateTemplateRepository.deleteById(savedPlateTemplate.getId());

        Optional<PlateTemplate> deletedPlateTemplate = plateTemplateRepository.findById(savedPlateTemplate.getId());
        assertThat(deletedPlateTemplate.isEmpty()).isTrue();
    }
}
