package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlateGraphQLController {

    private final PlateMeasurementService plateMeasurementService;

    public PlateGraphQLController(PlateMeasurementService plateMeasurementService) {
        this.plateMeasurementService = plateMeasurementService;
    }

    @QueryMapping
    public List<PlateMeasurementDTO> plateMeasurements(@Argument Long plateId, @Argument Boolean active) {
        if (active) {
            return plateMeasurementService.getPlateMeasurements(plateId);
        } else {
            return List.of(plateMeasurementService.getActivePlateMeasurement(plateId));
        }
    }
}
