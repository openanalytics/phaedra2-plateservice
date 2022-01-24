package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlateTemplateControllerTest {

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
    public void plateTemplatePostTest() throws Exception{
        PlateTemplate newPlateTemplate = new PlateTemplate();
        newPlateTemplate.setRows(2);
        newPlateTemplate.setColumns(3);
        newPlateTemplate.setCreatedOn(new Date());
        newPlateTemplate.setCreatedBy("smarien");

        String requestBody = objectMapper.writeValueAsString(newPlateTemplate);
        MvcResult mvcResult = this.mockMvc.perform(post("/plate-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        PlateTemplateDTO plateTemplateDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateTemplateDTO.class);
        assertThat(plateTemplateDTOResult).isNotNull();
        assertThat(plateTemplateDTOResult.getId()).isEqualTo(1L);
        assertThat(plateTemplateDTOResult.getWells().size()).isEqualTo(6);
    }

    @Test
    public void plateTemplatePutTest() throws Exception{
        Long plateTemplateId = 1000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate-template/{plateTemplateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateTemplate plateTemplate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateTemplate.class);
        assertThat(plateTemplate).isNotNull();
        assertThat(plateTemplate.getId()).isEqualTo(plateTemplateId);

        String newCreatedBy = "test";
        plateTemplate.setCreatedBy(newCreatedBy);

        String requestBody = objectMapper.writeValueAsString(plateTemplate);
        this.mockMvc.perform(put("/plate-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/plate-template/{plateTemplateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateTemplate updatedPlateTemplate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateTemplate.class);
        assertThat(updatedPlateTemplate.getCreatedBy()).isEqualTo(newCreatedBy);
    }

    @Test
    public void plateTemplateDeleteTest() throws Exception{
        Long plateTemplateId = 1000L;

        this.mockMvc.perform(delete("/plate-template/{plateTemplateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void plateTemplateGetOneFoundTest() throws Exception{
        Long plateTemplateId = 1000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate-template/{plateTemplateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateTemplate plateTemplate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateTemplate.class);
        assertThat(plateTemplate).isNotNull();
        assertThat(plateTemplate.getId()).isEqualTo(plateTemplateId);
    }

    @Test
    public void plateTemplateGetNotFoundTest() throws Exception{
        Long plateTemplateId = 1111L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate-template/{plateTemplateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    public void plateTemplateGetMultipleFoundTest() throws Exception{
        //Check size of list
        MvcResult mvcResult = this.mockMvc.perform(get("/plate-templates"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<PlateTemplate> plateTemplate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plateTemplate).isNotNull();
        assertThat(plateTemplate.size()).isEqualTo(1);

        //Add new plateTemplate
        PlateTemplate newPlateTemplate = new PlateTemplate();
        newPlateTemplate.setRows(2);
        newPlateTemplate.setColumns(3);
        newPlateTemplate.setCreatedOn(new Date());
        newPlateTemplate.setCreatedBy("smarien");

        String requestBody = objectMapper.writeValueAsString(newPlateTemplate);
        this.mockMvc.perform(post("/plate-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(get("/plate-templates"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<PlateTemplate> plateTemplate2 = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), List.class);
        assertThat(plateTemplate2).isNotNull();
        assertThat(plateTemplate2.size()).isEqualTo(2);
    }

    @Test
    public void plateTemplateGetMultipleNotFoundTest() throws Exception{
        //Check size of list
        MvcResult mvcResult = this.mockMvc.perform(get("/plate-templates"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<PlateTemplate> plateTemplate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plateTemplate).isNotNull();
        assertThat(plateTemplate.size()).isEqualTo(1);

        //Delete plate
        Long plateTemplateId = 1000L;

        this.mockMvc.perform(delete("/plate-template/{plateTemplateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult2 = this.mockMvc.perform(get("/plate-templates"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        List<PlateTemplate> plateTemplate2 = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), List.class);
        assertThat(plateTemplate2).isNotNull();
        assertThat(plateTemplate2.size()).isEqualTo(0);
    }
}