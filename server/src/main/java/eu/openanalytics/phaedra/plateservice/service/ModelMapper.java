package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.measservice.dto.MeasurementDTO;
import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.platservice.dto.PlateMeasurementDTO;
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

        plateMeasurementDTO.setMeasurementId(measurementDTO.getId());

        plateMeasurementDTO.setPlateId(plateMeasurement.getPlateId());
        plateMeasurementDTO.setActive(plateMeasurement.getActive());
        plateMeasurementDTO.setLinkedBy(plateMeasurement.getLinkedBy());
        plateMeasurementDTO.setLinkedOn(plateMeasurement.getLinkedOn());

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

        return plateMeasurementDTO;
    }

    public PlateMeasurementDTO map(PlateMeasurement plateMeasurement) {
        return modelMapper.map(plateMeasurement, PlateMeasurementDTO.class);
    }

    public PlateMeasurement map(PlateMeasurementDTO plateMeasurementDTO) {
        return modelMapper.map(plateMeasurementDTO, PlateMeasurement.class);
    }
}
