package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;

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

    private final WellRepository wellRepository;
    private final PlateService plateService;
    private final ProjectAccessService projectAccessService;
    
    public WellService(WellRepository wellRepository, PlateService plateService, ProjectAccessService projectAccessService) {
        this.wellRepository = wellRepository;
        this.plateService = plateService;
        this.projectAccessService = projectAccessService;
    }

    public WellDTO createWell(WellDTO wellDTO) {
    	long projectId = plateService.getProjectIdByPlateId(wellDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
    	
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .map(wellDTO, well);
        well = wellRepository.save(well);
        return mapTpWellDTO(well);
    }

    public List<WellDTO> createWells(Plate plate) {
    	long projectId = plateService.getProjectIdByPlateId(plate.getId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
    	
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
    	long projectId = plateService.getProjectIdByPlateId(wellDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
    	
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        well = wellRepository.save(well);
        return mapTpWellDTO(well);
    }

    public List<WellDTO> getWellsByPlateId(long plateId) {
    	long projectId = plateService.getProjectIdByPlateId(plateId);
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);
    	
        List<Well> result = wellRepository.findByPlateId(plateId);
        return result.stream().map(this::mapTpWellDTO).sorted(WELL_COMPARATOR).toList();
    }

    private WellDTO mapTpWellDTO(Well well) {
        WellDTO wellDTO = new WellDTO();
        modelMapper.typeMap(Well.class, WellDTO.class)
                .map(well, wellDTO);
        return wellDTO;
    }
}
