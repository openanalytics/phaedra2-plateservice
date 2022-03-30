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

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;

@RestController
public class PlateController {

    private final PlateService plateService;
    private final WellService wellService;
    private final PlateMeasurementService plateMeasurementService;

    public PlateController(PlateService plateService, WellService wellService, PlateMeasurementService plateMeasurementService) {
        this.plateService = plateService;
        this.wellService = wellService;
        this.plateMeasurementService = plateMeasurementService;
    }

    @PostMapping(value = "/plate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateDTO> createPlate(@RequestBody PlateDTO plateDTO) {
        PlateDTO result = plateService.createPlate(plateDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/plate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateDTO> updatePlate(@RequestBody PlateDTO plateDTO) {
        PlateDTO result = plateService.updatePlate(plateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/plate/{plateId}")
    public ResponseEntity<Void> deletePlate(@PathVariable long plateId) {
        plateService.deletePlate(plateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/plate/{plateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateDTO> getPlate(@PathVariable long plateId) {
        PlateDTO response = plateService.getPlateById(plateId);
        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/plate", params = {"experimentId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlateDTO>> getPlatesByExperiment(@RequestParam(required = false) long experimentId) {
        List<PlateDTO> response = plateService.getPlatesByExperimentId(experimentId);
        if (CollectionUtils.isNotEmpty(response))
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/plate", params = {"barcode"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlateDTO>> getPlatesByBarcode(@RequestParam(required = false) String barcode) {
        List<PlateDTO> response = plateService.getPlatesByBarcode(barcode);
        if (CollectionUtils.isNotEmpty(response))
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/plate/{plateId}/wells", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WellDTO>> getWells(@PathVariable long plateId) {
        List<WellDTO> wells = wellService.getWellsByPlateId(plateId);
        if (wells.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(wells);
    }

    @PostMapping(value = "/plate/{plateId}/measurement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateMeasurementDTO> addMeasurement(@PathVariable long plateId, @RequestBody PlateMeasurementDTO plateMeasurementDTO) {
        PlateMeasurementDTO result = plateMeasurementService.addPlateMeasurement(plateMeasurementDTO);
        plateMeasurementService.setActivePlateMeasurement(plateMeasurementDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/plate/{plateId}/measurements", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlateMeasurementDTO>> getPlateMeasurements(@PathVariable(name = "plateId") long plateId) {
        List<PlateMeasurementDTO> plateMeasurements = plateMeasurementService.getPlateMeasurements(plateId);
        return new ResponseEntity<>(plateMeasurements, HttpStatus.OK);
    }

    @GetMapping(value = "/plate/{plateId}/measurement/{measId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateMeasurementDTO> getPlateMeasurementByMeasId(@PathVariable(name = "plateId") long plateId,
                                                                    @PathVariable(name = "measId") long measId) {
        PlateMeasurementDTO result = plateMeasurementService.getPlateMeasurementByMeasId(plateId, measId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/plate/{plateId}/measurement/{measId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateMeasurementDTO> setActiveMeasurement(@PathVariable(name = "plateId") long plateId,
											   @PathVariable(name = "measId") long measId,
                                               @RequestBody PlateMeasurementDTO plateMeasurementDTO) {
		PlateMeasurementDTO result = plateMeasurementService.setActivePlateMeasurement(plateMeasurementDTO);
		return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/plate/{plateId}/link/{plateTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateDTO> linkPlate(@PathVariable long plateId, @PathVariable long plateTemplateId) {
        PlateDTO plateDTO = plateService.linkPlate(plateId,plateTemplateId);
        return new ResponseEntity<>(plateDTO, HttpStatus.OK);
    }
}
