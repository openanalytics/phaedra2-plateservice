package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.WellSubstance;
import eu.openanalytics.phaedra.plateservice.repository.WellSubstanceRepository;
import eu.openanalytics.phaedra.plateservice.dto.WellSubstanceDTO;

@Service
public class WellSubstanceService {
    private final org.modelmapper.ModelMapper modelMapper = new ModelMapper();

    private WellSubstanceRepository wellSubstanceRepository;

    public WellSubstanceService(WellSubstanceRepository wellSubstanceRepository) {
        this.wellSubstanceRepository = wellSubstanceRepository;
    }

    public WellSubstanceDTO getWellSubstanceByWellId(long wellId) {
        WellSubstance result = wellSubstanceRepository.findByWellId(wellId);
        if (result == null) return null;
        return mapToWellSubstanceDTO(result);
    }

    public List<WellSubstanceDTO> getWellSubstancesByPlateId(long plateId) {
    	return wellSubstanceRepository.findByPlateId(plateId)
    			.stream()
    			.map(this::mapToWellSubstanceDTO)
    			.toList();
    }

    public void updateWellSubstance(WellSubstanceDTO wellSubstanceDTO) {
        Optional<WellSubstance> wellSubstance = wellSubstanceRepository.findById(wellSubstanceDTO.getId());
        wellSubstance.ifPresent(e -> {
            modelMapper.typeMap(WellSubstanceDTO.class, WellSubstance.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(wellSubstanceDTO, e);
            wellSubstanceRepository.save(e);
        });
    }

    public WellSubstanceDTO createWellSubstance(WellSubstanceDTO wellSubstanceDTO) {
        WellSubstance wellSubstance = new WellSubstance();
        modelMapper.typeMap(WellSubstanceDTO.class, WellSubstance.class)
                .map(wellSubstanceDTO, wellSubstance);
        wellSubstance = wellSubstanceRepository.save(wellSubstance);

        return mapToWellSubstanceDTO(wellSubstance);
    }

    public void deleteWellSubstance(long wellSubstanceId) {
        wellSubstanceRepository.deleteById(wellSubstanceId);
    }

    private WellSubstanceDTO mapToWellSubstanceDTO(WellSubstance wellSubstance) {
        WellSubstanceDTO wellSubstanceDTO = new WellSubstanceDTO();
        modelMapper.typeMap(WellSubstance.class, WellSubstanceDTO.class)
                .map(wellSubstance, wellSubstanceDTO);
        return wellSubstanceDTO;
    }

}
