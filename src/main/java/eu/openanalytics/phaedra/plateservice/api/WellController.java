package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
@RestController
public class WellController {

    private WellService wellService;

    public WellController(WellService wellService) {
        this.wellService = wellService;
    }

    @PostMapping(value = "/well", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellDTO> createWell(@RequestBody WellDTO wellDTO) {
        WellDTO response = wellService.createWell(wellDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/well", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellDTO> updateWell(@RequestBody WellDTO wellDTO) {
        WellDTO response = wellService.updateWell(wellDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
