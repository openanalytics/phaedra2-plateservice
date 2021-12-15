package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.repository.PlateTemplateRepository;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlateTemplateService {
    private final ModelMapper modelMapper = new ModelMapper();

    private final PlateTemplateRepository plateTemplateRepository;

    private final WellTemplateService wellTemplateService;

    public PlateTemplateService(PlateTemplateRepository plateTemplateRepository, WellTemplateService wellTemplateService) {
        this.plateTemplateRepository = plateTemplateRepository;
        this.wellTemplateService = wellTemplateService;

        // TODO move to dedicated ModelMapper service
        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());
        modelMapper.createTypeMap(PlateTemplate.class, PlateTemplateDTO.PlateTemplateDTOBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());
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
        var builder = modelMapper.map(plateTemplate, PlateTemplateDTO.PlateTemplateDTOBuilder.class);
        builder.wells(wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplate.getId()));
        return builder.build();
    }
}
