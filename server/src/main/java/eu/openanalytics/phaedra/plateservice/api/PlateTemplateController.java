package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlateTemplateController {

    private final PlateTemplateService plateTemplateService;
    private final WellTemplateService wellTemplateService;

    public PlateTemplateController(PlateTemplateService plateTemplateService, WellTemplateService wellTemplateService) {
        this.plateTemplateService = plateTemplateService;
        this.wellTemplateService = wellTemplateService;
    }

    @PostMapping(value = "/plate-template", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateTemplateDTO> createPlateTemplate(@RequestBody PlateTemplateDTO plateTemplateDTO) {
        PlateTemplateDTO result = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/plate-template", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePlate(@RequestBody PlateTemplateDTO plateTemplateDTO) {
        plateTemplateService.updatePlateTemplate(plateTemplateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/plate-template/{plateTemplateId}")
    public ResponseEntity<Void> deletePlate(@PathVariable long plateTemplateId) {
        plateTemplateService.deletePlateTemplate(plateTemplateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/plate-template/{plateTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateTemplateDTO> getPlateTemplate(@PathVariable long plateTemplateId) {
        PlateTemplateDTO response = plateTemplateService.getPlateTemplateById(plateTemplateId);
        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/plate-templates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlateTemplateDTO>> getPlateTemplates() {
        List<PlateTemplateDTO> result = plateTemplateService.getAllPlateTemplates();
        if (CollectionUtils.isNotEmpty(result))
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

}
