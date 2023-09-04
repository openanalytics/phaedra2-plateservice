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

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.service.WellSubstanceService;

@RestController
@RequestMapping("/wellsubstances")
public class WellSubstanceController {

    private WellSubstanceService wellSubstanceService;

    public WellSubstanceController(WellSubstanceService wellSubstanceService){
        this.wellSubstanceService = wellSubstanceService;
    }

    @PostMapping
    public ResponseEntity<WellSubstanceDTO> createWellSubstance(@RequestBody WellSubstanceDTO wellSubstanceDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("/{wellSubstanceId}")
    public ResponseEntity<WellSubstanceDTO> updateWellSubstance(@PathVariable long wellSubstanceId, @RequestBody WellSubstanceDTO wellSubstanceDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{wellSubstanceId}")
    public ResponseEntity<WellSubstanceDTO> deleteWellSubstance(@PathVariable long wellSubstanceId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(params = {"wellId"})
    public ResponseEntity<WellSubstanceDTO> getWellSubstance(@RequestParam(required = false) long wellId) {
        WellSubstanceDTO result = wellSubstanceService.getWellSubstanceByWellId(wellId);
        if (result == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
        	return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping(params = {"plateId"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateId(plateId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(params = {"plateId", "substanceName"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId,
                                                                    @RequestParam(required = false) String substanceName) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndName(plateId, substanceName);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(params = {"plateId", "substanceType"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId,
                                                                    @RequestParam(required = false) SubstanceType substanceType) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndType(plateId, substanceType);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(params = {"plateId", "substanceType", "substanceName"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) long plateId,
                                                                    @RequestParam(required = false) String substanceName,
                                                                    @RequestParam(required = false) SubstanceType substanceType) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstancesByPlateIdAndNameAndType(plateId, substanceName, substanceType);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(params = {"plateId", "wellTypes"})
    public ResponseEntity<List<WellSubstanceDTO>> getWellSubstances(@RequestParam(required = false) Long plateId,
                                                                    @RequestParam(required = false) List<String> wellTypes) {
        List<WellSubstanceDTO> results = wellSubstanceService.getWellSubstanceByPlateIdAndWellTypes(plateId, wellTypes);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
