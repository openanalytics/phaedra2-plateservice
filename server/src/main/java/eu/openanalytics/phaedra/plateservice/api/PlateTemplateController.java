/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platetemplates")
public class PlateTemplateController {

    private final PlateTemplateService plateTemplateService;
    private final WellTemplateService wellTemplateService;

    public PlateTemplateController(PlateTemplateService plateTemplateService, WellTemplateService wellTemplateService) {
        this.plateTemplateService = plateTemplateService;
        this.wellTemplateService = wellTemplateService;
    }

    @PostMapping
    public ResponseEntity<PlateTemplateDTO> createPlateTemplate(@RequestBody PlateTemplateDTO plateTemplateDTO) {
        PlateTemplateDTO result = plateTemplateService.createPlateTemplate(plateTemplateDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{plateTemplateId}")
    public ResponseEntity<Void> updatePlate(@PathVariable long plateTemplateId, @RequestBody PlateTemplateDTO plateTemplateDTO) {
    	plateTemplateDTO.setId(plateTemplateId);
        plateTemplateService.updatePlateTemplate(plateTemplateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{plateTemplateId}")
    public ResponseEntity<Void> deletePlate(@PathVariable long plateTemplateId) {
        plateTemplateService.deletePlateTemplate(plateTemplateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{plateTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateTemplateDTO> getPlateTemplate(@PathVariable long plateTemplateId) {
        PlateTemplateDTO response = plateTemplateService.getPlateTemplateById(plateTemplateId);
        if (response == null) {
        	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
        	return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{plateTemplateId}/wells", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WellTemplateDTO>> getWellTemplatesByPlateTemplateId(@PathVariable long plateTemplateId) {
        List<WellTemplateDTO> response = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateId);
        if (response == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<PlateTemplateDTO>> getPlateTemplatesByName(@RequestParam(required = false) String name) {
        List<PlateTemplateDTO> result = plateTemplateService.getPlateTemplatesByName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PlateTemplateDTO>> getPlateTemplates() {
    	List<PlateTemplateDTO> result = plateTemplateService.getAllPlateTemplates();
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
