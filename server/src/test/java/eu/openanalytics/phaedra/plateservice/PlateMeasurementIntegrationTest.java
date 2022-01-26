package eu.openanalytics.phaedra.plateservice;

import eu.openanalytics.phaedra.plateservice.support.AbstractIntegrationTest;
import eu.openanalytics.phaedra.platservice.dto.PlateMeasurementDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PlateMeasurementIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void setActivePlateMeasurement() throws Exception {
        var res1 = performRequest(get("/plate/2000/measurement/1000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res1.getMeasurementId().equals(1000L));
        Assertions.assertTrue(res1.getActive());

        var res2 = performRequest(get("/plate/2000/measurement/2000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res2.getMeasurementId().equals(2000L));
        Assertions.assertFalse(res2.getActive());

        res1.setActive(false);
        res2.setActive(true);
        performRequest(put("/plate/2000/measurement/1000", res1), HttpStatus.OK, PlateMeasurementDTO.class);
        performRequest(put("/plate/2000/measurement/2000", res2), HttpStatus.OK, PlateMeasurementDTO.class);

        res1 = performRequest(get("/plate/2000/measurement/1000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res1.getMeasurementId().equals(1000L));
        Assertions.assertFalse(res1.getActive());

        res2 = performRequest(get("/plate/2000/measurement/2000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res2.getMeasurementId().equals(2000L));
        Assertions.assertTrue(res2.getActive());
    }
}
