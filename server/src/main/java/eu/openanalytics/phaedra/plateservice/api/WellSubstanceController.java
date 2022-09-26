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

import eu.openanalytics.phaedra.plateservice.service.WellSubstanceService;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumartion.SubstanceType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
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
    public ResponseEntity<WellSubstanceDTO> getWellSubstance(@RequestParam(required = false) long wellId) {
        WellSubstanceDTO result = wellSubstanceService.getWellSubstanceByWellId(wellId);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/well-substance", params = {"plateId"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateId(plateId);
        if (CollectionUtils.isNotEmpty(results))
            return new ResponseEntity<>(results, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/well-substance", params = {"plateId", "substanceName"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId,
                                                                    @RequestParam(required = false) String substanceName) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndName(plateId, substanceName);
        if (CollectionUtils.isNotEmpty(results))
            return new ResponseEntity<>(results, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/well-substance", params = {"plateId", "substanceType"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId,
                                                                    @RequestParam(required = false) SubstanceType substanceType) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndType(plateId, substanceType);
        if (CollectionUtils.isNotEmpty(results))
            return new ResponseEntity<>(results, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/well-substance", params = {"plateId", "wellTypes"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) Long plateId,
                                                                    @RequestParam(required = false) List<String> wellTypes) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstanceByPlateIdAndWellTypes(plateId, wellTypes);
        if (CollectionUtils.isNotEmpty(results))
            return new ResponseEntity<>(results, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
