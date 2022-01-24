package eu.openanalytics.phaedra.plateservice;

public class PlateMeasurementIntegrationTest {

//    @Test
//    public void setActivePlateMeasurement() throws Exception {
//        var res1 = performRequest(get("/plate/2000/measurement/1000"), HttpStatus.OK, PlateMeasurementDTO.class);
//        Assertions.assertTrue(res1.getMeasurementId().equals(1000L));
//        Assertions.assertTrue(res1.getActive());
//
//        var res2 = performRequest(get("/plate/2000/measurement/2000"), HttpStatus.OK, PlateMeasurementDTO.class);
//        Assertions.assertTrue(res2.getMeasurementId().equals(2000L));
//        Assertions.assertFalse(res2.getActive());
//
//        performRequest(put("/plate/2000/measurement/2000", null), HttpStatus.OK, PlateMeasurementDTO.class);
//
//        res1 = performRequest(get("/plate/2000/measurement/1000"), HttpStatus.OK, PlateMeasurementDTO.class);
//        Assertions.assertTrue(res1.getMeasurementId().equals(1000L));
//        Assertions.assertFalse(res1.getActive());
//
//        res2 = performRequest(get("/plate/2000/measurement/2000"), HttpStatus.OK, PlateMeasurementDTO.class);
//        Assertions.assertTrue(res2.getMeasurementId().equals(2000L));
//        Assertions.assertTrue(res2.getActive());
//    }
}
