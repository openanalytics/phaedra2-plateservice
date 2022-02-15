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

import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellTemplateService;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
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
