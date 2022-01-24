package eu.openanalytics.phaedra.plateservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class WellTemplateService {
	
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Comparator<WellTemplateDTO> WELL_TEMPLATE_DTO_COMPARATOR = Comparator.comparing(WellTemplateDTO::getRow).thenComparing(WellTemplateDTO::getColumn);

    private final WellTemplateRepository wellTemplateRepository;
    private final PlateTemplateService plateTemplateService;
	private final IAuthorizationService authService;
	
    public WellTemplateService(WellTemplateRepository wellTemplateRepository, PlateTemplateService plateTemplateService, IAuthorizationService authService) {
    	this.wellTemplateRepository = wellTemplateRepository;
    	this.plateTemplateService = plateTemplateService;
    	this.authService = authService;
    }

    public WellTemplateDTO createWellTemplate(WellTemplateDTO wellTemplateDTO) {
    	Optional.ofNullable(plateTemplateService.getPlateTemplateById(wellTemplateDTO.getPlateTemplateId()))
    			.map(PlateTemplateDTO::getCreatedBy)
    			.ifPresent(creator -> authService.performOwnershipCheck(creator));
    	
        WellTemplate wellTemplate = new WellTemplate(wellTemplateDTO.getPlateTemplateId());
        modelMapper.typeMap(WellTemplateDTO.class, WellTemplate.class)
                .map(wellTemplateDTO, wellTemplate);
        wellTemplate = wellTemplateRepository.save(wellTemplate);
        return mapToWellTemplateDTO(wellTemplate);
    }

    public List<WellTemplateDTO> createWellTemplates(PlateTemplate plateTemplate) {
    	authService.performOwnershipCheck(plateTemplate.getCreatedBy());
    	
        List<WellTemplate> wellTemplates = new ArrayList<>(plateTemplate.getRows()*plateTemplate.getColumns());
        for (int r = 1; r <= plateTemplate.getRows(); r++) {
            for (int c = 1; c <= plateTemplate.getColumns(); c++) {
                WellTemplate wellTemplate = new WellTemplate(plateTemplate.getId());
                wellTemplate.setRow(r);
                wellTemplate.setColumn(c);
                wellTemplates.add(wellTemplate);
            }
        }
        wellTemplateRepository.saveAll(wellTemplates);
        return wellTemplates.stream().map(this::mapToWellTemplateDTO).collect(Collectors.toList());
    }

    public void updateWellTemplate(WellTemplateDTO wellTemplateDTO) {
    	Optional.ofNullable(plateTemplateService.getPlateTemplateById(wellTemplateDTO.getPlateTemplateId()))
			.map(PlateTemplateDTO::getCreatedBy)
			.ifPresent(creator -> authService.performOwnershipCheck(creator));
    	
        Optional<WellTemplate> wellTemplate = wellTemplateRepository.findById(wellTemplateDTO.getId());
        wellTemplate.ifPresent( w -> {
            modelMapper.typeMap(WellTemplateDTO.class,WellTemplate.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(wellTemplateDTO, w);
            wellTemplateRepository.save(w);
        });
    }

    public WellTemplateDTO getWellTemplateById(long wellTemplateId) {
    	authService.performAccessCheck(p -> authService.hasUserAccess());
    	
        Optional<WellTemplate> result = wellTemplateRepository.findById(wellTemplateId);
        return result.map(this::mapToWellTemplateDTO).orElse(null);
    }

    public List<WellTemplateDTO> getWellTemplatesByPlateTemplateId(long plateTemplateId) {
    	authService.performAccessCheck(p -> authService.hasUserAccess());
    	
        List<WellTemplate> result = wellTemplateRepository.findByPlateTemplateId(plateTemplateId);
        return result.stream().map(this::mapToWellTemplateDTO).sorted(WELL_TEMPLATE_DTO_COMPARATOR).toList();
    }


    private WellTemplateDTO mapToWellTemplateDTO(WellTemplate wellTemplate) {
        WellTemplateDTO wellTemplateDTO = new WellTemplateDTO();
        modelMapper.typeMap(WellTemplate.class, WellTemplateDTO.class)
                .map(wellTemplate, wellTemplateDTO);
        return wellTemplateDTO;
    }

}
