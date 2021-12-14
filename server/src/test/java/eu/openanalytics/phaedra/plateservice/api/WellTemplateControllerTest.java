package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.plateservice.support.AbstractIntegrationTest;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WellTemplateControllerTest extends AbstractIntegrationTest {

    private static final WellTemplateService wellTemplateService = mock(WellTemplateService.class);

    @Test
    public void plateTemplatePostTest() {
        WellTemplateDTO wellTemplateDTO = WellTemplateDTO.builder().row(2).column(3).wellType("EMPTY").skipped(true).build();
        when(wellTemplateService.createWellTemplate(wellTemplateDTO)).thenReturn(wellTemplateDTO);
        WellTemplateController controller = new WellTemplateController(wellTemplateService);

        var httpEntity = controller.createWellTemplate(wellTemplateDTO);
        assertThat(httpEntity.getBody()).isEqualTo(wellTemplateDTO);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void plateTemplatePutTest() {
        WellTemplateDTO wellTemplateDTO = WellTemplateDTO.builder().row(2).column(3).wellType("EMPTY").skipped(true).build();
        doNothing().when(wellTemplateService).updateWellTemplate(wellTemplateDTO);
        WellTemplateController controller = new WellTemplateController(wellTemplateService);

        var httpEntity = controller.updateWellTemplate(wellTemplateDTO);
        assertThat(httpEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
