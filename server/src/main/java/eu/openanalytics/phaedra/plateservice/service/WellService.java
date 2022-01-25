package eu.openanalytics.phaedra.plateservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import eu.openanalytics.phaedra.platservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;

@Service
public class WellService {
	
    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Comparator<WellDTO> WELL_COMPARATOR = Comparator.comparing(WellDTO::getRow).thenComparing(WellDTO::getColumn);

    private final WellRepository wellRepository;
    private final PlateService plateService;
    private final ProjectAccessService projectAccessService;
    private final WellSubstanceService wellSubstanceService;
    
    public WellService(WellRepository wellRepository, PlateService plateService, ProjectAccessService projectAccessService, WellSubstanceService wellSubstanceService) {
        this.wellRepository = wellRepository;
        this.plateService = plateService;
        this.projectAccessService = projectAccessService;
        this.wellSubstanceService = wellSubstanceService;
    }

    public WellDTO createWell(WellDTO wellDTO) {
    	long projectId = plateService.getProjectIdByPlateId(wellDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
    	
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.map(wellDTO, Well.class);
        well = wellRepository.save(well);
        return modelMapper.map(well, WellDTO.class);
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
                well.setWellType("EMPTY");
                wells.add(well);
            }
        }
        wellRepository.saveAll(wells);
        return wells.stream().map(well -> modelMapper.map(well, WellDTO.class)).collect(Collectors.toList());
    }

    public WellDTO updateWell(WellDTO wellDTO) {
    	long projectId = plateService.getProjectIdByPlateId(wellDTO.getPlateId());
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
    	
        //TODO change to map function
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        well = wellRepository.save(well);
        
        WellDTO updatedDTO = modelMapper.map(well, WellDTO.class);
        updatedDTO.setWellSubstance(wellSubstanceService.getWellSubstanceByWellId(well.getId()));
        
        return updatedDTO;
    }

    public List<WellDTO> updateWells(List<WellDTO> wellDTOS){
        List<Well> wells = wellDTOS.stream().map(this::mapToWell).toList();
        wellRepository.saveAll(wells);
        return wellDTOS;
    }

    public List<WellDTO> getWellsByPlateId(long plateId) {
    	long projectId = Optional.ofNullable(plateService.getProjectIdByPlateId(plateId)).orElse(0l);
    	projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Read);
    	
    	List<WellSubstanceDTO> substances = wellSubstanceService.getWellSubstancesByPlateId(plateId);
    	
        return wellRepository.findByPlateId(plateId).stream()
        		.map(well -> modelMapper.map(well, WellDTO.class))
        		.map(dto -> {
        			dto.setWellSubstance(substances.stream().filter(s -> s.getWellId() == dto.getId()).findAny().orElse(null));
        			return dto;
        		})
        		.sorted(WELL_COMPARATOR)
        		.toList();
    }

    private Well mapToWell(WellDTO wellDTO) {
        Well well = new Well(wellDTO.getPlateId());
        modelMapper.typeMap(WellDTO.class, Well.class)
                .setPropertyCondition(Conditions.isNotNull())
                .map(wellDTO, well);
        return well;
    }
}
