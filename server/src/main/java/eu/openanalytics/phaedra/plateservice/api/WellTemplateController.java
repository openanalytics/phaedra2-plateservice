/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
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
