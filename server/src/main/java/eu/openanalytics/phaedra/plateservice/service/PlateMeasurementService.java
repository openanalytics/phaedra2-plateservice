package eu.openanalytics.phaedra.plateservice.service;

import com.google.common.primitives.Longs;
import eu.openanalytics.phaedra.measservice.dto.MeasurementDTO;
import eu.openanalytics.phaedra.measurementservice.client.MeasurementServiceClient;
import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import eu.openanalytics.phaedra.plateservice.repository.PlateMeasurementRepository;
import eu.openanalytics.phaedra.platservice.dto.PlateMeasurementDTO;
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

    public PlateMeasurementService(PlateMeasurementRepository plateMeasurementRepository, MeasurementServiceClient measurementServiceClient, ModelMapper modelMapper) {
        this.plateMeasurementRepository = plateMeasurementRepository;
        this.measurementServiceClient = measurementServiceClient;
        this.modelMapper = modelMapper;
    }

    public PlateMeasurementDTO addPlateMeasurement(PlateMeasurementDTO plateMeasurementDTO) {
        PlateMeasurement plateMeasurement = modelMapper.map(plateMeasurementDTO);
        plateMeasurement = plateMeasurementRepository.save(plateMeasurement);

        return modelMapper.map(plateMeasurement);
    }

    public List<PlateMeasurementDTO> getPlateMeasurements(long plateId) {
        List<PlateMeasurement> result = plateMeasurementRepository.findByPlateId(plateId);
        Map<Long, PlateMeasurement> plateMeasurementByMeasurementId = result.stream().collect(Collectors.toMap(PlateMeasurement::getMeasurementId, pm -> pm));

        List<MeasurementDTO> measurementDTOs = measurementServiceClient.getMeasurements(Longs.toArray(plateMeasurementByMeasurementId.keySet()));
        if (CollectionUtils.isNotEmpty(measurementDTOs)) {
            return measurementDTOs.stream().map(mDTO -> modelMapper.map(plateMeasurementByMeasurementId.get(mDTO.getId()), mDTO)).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public PlateMeasurementDTO getPlateMeasurementByMeasId(long plateId, long measId) {
        PlateMeasurement result = plateMeasurementRepository.findByPlateIdAndMeasurementId(plateId, measId);
        return mapToPlateMeasurementDTO(result);
    }

    public PlateMeasurementDTO setActivePlateMeasurement(long plateId, long measId) {
        plateMeasurementRepository.findByPlateId(plateId).stream()
                .filter(pm -> pm.getPlateId().equals(plateId) && pm.getActive() == true)
                .forEach(pm -> {
                    pm.setActive(false);
                    plateMeasurementRepository.save(pm);
                });

        PlateMeasurement plateMeasurement = plateMeasurementRepository.findByPlateIdAndMeasurementId(plateId, measId);
        plateMeasurement.setActive(true);

        return modelMapper.map(plateMeasurementRepository.save(plateMeasurement));
    }

    private PlateMeasurementDTO mapToPlateMeasurementDTO(PlateMeasurement plateMeasurement) {
        return modelMapper.map(plateMeasurement);
    }
}
