package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.platservice.dto.ExperimentSummaryDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.ProjectDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", Containers.postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USER", Containers.postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", Containers.postgreSQLContainer::getPassword);
    }

    @Test
    public void projectPostTest() throws Exception {
        Project project = new Project();
        project.setName("Test");

        String requestBody = objectMapper.writeValueAsString(project);
        MvcResult mvcResult = this.mockMvc.perform(post("/project").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        ProjectDTO projectDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectDTO.class);
        assertThat(projectDTO).isNotNull();
        assertThat(projectDTO.getId()).isNotNull();
        assertThat(projectDTO.getName()).isEqualTo("Test");
    }

    @Test
    public void projectPutTest() throws Exception {
        Long projectId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/project/{projectId}", projectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ProjectDTO projectDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectDTO.class);
        assertThat(projectDTO).isNotNull();
        assertThat(projectDTO.getId()).isEqualTo(projectId);
        assertThat(projectDTO.getName()).isEqualTo("SBE_0001");

        projectDTO.setName("changed");

        String requestBody = objectMapper.writeValueAsString(projectDTO);
        this.mockMvc.perform(put("/project").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/project/{projectId}", projectDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ProjectDTO changed = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectDTO.class);
        assertThat(changed.getName()).isEqualTo("changed");
    }

    @Test
    public void projectDeleteAndGetNotFoundTest() throws Exception {
        Long projectId = 1000L;
        this.mockMvc.perform(delete("/project/{projectId}", projectId))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/project/{projectId}", projectId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("");
    }

    @Test
    public void projectGetOneFoundTest() throws Exception {
        Long projectId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/project/{projectId}", projectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ProjectDTO projectDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectDTO.class);
        assertThat(projectDTO).isNotNull();
        assertThat(projectDTO.getId()).isEqualTo(projectId);
        assertThat(projectDTO.getName()).isEqualTo("SBE_0001");
    }

    @Test
    public void projectsGetOneFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ProjectDTO> projectDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(projectDTO).isNotEmpty();
        assertThat(projectDTO.size()).isEqualTo(1);
    }

    @Test
    public void projectsGetNoneFound() throws Exception {
        //Delete only project
        Long projectId = 1000L;
        this.mockMvc.perform(delete("/project/{projectId}", projectId))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        List<ProjectDTO> projectDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(projectDTO).isEmpty();
    }

    @Test
    public void projectGetExperiments() throws Exception {
        Long projectId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/project/{projectId}/experiments",projectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ProjectDTO> projectDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(projectDTO).isNotEmpty();
        assertThat(projectDTO.size()).isEqualTo(1);
    }

    @Test
    public void projectGetExperimentSummaries() throws Exception {
        Long projectId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/project/{projectId}/experimentsummaries",projectId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ExperimentSummaryDTO> experimentSummaryDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(experimentSummaryDTOS).isNotEmpty();
        assertThat(experimentSummaryDTOS.size()).isEqualTo(1);
        ExperimentSummaryDTO ret = objectMapper.convertValue(experimentSummaryDTOS.get(0), ExperimentSummaryDTO.class);

        assertThat(ret.experimentId).isEqualTo(1000L);
        assertThat(ret.nrPlates).isEqualTo(2);
        assertThat(ret.nrPlatesCalculated).isEqualTo(0);
        assertThat(ret.nrPlatesValidated).isEqualTo(0);
        assertThat(ret.nrPlatesApproved).isEqualTo(0);
    }
}