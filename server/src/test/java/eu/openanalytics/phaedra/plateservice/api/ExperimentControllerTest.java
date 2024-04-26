/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.plateservice.model.Experiment;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
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
        MvcResult mvcResult = this.mockMvc.perform(post("/experiments").contentType(MediaType.APPLICATION_JSON).content(requestBody))
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
        MvcResult mvcResult = this.mockMvc.perform(get("/experiments/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ExperimentDTO experimentDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExperimentDTO.class);
        assertThat(experimentDTO).isNotNull();
        assertThat(experimentDTO.getId()).isEqualTo(experimentId);
        assertThat(experimentDTO.getName()).isEqualTo("SBE_0001_EXP1");

        experimentDTO.setName("changed");

        String requestBody = objectMapper.writeValueAsString(experimentDTO);
        this.mockMvc.perform(put("/experiments/{experimentId}", experimentId).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/experiments/{experimentId}", experimentDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ExperimentDTO changed = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExperimentDTO.class);
        assertThat(changed.getName()).isEqualTo("changed");
    }

    @Test
    public void projectDeleteAndGetNotFoundTest() throws Exception {
        Long experimentId = 1000L;
        this.mockMvc.perform(delete("/experiments/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/experiments/{experimentId}", experimentId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("");
    }

    @Test
    public void experimentGetOneFoundTest() throws Exception {
        Long experimentId = 1000L;
        MvcResult mvcResult = this.mockMvc.perform(get("/experiments/{experimentId}", experimentId))
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
        MvcResult mvcResult = this.mockMvc.perform(get("/experiments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ExperimentDTO> experimentDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(experimentDTOS).isNotEmpty();
        assertThat(experimentDTOS.size()).isEqualTo(2);
    }

    @Test
    public void experimentsGetNoneFound() throws Exception {
        //Delete only project
        this.mockMvc.perform(delete("/experiments/{experimentId}", 1000L))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/experiments/{experimentId}", 2000L))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/experiments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<ExperimentDTO> experimentDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(experimentDTOS).isEmpty();
    }

}
