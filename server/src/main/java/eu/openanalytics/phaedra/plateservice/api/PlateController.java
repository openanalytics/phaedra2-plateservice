/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.plateservice.dto.AcceptWellsDTO;
import eu.openanalytics.phaedra.plateservice.dto.DisapprovePlatesDTO;
import eu.openanalytics.phaedra.plateservice.dto.InvalidatePlatesDTO;
import eu.openanalytics.phaedra.plateservice.dto.LinkPlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.LinkPlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.MovePlatesDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.plateservice.dto.RejectWellsDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellStatusDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.LinkType;
import eu.openanalytics.phaedra.plateservice.exceptions.ClonePlateException;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;

@RestController
@RequestMapping("/plates")
public class PlateController {

    private final PlateService plateService;
    private final WellService wellService;
    private final PlateMeasurementService plateMeasurementService;
    private final MetadataServiceClient metadataServiceHttpClient;

    public PlateController (
        PlateService plateService,
        WellService wellService,
        PlateMeasurementService plateMeasurementService,
        MetadataServiceClient metadataServiceClient
    )
    {
        this.plateService = plateService;
        this.wellService = wellService;
        this.plateMeasurementService = plateMeasurementService;
        this.metadataServiceHttpClient = metadataServiceClient;
    }

    @PostMapping
    public ResponseEntity<PlateDTO> createPlate(@RequestBody PlateDTO plateDTO) {
        PlateDTO result = plateService.createPlate(plateDTO);
        if (result == null) return ResponseEntity.notFound().build();

        if (CollectionUtils.isNotEmpty(plateDTO.getTags())) {
            metadataServiceHttpClient.addTags(ObjectClass.PLATE.name(), result.getId(), plateDTO.getTags());
        }

        if (CollectionUtils.isNotEmpty(plateDTO.getProperties())) {
            Map<String, String> properties = plateDTO.getProperties().stream().collect(Collectors.toMap(PropertyDTO::propertyName, PropertyDTO::propertyValue));
            metadataServiceHttpClient.addProperties(ObjectClass.PLATE.name(), result.getId(), properties);
        }

        if (ObjectUtils.isNotEmpty(plateDTO.getMeasurementId())) {
            plateMeasurementService.linkMeasurement(result.getId(), plateDTO.getMeasurementId());
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlateDTO>> getPlates(
        @RequestParam(required = false) String barcode,
        @RequestParam(required = false) Long experimentId) {

        List<PlateDTO> results = new ArrayList<>();

        if (StringUtils.isNotBlank(barcode))
            results.addAll(plateService.getPlatesByBarcode(barcode));
        if (ObjectUtils.isNotEmpty(experimentId))
            results.addAll(plateService.getPlatesByExperimentId(experimentId));
        if (StringUtils.isBlank(barcode) && ObjectUtils.isEmpty(experimentId)) {
            results.addAll(plateService.getPlates(List.of()));
        }

        return ResponseEntity.ok(results);
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

    @DeleteMapping()
    public ResponseEntity<Void> deletePlates(@RequestBody List<Long> plateIds) {
        plateService.deletePlates(plateIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Void> deletePlates_Deprecated(@RequestBody List<Long> plateIds) {
        try {
            for (Long plateId: plateIds) {
                plateService.deletePlate(plateId);
            }
            return ResponseEntity.ok().build();
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Void> disapprovePlates(@RequestBody DisapprovePlatesDTO disapprovePlatesDTO) {
        try {
            for (Long plateId : disapprovePlatesDTO.getPlateIds()) {
                plateService.disapprovePlate(plateId, disapprovePlatesDTO.getReason());
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

    @GetMapping(value = "/{plateId}/wells")
    public ResponseEntity<List<WellDTO>> getPlateWells(@PathVariable long plateId) {
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
            PlateDTO plateDTO = plateService.linkPlate(plateId, LinkType.Template, plateTemplateId);
            return ResponseEntity.ok(plateDTO);
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/link-template", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> linkPlateTemplate(@RequestBody LinkPlateTemplateDTO linkPlateTemplateDTO) {
        try {
            for (Long plateId : linkPlateTemplateDTO.getPlateIds()) {
                plateService.linkPlate(plateId, LinkType.Template, linkPlateTemplateDTO.getPlateTemplateId());
            }
            return ResponseEntity.ok().build();
        } catch (PlateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
