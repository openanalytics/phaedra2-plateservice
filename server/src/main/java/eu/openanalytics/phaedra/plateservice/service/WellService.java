package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WellService {
    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow).thenComparing(WellDTO::getColumn);

    private WellRepository wellRepository;

    public WellService(WellRepository wellRepository) {
        this.wellRepository = wellRepository;
    }

    public WellDTO createWell(WellDTO wellDTO) {
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .map(wellDTO, well);
        well = wellRepository.save(well);
        return mapTpWellDTO(well);
    }

    public List<WellDTO> createWells(Plate plate) {
        List<Well> wells = new ArrayList<>(plate.getRows() * plate.getColumns());
        for (int r = 1; r <= plate.getRows(); r++) {
            for (int c = 1; c <= plate.getColumns(); c++) {
                Well well = new Well(plate.getId());
                well.setRow(r);
                well.setColumn(c);
                // note: use an example layout for the plate. This will be changed later.
                if (c <= 2) {
                    well.setWellType("LC");
                } else if (c >= plate.getColumns() -1) {
                    well.setWellType("HC");
                } else {
                    well.setWellType("SAMPLE");
                }
                wells.add(well);
            }
        }
        wellRepository.saveAll(wells);
        return wells.stream().map(this::mapTpWellDTO).collect(Collectors.toList());
    }

    public WellDTO updateWell(WellDTO wellDTO) {
        //TODO change to map function
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        well = wellRepository.save(well);
        return mapTpWellDTO(well);
    }

    public List<WellDTO> updateWells(List<WellDTO> wellDTOS){
        List<Well> wells = wellDTOS.stream().map(this::mapToWell).toList();
        wellRepository.saveAll(wells);
        return wellDTOS;
    }

    public List<WellDTO> getWellsByPlateId(long plateId) {
        List<Well> result = wellRepository.findByPlateId(plateId);
        return result.stream().map(this::mapTpWellDTO).sorted(WELL_COMPARATOR).toList();
    }

    private WellDTO mapTpWellDTO(Well well) {
        WellDTO wellDTO = new WellDTO();
        modelMapper.typeMap(Well.class, WellDTO.class)
                .map(well, wellDTO);
        return wellDTO;
    }

    private Well mapToWell(WellDTO wellDTO) {
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        return well;
    }
}
