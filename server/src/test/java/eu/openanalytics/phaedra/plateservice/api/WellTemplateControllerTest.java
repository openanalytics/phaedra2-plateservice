package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class WellTemplateControllerTest {

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
    public void wellTemplatePostTest() throws Exception{
        WellTemplate newWellTemplate = new WellTemplate();
        newWellTemplate.setRow(2);
        newWellTemplate.setColumn(3);
        newWellTemplate.setPlateTemplateId(1000L);

        String requestBody = objectMapper.writeValueAsString(newWellTemplate);
        MvcResult mvcResult = this.mockMvc.perform(post("/well-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        WellTemplateDTO wellTemplateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WellTemplateDTO.class);
        assertThat(wellTemplateDTO).isNotNull();
        assertThat(wellTemplateDTO.getId()).isEqualTo(1L);
    }

    @Test
    public void wellTemplatePutTest() throws Exception{
        //Create WellTemplate
        WellTemplate wellTemplate = new WellTemplate();
        wellTemplate.setRow(2);
        wellTemplate.setColumn(3);
        wellTemplate.setPlateTemplateId(1000L);

        String requestBody = objectMapper.writeValueAsString(wellTemplate);
        MvcResult mvcResult = this.mockMvc.perform(post("/well-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        WellTemplateDTO wellTemplateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WellTemplateDTO.class);
        assertThat(wellTemplateDTO).isNotNull();
        assertThat(wellTemplateDTO.getId()).isEqualTo(1L);

        String newWellType = "test";
        wellTemplateDTO.setWellType(newWellType);

        String requestBody2 = objectMapper.writeValueAsString(wellTemplateDTO);
        this.mockMvc.perform(put("/well-template").contentType(MediaType.APPLICATION_JSON).content(requestBody2))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/well-template/{wellTemplateId}", wellTemplateDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        WellTemplate updatedWellTemplate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WellTemplate.class);
        assertThat(updatedWellTemplate.getWellType()).isEqualTo(newWellType);
    }

    @Test
    public void wellTemplateGetOneFoundTest() throws Exception{
        WellTemplate newWellTemplate = new WellTemplate();
        newWellTemplate.setRow(2);
        newWellTemplate.setColumn(3);
        newWellTemplate.setPlateTemplateId(1000L);

        String requestBody = objectMapper.writeValueAsString(newWellTemplate);
        MvcResult mvcResult = this.mockMvc.perform(post("/well-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        WellTemplateDTO wellTemplateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WellTemplateDTO.class);
        assertThat(wellTemplateDTO).isNotNull();
        assertThat(wellTemplateDTO.getId()).isEqualTo(1L);

        Long wellTemplateDTOId = wellTemplateDTO.getId();

        MvcResult mvcResult2 = this.mockMvc.perform(get("/well-template/{wellTemplateDTOId}", wellTemplateDTOId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        WellTemplate wellTemplate = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), WellTemplate.class);
        assertThat(wellTemplate).isNotNull();
        assertThat(wellTemplate.getId()).isEqualTo(wellTemplateDTOId);
    }

    @Test
    public void wellTemplateGetNotFoundTest() throws Exception {
        Long wellTemplateId = 1111L;

        MvcResult mvcResult = this.mockMvc.perform(get("/well-template/{wellTemplateId}", wellTemplateId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
    }
}
