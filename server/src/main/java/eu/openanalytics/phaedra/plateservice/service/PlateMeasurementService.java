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
package eu.openanalytics.phaedra.plateservice.service;

import com.google.common.primitives.Longs;
import eu.openanalytics.phaedra.measservice.dto.MeasurementDTO;
import eu.openanalytics.phaedra.measurementservice.client.MeasurementServiceClient;
import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.plateservice.repository.PlateMeasurementRepository;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.enumartion.ProjectAccessLevel;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlateMeasurementService {

	private final PlateMeasurementRepository plateMeasurementRepository;
    private final MeasurementServiceClient measurementServiceClient;
    private final ModelMapper modelMapper;
    private final PlateService plateService;
    private final ProjectAccessService projectAccessService;

    public PlateMeasurementService(PlateMeasurementRepository plateMeasurementRepository,
    		MeasurementServiceClient measurementServiceClient, ModelMapper modelMapper,
    		PlateService plateService, ProjectAccessService projectAccessService) {

        this.plateMeasurementRepository = plateMeasurementRepository;
        this.measurementServiceClient = measurementServiceClient;
        this.modelMapper = modelMapper;
        this.plateService = plateService;
        this.projectAccessService = projectAccessService;
    }

    public PlateMeasurementDTO addPlateMeasurement(PlateMeasurementDTO plateMeasurementDTO) {
    	long projectId = plateService.getProjectIdByPlateId(plateMeasurementDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

        PlateMeasurement plateMeasurement = modelMapper.map(plateMeasurementDTO);
        plateMeasurement = plateMeasurementRepository.save(plateMeasurement);

        return modelMapper.map(plateMeasurement);
    }

    public List<PlateMeasurementDTO> getPlateMeasurements(long plateId) {
    	long projectId = plateService.getProjectIdByPlateId(plateId);
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);

        List<PlateMeasurement> result = plateMeasurementRepository.findByPlateId(plateId);
        if (CollectionUtils.isNotEmpty(result)) {
            Map<Long, PlateMeasurement> plateMeasurementByMeasurementId = result.stream().collect(Collectors.toMap(PlateMeasurement::getMeasurementId, pm -> pm));
            List<MeasurementDTO> measurementDTOs = measurementServiceClient.getMeasurementsByMeasIds(Longs.toArray(plateMeasurementByMeasurementId.keySet()));
            return measurementDTOs.stream().map(mDTO -> modelMapper.map(plateMeasurementByMeasurementId.get(mDTO.getId()), mDTO)).toList();
        }
        return new ArrayList<>();
    }

    public PlateMeasurementDTO getPlateMeasurementByMeasId(long plateId, long measId) {
    	long projectId = plateService.getProjectIdByPlateId(plateId);
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);

        PlateMeasurement result = plateMeasurementRepository.findByPlateIdAndMeasurementId(plateId, measId);
        return mapToPlateMeasurementDTO(result);
    }

    public PlateMeasurementDTO setActivePlateMeasurement(PlateMeasurementDTO plateMeasurementDTO) {
    	long projectId = plateService.getProjectIdByPlateId(plateMeasurementDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);

    	PlateMeasurement plateMeasurement = plateMeasurementRepository.findByPlateIdAndMeasurementId(plateMeasurementDTO.getPlateId(), plateMeasurementDTO.getMeasurementId());
    	if (plateMeasurement != null) {
            plateMeasurement.setActive(plateMeasurementDTO.getActive());
            plateMeasurementRepository.save(plateMeasurement);

            List<PlateMeasurement> plateMeasurements = plateMeasurementRepository.findByPlateId(plateMeasurementDTO.getPlateId());
            for (PlateMeasurement pm : plateMeasurements) {
                if (!pm.getMeasurementId().equals(plateMeasurementDTO.getMeasurementId())) {
                    pm.setActive(false);
                    plateMeasurementRepository.save(pm);
                }
            }
            return plateMeasurementDTO;
        } else {
    	    //TODO: Throw an exception because this state should not be possible
    	    return null;
        }
    }

    public PlateMeasurementDTO getActivePlateMeasurement(long plateId) {
        long projectId = plateService.getProjectIdByPlateId(plateId);
        projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);

        PlateMeasurement activePlateMeasurement = plateMeasurementRepository.findByPlateIdAndActive(plateId, true);
        if (activePlateMeasurement != null) {
            return mapToPlateMeasurementDTO(activePlateMeasurement);
        } else {
            return null;
        }
    }

    private PlateMeasurementDTO mapToPlateMeasurementDTO(PlateMeasurement plateMeasurement) {
        return modelMapper.map(plateMeasurement);
    }
}
