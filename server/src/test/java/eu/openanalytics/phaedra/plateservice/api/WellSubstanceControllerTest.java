/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.support.Containers;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellSubstanceControllerTest {

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
    public void getWellSubstanceByWellIdTest() throws Exception {
        Long wellId = 38748L;

        MvcResult mvcResult = this.mockMvc.perform(get("/wellsubstances").param("wellId", wellId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getWellSubstanceByPlateIdTest() throws Exception {
        Long plateId = 2000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/wellsubstances")
                        .param("plateId", plateId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<WellSubstanceDTO> wellSubstanceDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(wellSubstanceDTOList).isNotNull().isNotEmpty().hasSize(384);
    }

    @Test
    public void getWellSubstanceByPlateIdAndWellTypesTest() throws Exception {
        Long plateId = 2000L;
        String wellTypes = "LC,HC";

        MvcResult mvcResult = this.mockMvc.perform(get("/wellsubstances")
                        .param("plateId", plateId.toString())
                        .param("wellTypes", wellTypes))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<WellSubstanceDTO> wellSubstanceDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(wellSubstanceDTOList).isNotNull().isNotEmpty().hasSize(64);
    }

    @Test
    public void getWellSubstanceByPlateIdAndName() throws Exception {
        Long plateId = 2000L;
        String substanceName = "000702-1";

        MvcResult mvcResult = this.mockMvc.perform(get("/wellsubstances")
                        .param("plateId", plateId.toString())
                        .param("substanceName", substanceName))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<WellSubstanceDTO> wellSubstanceDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(wellSubstanceDTOList).isNotNull().isNotEmpty().hasSize(32);
    }

    @Test
    public void getWellSubstanceByPlateIdAndType() throws Exception {
        Long plateId = 2000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/wellsubstances")
                        .param("plateId", plateId.toString())
                        .param("substanceType", SubstanceType.COMPOUND.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<WellSubstanceDTO> wellSubstanceDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(wellSubstanceDTOList).isNotNull().isNotEmpty().hasSize(384);
    }

}
