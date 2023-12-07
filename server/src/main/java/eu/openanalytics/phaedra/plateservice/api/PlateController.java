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

import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plates")
public class PlateController {

    private final PlateService plateService;
    private final WellService wellService;
    private final PlateMeasurementService plateMeasurementService;

    public PlateController(PlateService plateService, WellService wellService, PlateMeasurementService plateMeasurementService) {
        this.plateService = plateService;
        this.wellService = wellService;
        this.plateMeasurementService = plateMeasurementService;
    }

    @PostMapping
    public ResponseEntity<PlateDTO> createPlate(@RequestBody PlateDTO plateDTO) {
        PlateDTO result = plateService.createPlate(plateDTO);
        if (result == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{plateId}")
    public ResponseEntity<PlateDTO> updatePlate(@PathVariable long plateId, @RequestBody PlateDTO plateDTO) {
    	plateDTO.setId(plateId);
        PlateDTO result = plateService.updatePlate(plateDTO);
        if (result == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{plateId}")
    public ResponseEntity<Void> deletePlate(@PathVariable long plateId) {
        plateService.deletePlate(plateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{plateId}")
    public ResponseEntity<PlateDTO> getPlate(@PathVariable long plateId) {
        PlateDTO plate = plateService.getPlateById(plateId);
        if (plate == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(plate, HttpStatus.OK);
    }

    @GetMapping(params = {"experimentId"})
    public ResponseEntity<List<PlateDTO>> getPlatesByExperiment(@RequestParam(required = false) long experimentId) {
        List<PlateDTO> response = plateService.getPlatesByExperimentId(experimentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"barcode"})
    public ResponseEntity<List<PlateDTO>> getPlatesByBarcode(@RequestParam(required = false) String barcode) {
        List<PlateDTO> response = plateService.getPlatesByBarcode(barcode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{plateId}/wells")
    public ResponseEntity<List<WellDTO>> getWells(@PathVariable long plateId) {
        List<WellDTO> wells = wellService.getWellsByPlateId(plateId);
        if (wells.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(wells);
    }

    @PostMapping(value = "/{plateId}/measurements")
    public ResponseEntity<PlateMeasurementDTO> addMeasurement(@PathVariable long plateId, @RequestBody PlateMeasurementDTO plateMeasurementDTO) {
        PlateMeasurementDTO result = plateMeasurementService.addPlateMeasurement(plateMeasurementDTO, true);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{plateId}/measurements")
    public ResponseEntity<List<PlateMeasurementDTO>> getPlateMeasurements(@PathVariable(name = "plateId") long plateId) {
        List<PlateMeasurementDTO> plateMeasurements = plateMeasurementService.getPlateMeasurements(plateId);
        return new ResponseEntity<>(plateMeasurements, HttpStatus.OK);
    }

    @GetMapping(value = "/{plateId}/measurements/{measId}")
    public ResponseEntity<PlateMeasurementDTO> getPlateMeasurementByMeasId(
    		@PathVariable(name = "plateId") long plateId,
    		@PathVariable(name = "measId") long measId) {
        PlateMeasurementDTO result = plateMeasurementService.getPlateMeasurementByMeasId(plateId, measId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/{plateId}/measurements/{measId}")
    public ResponseEntity<PlateMeasurementDTO> setActiveMeasurement(
    		@PathVariable(name = "plateId") long plateId,
    		@PathVariable(name = "measId") long measId,
    		@RequestBody PlateMeasurementDTO plateMeasurementDTO) {
		PlateMeasurementDTO result = plateMeasurementService.setActivePlateMeasurement(plateMeasurementDTO);
		return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/{plateId}/link/{plateTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateDTO> linkPlate(@PathVariable long plateId, @PathVariable long plateTemplateId) {
        PlateDTO plateDTO = plateService.linkPlate(plateId,plateTemplateId);
        return new ResponseEntity<>(plateDTO, HttpStatus.OK);
    }
}
