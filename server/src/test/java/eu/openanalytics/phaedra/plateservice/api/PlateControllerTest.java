package eu.openanalytics.phaedra.plateservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.support.Containers;
import eu.openanalytics.phaedra.plateservice.dto.*;
import eu.openanalytics.phaedra.plateservice.enumartion.LinkStatus;
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
@AutoConfigureMockMvc(addFilters = false)
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
    public void plateGetOneFoundTest() throws Exception {
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
        PlateDTO plate = new PlateDTO();
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

    @Test
    public void linkPlateNotFoundPlateTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(put("/plate/{plateId}/link/{plateTemplateId}", 1234L, 1234L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void linkPlateNotFoundPlateTemplateTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(put("/plate/{plateId}/link/{plateTemplateId}", 1000L, 1234L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void linkPlateWrongDimensionsTest() throws Exception {
        PlateTemplate newPlateTemplate = new PlateTemplate();
        newPlateTemplate.setRows(22);
        newPlateTemplate.setColumns(33);
        newPlateTemplate.setCreatedOn(new Date());
        newPlateTemplate.setCreatedBy("smarien");

        String requestBody = objectMapper.writeValueAsString(newPlateTemplate);
        MvcResult mvcResult = this.mockMvc.perform(post("/plate-template").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(put("/plate/{plateId}/link/{plateTemplateId}", 1000L, 1L))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void linkPlateSimpleTest() throws Exception {
        //Add template
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

        //Add Plate
        Plate plate = new Plate();
        plate.setRows(2);
        plate.setColumns(3);
        plate.setExperimentId(1000L);
        plate.setSequence(1);

        String requestBody2 = objectMapper.writeValueAsString(plate);
        MvcResult mvcResult2 = this.mockMvc.perform(post("/plate").contentType(MediaType.APPLICATION_JSON).content(requestBody2))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult3 = this.mockMvc.perform(put("/plate/{plateId}/link/{plateTemplateId}", 1L, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long plateId = 1L;

        MvcResult mvcResult4 = this.mockMvc.perform(get("/plate/{plateId}", plateId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTO = objectMapper.readValue(mvcResult4.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTO).isNotNull();
        assertThat(plateDTO.getId()).isEqualTo(plateId);
        for (WellDTO w : plateDTO.getWells()) {
            assertThat(w.getWellType()).isEqualTo("EMPTY");
        }
    }

    @Test
    public void linkPlateHardTest() throws Exception {
        //Add template
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
        List<WellTemplateDTO> wellTemplateDTOS = plateTemplateDTOResult.getWells();
        assertThat(wellTemplateDTOS.size()).isEqualTo(6);

        //Add Plate
        Plate plate = new Plate();
        plate.setRows(2);
        plate.setColumns(3);
        plate.setExperimentId(1000L);
        plate.setSequence(1);

        String requestBody2 = objectMapper.writeValueAsString(plate);
        MvcResult mvcResult2 = this.mockMvc.perform(post("/plate").contentType(MediaType.APPLICATION_JSON).content(requestBody2))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        //Change WellTemplates
        wellTemplateDTOS.get(0).setWellType("HC");
        wellTemplateDTOS.get(0).setSubstanceType("COMPOUND");
        wellTemplateDTOS.get(0).setSubstanceName("eeee");
        wellTemplateDTOS.get(1).setWellType("LC");
        wellTemplateDTOS.get(1).setSubstanceType("COMPOUND");
        wellTemplateDTOS.get(1).setSubstanceName("test-name");
        wellTemplateDTOS.get(1).setConcentration(0.1);
        wellTemplateDTOS.get(2).setWellType("EMPTY");
        wellTemplateDTOS.get(3).setWellType("SAMPLE");
        wellTemplateDTOS.get(3).setSubstanceType("COMPOUND");
        wellTemplateDTOS.get(3).setSubstanceName("eeee");
        wellTemplateDTOS.get(4).setWellType("LC");
        wellTemplateDTOS.get(4).setSubstanceType("COMPOUND");
        wellTemplateDTOS.get(4).setSubstanceName("test-name2");
        wellTemplateDTOS.get(5).setWellType("HC");
        wellTemplateDTOS.get(5).setSubstanceType("COMPOUND");
        wellTemplateDTOS.get(5).setSubstanceName("eeee");
        wellTemplateDTOS.get(5).setConcentration(0.3);


        String requestBody3 = objectMapper.writeValueAsString(wellTemplateDTOS);
        this.mockMvc.perform(put("/well-templates").contentType(MediaType.APPLICATION_JSON).content(requestBody3))
                .andDo(print())
                .andExpect(status().isOk());

        //Link
        this.mockMvc.perform(put("/plate/{plateId}/link/{plateTemplateId}", 1L, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult3 = this.mockMvc.perform(get("/plate/{plateId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTO = objectMapper.readValue(mvcResult3.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTO).isNotNull();
        assertThat(plateDTO.getId()).isEqualTo(1L);
        assertThat(plateDTO.getLinkSource()).isEqualTo("layout-template");
        assertThat(plateDTO.getLinkStatus()).isEqualTo(LinkStatus.LINKED);
        assertThat(Long.parseLong(plateDTO.getLinkTemplateId())).isEqualTo(1L);
        assertThat(plateDTO.getLinkedOn()).isNotNull();
        List<WellDTO> wellDTOS = plateDTO.getWells();
        assertThat(wellDTOS.get(0).getWellType()).isEqualTo("HC");
        assertThat(wellDTOS.get(0).getWellSubstance().getType()).isEqualTo("COMPOUND");
        assertThat(wellDTOS.get(0).getWellSubstance().getConcentration()).isEqualTo(0.0);
        assertThat(wellDTOS.get(1).getWellType()).isEqualTo("LC");
        assertThat(wellDTOS.get(1).getWellSubstance().getName()).isEqualTo("test-name");
        assertThat(wellDTOS.get(1).getWellSubstance().getConcentration()).isEqualTo(0.1);
        assertThat(wellDTOS.get(2).getWellType()).isEqualTo("EMPTY");
        assertThat(wellDTOS.get(2).getWellSubstance()).isNull();
        assertThat(wellDTOS.get(3).getWellType()).isEqualTo("SAMPLE");
        assertThat(wellDTOS.get(3).getWellSubstance().getType()).isEqualTo("COMPOUND");
        assertThat(wellDTOS.get(4).getWellType()).isEqualTo("LC");
        assertThat(wellDTOS.get(4).getWellSubstance().getName()).isEqualTo("test-name2");
        assertThat(wellDTOS.get(5).getWellType()).isEqualTo("HC");
        assertThat(wellDTOS.get(5).getWellSubstance().getConcentration()).isEqualTo(0.3);

        //Change again to test delete and edit functionality
        wellTemplateDTOS.get(0).setSubstanceName("qwerty"); //changed
        wellTemplateDTOS.get(0).setSubstanceType("VIRUS"); //changed
        wellTemplateDTOS.get(1).setSubstanceType(""); //Should get removed

        String requestBody4 = objectMapper.writeValueAsString(wellTemplateDTOS);
        this.mockMvc.perform(put("/well-templates").contentType(MediaType.APPLICATION_JSON).content(requestBody4))
                .andDo(print())
                .andExpect(status().isOk());

        //Link again
        this.mockMvc.perform(put("/plate/{plateId}/link/{plateTemplateId}", 1L, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult changedWell1 = this.mockMvc.perform(get("/plate/{plateId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PlateDTO plateDTOChanged = objectMapper.readValue(changedWell1.getResponse().getContentAsString(), PlateDTO.class);
        assertThat(plateDTOChanged).isNotNull();
        assertThat(plateDTOChanged.getId()).isEqualTo(1L);
        List<WellDTO> changedWellDTOS = plateDTOChanged.getWells();
        assertThat(changedWellDTOS.get(0).getWellType()).isEqualTo("HC");
        assertThat(changedWellDTOS.get(0).getWellSubstance().getName()).isEqualTo("qwerty");
        assertThat(changedWellDTOS.get(0).getWellSubstance().getType()).isEqualTo("VIRUS");
        assertThat(changedWellDTOS.get(0).getWellSubstance().getId()).isEqualTo(wellDTOS.get(0).getWellSubstance().getId());
        assertThat(changedWellDTOS.get(1).getWellType()).isEqualTo("LC");
        assertThat(changedWellDTOS.get(1).getWellSubstance()).isNull();
    }

}
