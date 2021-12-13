package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.repository.PlateTemplateRepository;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlateTemplateService {
    private static final ModelMapper modelMapper = new ModelMapper();

    private final PlateTemplateRepository plateTemplateRepository;

    private final WellTemplateService wellTemplateService;

    public PlateTemplateService(PlateTemplateRepository plateTemplateRepository, WellTemplateService wellTemplateService) {
        this.plateTemplateRepository = plateTemplateRepository;
        this.wellTemplateService = wellTemplateService;
    }

    public PlateTemplateDTO createPlateTemplate(PlateTemplateDTO plateTemplateDTO) {
        PlateTemplate plateTemplate = new PlateTemplate();
        modelMapper.typeMap(PlateTemplateDTO.class, PlateTemplate.class)
                .map(plateTemplateDTO, plateTemplate);
        plateTemplate = plateTemplateRepository.save(plateTemplate);

        //Add wellTemplates
        wellTemplateService.createWellTemplates(plateTemplate);

        return mapToPlateTemplateDTO(plateTemplate);
    }

    public void updatePlateTemplate(PlateTemplateDTO plateTemplateDTO) {
        Optional<PlateTemplate> plateTemplate = plateTemplateRepository.findById(plateTemplateDTO.getId());
        plateTemplate.ifPresent(p -> {
            modelMapper.typeMap(PlateTemplateDTO.class, PlateTemplate.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(plateTemplateDTO, p);
            plateTemplateRepository.save(p);
        });
    }

    public void deletePlateTemplate(long plateTemplateId) {
        plateTemplateRepository.deleteById(plateTemplateId);
    }

    public List<PlateTemplateDTO> getAllPlateTemplates() {
        List<PlateTemplate> plateTemplates = (List<PlateTemplate>) plateTemplateRepository.findAll();
        return plateTemplates.stream()
                .map(this::mapToPlateTemplateDTO)
                .collect(Collectors.toList());
    }

    public PlateTemplateDTO getPlateTemplateById(long plateTemplateId) {
        Optional<PlateTemplate> result = plateTemplateRepository.findById(plateTemplateId);
        return result.map(this::mapToPlateTemplateDTO).orElse(null);
    }

    private PlateTemplateDTO mapToPlateTemplateDTO(PlateTemplate plateTemplate) {
        PlateTemplateDTO plateTemplateDTO = new PlateTemplateDTO();
        modelMapper.typeMap(PlateTemplate.class, PlateTemplateDTO.class).map(plateTemplate, plateTemplateDTO);
        return plateTemplateDTO;
    }
}
