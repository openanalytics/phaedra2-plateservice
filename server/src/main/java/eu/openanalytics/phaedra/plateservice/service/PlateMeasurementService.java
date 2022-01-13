package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.plateservice.repository.PlateMeasurementRepository;
import eu.openanalytics.phaedra.platservice.dto.PlateMeasurementDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlateMeasurementService {
    private static final ModelMapper modelMapper = new ModelMapper();

    private final PlateMeasurementRepository plateMeasurementRepository;

    public PlateMeasurementService(PlateMeasurementRepository plateMeasurementRepository) {
        this.plateMeasurementRepository = plateMeasurementRepository;
    }

    public PlateMeasurementDTO addPlateMeasurement(PlateMeasurementDTO plateMeasurementDTO) {
        PlateMeasurement plateMeasurement = new PlateMeasurement();
        modelMapper.typeMap(PlateMeasurementDTO.class, PlateMeasurement.class)
                .map(plateMeasurementDTO, plateMeasurement);

        plateMeasurement = plateMeasurementRepository.save(plateMeasurement);

        modelMapper.typeMap(PlateMeasurement.class, PlateMeasurementDTO.class)
                .map(plateMeasurement, plateMeasurementDTO);
        return plateMeasurementDTO;
    }

    public List<PlateMeasurementDTO> getPlateMeasurements(long plateId) {
        List<PlateMeasurement> result = plateMeasurementRepository.findByPlateId(plateId);
        return result.stream().map(this::mapToPlateMeasurementDTO).collect(Collectors.toList());
    }

    public PlateMeasurementDTO getPlateMeasurementByMeasId(long plateId, long measId) {
        PlateMeasurement result = plateMeasurementRepository.findByPlateIdAndMeasurementId(plateId, measId);
        return mapToPlateMeasurementDTO(result);
    }

    public PlateMeasurementDTO setActivePlateMeasurement(long plateId, long measId) {
        PlateMeasurementDTO plateMeasurementDTO = new PlateMeasurementDTO();
        List<PlateMeasurement> plateMeasurements = plateMeasurementRepository.findByPlateId(plateId);
        plateMeasurements.stream()
                .filter(pm -> pm.getPlateId().equals(plateId) && pm.getActive() == true)
                .forEach(pm -> {
                    pm.setActive(false);
                    plateMeasurementRepository.save(pm);
                });

        plateMeasurements.stream()
                .filter(pm -> pm.getPlateId().equals(plateId) && pm.getMeasurementId().equals(measId))
                .findFirst()
                .ifPresent(pm -> {
                    pm.setActive(true);
                    plateMeasurementRepository.save(pm);
                    modelMapper.typeMap(PlateMeasurement.class, PlateMeasurementDTO.class)
                            .map(pm, plateMeasurementDTO);
                });
        return plateMeasurementDTO;
    }

    private PlateMeasurementDTO mapToPlateMeasurementDTO(PlateMeasurement plateMeasurement) {
        return modelMapper.map(plateMeasurement, PlateMeasurementDTO.class);
    }
}
