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

import eu.openanalytics.phaedra.measservice.dto.MeasurementDTO;
import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class ModelMapper {

    private final org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    public ModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        modelMapper.createTypeMap(PlateMeasurementDTO.class, PlateMeasurement.class);

        modelMapper.createTypeMap(PlateMeasurement.class, PlateMeasurementDTO.class);


//        modelMapper.validate();
    }

    public PlateMeasurementDTO map(PlateMeasurement plateMeasurement, MeasurementDTO measurementDTO) {
        PlateMeasurementDTO plateMeasurementDTO = new PlateMeasurementDTO();

        if (plateMeasurement != null) {
            plateMeasurementDTO.setPlateId(plateMeasurement.getPlateId());
            plateMeasurementDTO.setActive(plateMeasurement.getActive());
            plateMeasurementDTO.setLinkedBy(plateMeasurement.getLinkedBy());
            plateMeasurementDTO.setLinkedOn(plateMeasurement.getLinkedOn());
        }

        if (measurementDTO != null) {
            plateMeasurementDTO.setMeasurementId(measurementDTO.getId());
            plateMeasurementDTO.setName(measurementDTO.getName());
            plateMeasurementDTO.setBarcode(measurementDTO.getBarcode());
            plateMeasurementDTO.setDescription(measurementDTO.getDescription());
            plateMeasurementDTO.setRows(measurementDTO.getRows());
            plateMeasurementDTO.setColumns(measurementDTO.getColumns());
            plateMeasurementDTO.setCreatedOn(measurementDTO.getCreatedOn());
            plateMeasurementDTO.setCreatedBy(measurementDTO.getCreatedBy());
            plateMeasurementDTO.setWellColumns(measurementDTO.getWellColumns());
            plateMeasurementDTO.setSubWellColumns(measurementDTO.getSubWellColumns());
            plateMeasurementDTO.setImageChannels(measurementDTO.getImageChannels());
        }

        return plateMeasurementDTO;
    }

    public PlateMeasurementDTO map(PlateMeasurement plateMeasurement) {
        return modelMapper.map(plateMeasurement, PlateMeasurementDTO.class);
    }

    public PlateMeasurement map(PlateMeasurementDTO plateMeasurementDTO) {
        return modelMapper.map(plateMeasurementDTO, PlateMeasurement.class);
    }
}
