package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
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
public class PlateControllerTest {

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
    public void plateAreWellsPresentTest() throws Exception{
        Plate plate = new Plate();
        plate.setRows(3);
        plate.setColumns(4);
        plate.setExperimentId(1L);
        plate.setSequence(1);

        String requestBody = objectMapper.writeValueAsString(plate);
        MvcResult mvcResult = this.mockMvc.perform(post("/plate").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        PlateDTO plateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTO.getWells().size()).isEqualTo(12);

        Long plateDTOId = plateDTO.getId();

        MvcResult mvcResult2 = this.mockMvc.perform(get("/plate/{plateId}", plateDTOId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTOGet = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTOGet).isNotNull();
        assertThat(plateDTOGet.getId()).isEqualTo(plateDTOId);
        assertThat(plateDTOGet.getWells().size()).isEqualTo(12);
    }
}
