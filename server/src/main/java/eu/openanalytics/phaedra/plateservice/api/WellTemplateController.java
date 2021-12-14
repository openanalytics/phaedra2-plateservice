package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WellTemplateController {

    private final WellTemplateService wellTemplateService;

    public WellTemplateController(WellTemplateService wellTemplateService){
        this.wellTemplateService = wellTemplateService;
    }

    @PostMapping(value = "/well-template", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellTemplateDTO> createWellTemplate(@RequestBody WellTemplateDTO wellTemplateDTO) {
        WellTemplateDTO response = wellTemplateService.createWellTemplate(wellTemplateDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/well-template", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellTemplateDTO> updateWellTemplate(@RequestBody WellTemplateDTO wellTemplateDTO) {
        wellTemplateService.updateWellTemplate(wellTemplateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
