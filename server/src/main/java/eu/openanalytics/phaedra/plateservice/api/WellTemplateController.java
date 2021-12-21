package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/well-template/{wellTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellTemplateDTO> getWellTemplate(@PathVariable long wellTemplateId) {
        WellTemplateDTO response = wellTemplateService.getWellTemplateById(wellTemplateId);
        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/well-templates", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellTemplateDTO> updateWellTemplates(@RequestBody List<WellTemplateDTO> wellTemplateDTOS) {
        wellTemplateDTOS.forEach(wellTemplateDTO -> {
            wellTemplateService.updateWellTemplate(wellTemplateDTO);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
