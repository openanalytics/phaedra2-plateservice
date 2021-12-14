package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import eu.openanalytics.phaedra.plateservice.repository.WellTemplateRepository;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellTemplateService {
    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Comparator<WellTemplateDTO> WELL_TEMPLATE_DTO_COMPARATOR = Comparator.comparing(WellTemplateDTO::getRow).thenComparing(WellTemplateDTO::getColumn);

    private WellTemplateRepository wellTemplateRepository;

    public WellTemplateService(WellTemplateRepository wellTemplateRepository){ this.wellTemplateRepository = wellTemplateRepository;}

    public WellTemplateDTO createWellTemplate(WellTemplateDTO wellTemplateDTO){
        WellTemplate wellTemplate = new WellTemplate(wellTemplateDTO.getPlateTemplateId());
        modelMapper.typeMap(WellTemplateDTO.class, WellTemplate.class)
                .map(wellTemplateDTO, wellTemplate);
        wellTemplate = wellTemplateRepository.save(wellTemplate);
        return mapToWellTemplateDTO(wellTemplate);
    }

    public List<WellTemplateDTO> createWellTemplates(PlateTemplate plateTemplate){
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

    public void updateWellTemplate(WellTemplateDTO wellTemplateDTO){
        Optional<WellTemplate> wellTemplate = wellTemplateRepository.findById(wellTemplateDTO.getId());
        wellTemplate.ifPresent( w -> {
            modelMapper.typeMap(WellTemplateDTO.class,WellTemplate.class)
                    .setPropertyCondition(Conditions.isNotNull())
                    .map(wellTemplateDTO, w);
            wellTemplateRepository.save(w);
        });
    }

    public List<WellTemplateDTO> getWellTemplatesByPlateTemplateId(long plateTemplateId){
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
