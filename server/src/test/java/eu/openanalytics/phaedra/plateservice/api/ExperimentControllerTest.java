package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
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
public class ExperimentControllerTest {


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
    public void experimentPostTest() throws Exception {
        Experiment experiment = new Experiment();
        experiment.setName("Test");
        experiment.setProjectId(1000L);

        String requestBody = objectMapper.writeValueAsString(experiment);
        MvcResult mvcResult = this.mockMvc.perform(post("/experiment").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        ExperimentDTO experimentDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExperimentDTO.class);
        assertThat(experimentDTO).isNotNull();
        assertThat(experimentDTO.getId()).isNotNull();
        assertThat(experimentDTO.getName()).isEqualTo("Test");
        assertThat(experimentDTO.getProjectId()).isEqualTo(1000L);
    }

    @Test
    public void experimentPutTest() throws Exception {
        Long experimentId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/experiment/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ExperimentDTO experimentDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExperimentDTO.class);
        assertThat(experimentDTO).isNotNull();
        assertThat(experimentDTO.getId()).isEqualTo(experimentId);
        assertThat(experimentDTO.getName()).isEqualTo("SBE_0001_EXP1");

        experimentDTO.setName("changed");

        String requestBody = objectMapper.writeValueAsString(experimentDTO);
        this.mockMvc.perform(put("/experiment").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/experiment/{experimentId}", experimentDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ExperimentDTO changed = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExperimentDTO.class);
        assertThat(changed.getName()).isEqualTo("changed");
    }

    @Test
    public void projectDeleteAndGetNotFoundTest() throws Exception {
        Long experimentId = 1000L;
        this.mockMvc.perform(delete("/experiment/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/experiment/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("");
    }

    @Test
    public void experimentGetOneFoundTest() throws Exception {
        Long experimentId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/experiment/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ExperimentDTO experimentDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExperimentDTO.class);
        assertThat(experimentDTO).isNotNull();
        assertThat(experimentDTO.getId()).isEqualTo(experimentId);
        assertThat(experimentDTO.getName()).isEqualTo("SBE_0001_EXP1");
    }

    @Test
    public void experimentsGetOneFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/experiment"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ExperimentDTO> experimentDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(experimentDTOS).isNotEmpty();
        assertThat(experimentDTOS.size()).isEqualTo(1);
    }

    @Test
    public void experimentsGetNoneFound() throws Exception {
        //Delete only project
        Long experimentId = 1000L;
        this.mockMvc.perform(delete("/experiment/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/experiment"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ExperimentDTO> experimentDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(experimentDTOS).isEmpty();
    }

}
