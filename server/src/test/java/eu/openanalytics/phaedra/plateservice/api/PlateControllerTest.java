package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateMeasurementDTO;
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
    public void platePostTest() throws Exception {
        Plate plate = new Plate();
        plate.setRows(3);
        plate.setColumns(4);
        plate.setExperimentId(1000L);
        plate.setSequence(1);

        String requestBody = objectMapper.writeValueAsString(plate);
        MvcResult mvcResult = this.mockMvc.perform(post("/plate").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        PlateDTO plateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTO).isNotNull();
        assertThat(plateDTO.getId()).isEqualTo(1L);
        assertThat(plateDTO.getWells().size()).isEqualTo(12);
    }

    @Test
    public void platePutTest() throws Exception {
        Long plateId = 1000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate/{plateId}", plateId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTOGet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTOGet).isNotNull();
        assertThat(plateDTOGet.getId()).isEqualTo(plateId);
        assertThat(plateDTOGet.getSequence()).isEqualTo(0);

        Integer newSequence = 2;
        plateDTOGet.setSequence(newSequence);
        plateDTOGet.setInvalidatedReason("test");
        plateDTOGet.setDisapprovedReason("test2");

        String requestBody = objectMapper.writeValueAsString(plateDTOGet);
        this.mockMvc.perform(put("/plate").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/plate/{plateId}", plateDTOGet.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTO.getSequence()).isEqualTo(newSequence);
        assertThat(plateDTO.getInvalidatedReason()).isEqualTo("test");
        assertThat(plateDTO.getDisapprovedReason()).isEqualTo("test2");
    }

    @Test
    public void plateDeleteTest() throws Exception {
        Long plateTemplateId = 1000L;

        this.mockMvc.perform(delete("/plate/{plateId}", plateTemplateId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void plateTemplateGetOneFoundTest() throws Exception {
        Long plateId = 1000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate/{plateId}", plateId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTO).isNotNull();
        assertThat(plateDTO.getId()).isEqualTo(plateId);
    }

    @Test
    public void plateGetNotFoundTest() throws Exception {
        Long plateId = 1111L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate/{plateId}", plateId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    public void plateGetMultipleFoundTest() throws Exception {
        //Check size of list
        MvcResult mvcResult = this.mockMvc.perform(get("/plate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Plate> plates = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plates).isNotNull();
        assertThat(plates.size()).isEqualTo(2);

        //Add new plate
        Plate plate = new Plate();
        plate.setRows(3);
        plate.setColumns(4);
        plate.setExperimentId(1000L);
        plate.setSequence(1);

        String requestBody = objectMapper.writeValueAsString(plate);
        this.mockMvc.perform(post("/plate").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(get("/plate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Plate> plates2 = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), List.class);
        assertThat(plates2).isNotNull();
        assertThat(plates2.size()).isEqualTo(3);
    }

    @Test
    public void plateGetMultipleNotFoundTest() throws Exception {
        //Check size of list
        MvcResult mvcResult = this.mockMvc.perform(get("/plate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Plate> plates = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plates).isNotNull();
        assertThat(plates.size()).isEqualTo(2);

        //Delete plate
        Long plateId = 1000L;
        Long plateId2 = 2000L;
        this.mockMvc.perform(delete("/plate/{plateId}", plateId))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(delete("/plate/{plateId}", plateId2))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult2 = this.mockMvc.perform(get("/plate"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        List<Plate> plates2 = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), List.class);
        assertThat(plates2).isNotNull();
        assertThat(plates2.size()).isEqualTo(0);
    }

    @Test
    public void getPlatesByExperimentFoundTest() throws Exception {
        Long experimentId = 1000L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate/").param("experimentId", experimentId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Plate> plates2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plates2).isNotNull();
        assertThat(plates2.size()).isEqualTo(2);
    }

    @Test
    public void getPlatesByExperimentNotFoundTest() throws Exception {
        Long experimentId = 1111L;

        MvcResult mvcResult = this.mockMvc.perform(get("/plate/").param("experimentId", experimentId.toString()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        List<Plate> plates2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plates2).isEmpty();
    }

    @Test
    public void getPlatesByBarcodeFoundTest() throws Exception {
        String barcode = "barcode1";

        MvcResult mvcResult = this.mockMvc.perform(get("/plate").param("barcode", barcode))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<PlateDTO> plateDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plateDTOs).isNotNull();
        assertThat(plateDTOs.size()).isEqualTo(1);
    }

    @Test
    public void getPlatesByBarcodeNotFoundTest() throws Exception {
        String barcode = "barcode12345";

        MvcResult mvcResult = this.mockMvc.perform(get("/plate").param("barcode", barcode))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        List<PlateDTO> plateDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(plateDTOs).isNotNull();
        assertThat(plateDTOs).isEmpty();
    }

    @Test
    public void plateAreWellsPresentTest() throws Exception {
        Plate plate = new Plate();
        plate.setRows(3);
        plate.setColumns(4);
        plate.setExperimentId(1000L);
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

    @Test
    public void plateMeasurementPostTest() throws Exception {
        PlateMeasurement plateMeasurement = new PlateMeasurement();
        plateMeasurement.setPlateId(1000L);
        plateMeasurement.setMeasurementId(1000L);
        plateMeasurement.setActive(true);

        String requestBody = objectMapper.writeValueAsString(plateMeasurement);
        MvcResult mvcResult = this.mockMvc.perform(post("/plate/{plateId}/measurement", 1000L).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        PlateMeasurementDTO plateMeasurementDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlateMeasurementDTO.class);
        assertThat(plateMeasurementDTO).isNotNull();
        assertThat(plateMeasurementDTO.getMeasurementId()).isEqualTo(1000L);
    }

}
