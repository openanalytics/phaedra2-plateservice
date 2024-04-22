/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import eu.openanalytics.phaedra.plateservice.dto.*;
import eu.openanalytics.phaedra.plateservice.exceptions.ClonePlateException;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
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
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{plateId}")
    public ResponseEntity<Void> deletePlate(@PathVariable long plateId) {
        plateService.deletePlate(plateId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{plateId}")
    public ResponseEntity<PlateDTO> getPlate(@PathVariable long plateId) {
        try {
            PlateDTO plate = plateService.getPlateById(plateId);
            return ResponseEntity.ok(plate);
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/validate")
    public ResponseEntity<Void> validatePlates(@RequestBody List<Long> plateIds) {
        try {
            for (Long plateId : plateIds) {
                plateService.validatePlate(plateId);
            }
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/invalidate")
    public ResponseEntity<Void> invalidatePlates(@RequestBody InvalidatePlatesDTO invalidatePlatesDTO) {
        try {
            for (Long plateId : invalidatePlatesDTO.getPlateIds()) {
                plateService.invalidatePlate(plateId, invalidatePlatesDTO.getReason());
            }
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/reset-validations")
    public ResponseEntity<Void> resetPlateValidations(@RequestBody List<Long> plateIds) {
        try {
            for (Long plateId : plateIds) {
                plateService.resetPlateValidation(plateId);
            }
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/approve")
    public ResponseEntity<Void> approvePlates(@RequestBody List<Long> plateIds) {
        try {
            for (Long plateId : plateIds) {
                plateService.approvePlate(plateId);
            }
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/disapprove")
    public ResponseEntity<Void> disapprovePlates(@RequestBody List<Long> plateIds) {
        try {
            for (Long plateId : plateIds) {
                plateService.disapprovePlate(plateId);
            }
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/clone")
    public ResponseEntity<Void> clonePlate(@RequestBody List<Long> plateIds) {
        try {
            for (Long plateId : plateIds) {
                PlateDTO clone = plateService.clonePlateById(plateId);
                if (clone != null) {
                    plateMeasurementService.clonePlateMeasurements(plateId, clone.getId());
                }
            }
            return ResponseEntity.ok().build();
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ClonePlateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/move")
    public ResponseEntity<Void> movePlate(@RequestBody MovePlatesDTO movePlatesDTO) {
        try {
            for (Long plateId : movePlatesDTO.getPlateIds()) {
                plateService.moveByPlateId(plateId, movePlatesDTO.getExperimentId());
            }
            return ResponseEntity.ok().build();
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = {"experimentId"})
    public ResponseEntity<List<PlateDTO>> getPlatesByExperiment(@RequestParam(required = false) long experimentId) {
        List<PlateDTO> response = plateService.getPlatesByExperimentId(experimentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = {"barcode"})
    public ResponseEntity<List<PlateDTO>> getPlatesByBarcode(@RequestParam(required = false) String barcode) {
        List<PlateDTO> response = plateService.getPlatesByBarcode(barcode);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{plateId}/wells")
    public ResponseEntity<List<WellDTO>> getWells(@PathVariable long plateId) {
        try {
            List<WellDTO> wells = wellService.getWellsByPlateId(plateId);
            if (wells.isEmpty()) return ResponseEntity.notFound().build();

            return ResponseEntity.ok(wells);
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{plateId}/wells")
    public ResponseEntity<Void> updateWell(@PathVariable long plateId, @RequestBody List<WellDTO> wells) {
        wellService.updateWells(wells);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{plateId}/wells/{wellId}/reject")
    public ResponseEntity<Void> rejectWell(@PathVariable long plateId, @PathVariable long wellId, @RequestBody WellStatusDTO wellStatusDTO) {
        wellService.rejectWell(plateId, wellId, wellStatusDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{plateId}/wells/reject")
    public ResponseEntity<Void> rejectWells(@PathVariable long plateId, @RequestBody RejectWellsDTO rejectWellsDTO) {
        wellService.rejectWells(plateId, rejectWellsDTO.getWellIds(), rejectWellsDTO.getWellStatusDTO());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{plateId}/wells/{wellId}/accept")
    public ResponseEntity<Void> acceptWell(@PathVariable long plateId, @PathVariable long wellId) {
        wellService.acceptWell(plateId, wellId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{plateId}/wells/accept")
    public ResponseEntity<Void> acceptWells(@PathVariable long plateId, @RequestBody AcceptWellsDTO acceptWellsDTO) {
        wellService.acceptWells(plateId, acceptWellsDTO.getWellIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{plateId}/measurements")
    public ResponseEntity<PlateMeasurementDTO> addMeasurement(@PathVariable long plateId, @RequestBody PlateMeasurementDTO plateMeasurementDTO) {
        PlateMeasurementDTO result = plateMeasurementService.addPlateMeasurement(plateMeasurementDTO, true);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{plateId}/measurements")
    public ResponseEntity<List<PlateMeasurementDTO>> getPlateMeasurements(@PathVariable(name = "plateId") long plateId) {
        List<PlateMeasurementDTO> plateMeasurements = plateMeasurementService.getPlateMeasurements(plateId);
        return ResponseEntity.ok(plateMeasurements);
    }

    @GetMapping(value = "/{plateId}/measurements/{measId}")
    public ResponseEntity<PlateMeasurementDTO> getPlateMeasurementByMeasId(
    		@PathVariable(name = "plateId") long plateId,
    		@PathVariable(name = "measId") long measId) {
        PlateMeasurementDTO result = plateMeasurementService.getPlateMeasurementByMeasId(plateId, measId);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{plateId}/measurements/{measId}")
    public ResponseEntity<PlateMeasurementDTO> setActiveMeasurement(
    		@PathVariable(name = "plateId") long plateId,
    		@PathVariable(name = "measId") long measId,
    		@RequestBody PlateMeasurementDTO plateMeasurementDTO) {
		PlateMeasurementDTO result = plateMeasurementService.setActivePlateMeasurement(plateMeasurementDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/link-measurement")
    public ResponseEntity<Void> setActiveMeasurement(@RequestBody LinkPlateMeasurementDTO linkPlateMeasurementDTO) {
        for (Long plateId : linkPlateMeasurementDTO.getPlateIds()) {
            plateMeasurementService.linkMeasurement(plateId, linkPlateMeasurementDTO.getMeasurementId());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{plateId}/link/{plateTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateDTO> setPlateTemplate(@PathVariable long plateId, @PathVariable long plateTemplateId) {
        try {
            PlateDTO plateDTO = plateService.linkPlateTemplate(plateId, plateTemplateId);
            return ResponseEntity.ok(plateDTO);
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/link-template", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> linkPlateTemplate(@RequestBody LinkPlateTemplateDTO linkPlateTemplateDTO) {
        try {
            for (Long plateId : linkPlateTemplateDTO.getPlateIds()) {
                plateService.linkPlateTemplate(plateId, linkPlateTemplateDTO.getPlateTemplateId());
            }
            return ResponseEntity.ok().build();
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
