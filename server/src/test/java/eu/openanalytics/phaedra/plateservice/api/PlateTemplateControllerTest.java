package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.plateservice.support.AbstractIntegrationTest;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class PlateTemplateControllerTest extends AbstractIntegrationTest {

    private static final PlateTemplateService plateTemplateService = mock(PlateTemplateService.class);
    private static final WellTemplateService wellTemplateService = mock(WellTemplateService.class);

    @Test
    public void plateTemplatePostTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").build();
        when(plateTemplateService.createPlateTemplate(plateTemplateDTO)).thenReturn(plateTemplateDTO);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);

        var httpEntity = controller.createPlateTemplate(plateTemplateDTO);
        assertThat(httpEntity.getBody().getCreatedOn()).isEqualTo(plateTemplateDTO.getCreatedOn());
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void plateTemplatePutTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").build();
        doNothing().when(plateTemplateService).updatePlateTemplate(plateTemplateDTO);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);

        var httpEntity = controller.updatePlate(plateTemplateDTO);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void plateTemplateDeleteTest() {
        doNothing().when(plateTemplateService).deletePlateTemplate(1111L);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);
        var httpEntity = controller.deletePlate(1111L);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void plateTemplateGetOneFoundTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").build();
        when(plateTemplateService.getPlateTemplateById(1111L)).thenReturn(plateTemplateDTO);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);
        var httpEntity = controller.getPlateTemplate(1111L);
        assertThat(httpEntity.getBody().getCreatedOn()).isEqualTo(plateTemplateDTO.getCreatedOn());
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void plateTemplateGetNotFoundTest() {
        when(plateTemplateService.getPlateTemplateById(1111L)).thenReturn(null);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);
        var httpEntity = controller.getPlateTemplate(1111L);
        assertThat(httpEntity.getBody()).isEqualTo(null);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void plateTemplateGetMultipleFoundTest() {
        PlateTemplateDTO plateTemplateDTO = PlateTemplateDTO.builder().rows(2).columns(3).createdOn(new Date()).createdBy("smarien").build();
        List<PlateTemplateDTO> list = new ArrayList<>();
        list.add(plateTemplateDTO);
        when(plateTemplateService.getAllPlateTemplates()).thenReturn(list);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);
        var httpEntity = controller.getPlateTemplates();
        assertThat(httpEntity.getBody()).isEqualTo(list);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void plateTemplateGetMultipleNotFoundTest() {
        List<PlateTemplateDTO> list = new ArrayList<>();
        when(plateTemplateService.getAllPlateTemplates()).thenReturn(list);
        PlateTemplateController controller = new PlateTemplateController(plateTemplateService, wellTemplateService);
        var httpEntity = controller.getPlateTemplates();
        assertThat(httpEntity.getBody()).isEqualTo(list);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
