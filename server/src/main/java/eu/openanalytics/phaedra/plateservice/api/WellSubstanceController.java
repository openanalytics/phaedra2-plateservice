package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.WellSubstanceService;
import eu.openanalytics.phaedra.platservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.platservice.enumartion.SubstanceType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
@RestController
public class WellSubstanceController {

    private WellSubstanceService wellSubstanceService;

    public WellSubstanceController(WellSubstanceService wellSubstanceService){
        this.wellSubstanceService = wellSubstanceService;
    }

    @PostMapping("/well-substance")
    public ResponseEntity<WellSubstanceDTO> createWellSubstance(@RequestBody WellSubstanceDTO wellSubstanceDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("/well-substance")
    public ResponseEntity<WellSubstanceDTO> updateWellSubstance(@RequestBody WellSubstanceDTO wellSubstanceDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/well-substance/id")
    public ResponseEntity<WellSubstanceDTO> deleteWellSubstance(@PathVariable long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/well-substance")
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/well-substance", params = {"wellId"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long wellId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/well-substance", params = {"substanceName"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) String substanceName) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/well-substance", params = {"substanceType"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) SubstanceType substanceType) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
