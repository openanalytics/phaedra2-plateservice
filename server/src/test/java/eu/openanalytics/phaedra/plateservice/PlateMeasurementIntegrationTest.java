package eu.openanalytics.phaedra.plateservice;

import eu.openanalytics.phaedra.plateservice.support.AbstractIntegrationTest;
import eu.openanalytics.phaedra.platservice.dto.PlateMeasurementDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PlateMeasurementIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void simpleCreateAndGetTest() throws Exception {
        var input1 = PlateMeasurementDTO.builder()
                .plateId(1000L)
                .measurementId(1000L)
                .active(true)
                .linkedBy("sberberovic")
                .linkedOn(new Date())
                .build();

        performRequest(post("/plate/1000/measurement", input1), HttpStatus.OK, PlateMeasurementDTO.class);

        var res1 = performRequest(get("/plate/1000/measurements"), HttpStatus.OK, List.class);
        Assertions.assertTrue(res1.size() == 1);
        Assertions.assertTrue(((LinkedHashMap) res1.get(0)).get("plateId").equals(1000));
        Assertions.assertTrue(((LinkedHashMap) res1.get(0)).get("measurementId").equals(1000));
    }

    @Test
    public void setActivePlateMeasurement() throws Exception {
        var res1 = performRequest(get("/plate/2000/measurement/1000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res1.getMeasurementId().equals(1000L));
        Assertions.assertTrue(res1.getActive());

        var res2 = performRequest(get("/plate/2000/measurement/2000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res2.getMeasurementId().equals(2000L));
        Assertions.assertFalse(res2.getActive());

        performRequest(put("/plate/2000/measurement/2000", null), HttpStatus.OK, PlateMeasurementDTO.class);

        res1 = performRequest(get("/plate/2000/measurement/1000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res1.getMeasurementId().equals(1000L));
        Assertions.assertFalse(res1.getActive());

        res2 = performRequest(get("/plate/2000/measurement/2000"), HttpStatus.OK, PlateMeasurementDTO.class);
        Assertions.assertTrue(res2.getMeasurementId().equals(2000L));
        Assertions.assertTrue(res2.getActive());
    }
}
