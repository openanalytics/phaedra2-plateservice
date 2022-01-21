package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.PlateTemplate;
import eu.openanalytics.phaedra.plateservice.model.WellSubstance;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.platservice.dto.*;
import eu.openanalytics.phaedra.platservice.enumartion.LinkStatus;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlateService {
	private final ModelMapper modelMapper = new ModelMapper();

	private final PlateRepository plateRepository;
	private final WellService wellService;
	private final PlateTemplateService plateTemplateService;
	private final WellTemplateService wellTemplateService;
	private final WellSubstanceService wellSubstanceService;

	public PlateService(PlateRepository plateRepository, WellService wellService, PlateTemplateService plateTemplateService, WellTemplateService wellTemplateService, WellSubstanceService wellSubstanceService) {
		this.plateRepository = plateRepository;
		this.wellService = wellService;
		this.plateTemplateService = plateTemplateService;
		this.wellTemplateService = wellTemplateService;
		this.wellSubstanceService = wellSubstanceService;

		// TODO move to dedicated ModelMapper service
		Configuration builderConfiguration = modelMapper.getConfiguration().copy()
				.setDestinationNameTransformer(NameTransformers.builder())
				.setDestinationNamingConvention(NamingConventions.builder());
		modelMapper.createTypeMap(Plate.class, PlateDTO.PlateDTOBuilder.class, builderConfiguration)
				.setPropertyCondition(Conditions.isNotNull());
	}

	public PlateDTO createPlate(PlateDTO plateDTO) {
		Plate plate = new Plate();
		modelMapper.typeMap(PlateDTO.class, Plate.class)
				.map(plateDTO, plate);
		plate = plateRepository.save(plate);

		// Automatically create the corresponding wells
		wellService.createWells(plate);

		return mapToPlateDTO(plate);
	}

	public PlateDTO updatePlate(PlateDTO plateDTO) {
		Optional<Plate> plate = plateRepository.findById(plateDTO.getId());
		plate.ifPresent(p -> {
			modelMapper.typeMap(PlateDTO.class, Plate.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(plateDTO, p);
			plateRepository.save(p);
		});
		return plateDTO;
	}

	public void deletePlate(long plateId) {
		plateRepository.deleteById(plateId);
	}

	public List<PlateDTO> getAllPlates() {
		List<Plate> result = (List<Plate>) plateRepository.findAll();
		return result.stream().map(this::mapToPlateDTO).collect(Collectors.toList());
	}

	public List<PlateDTO> getPlatesByExperimentId(long experimentId) {
		List<Plate> result = plateRepository.findByExperimentId(experimentId);
		return result.stream().map(this::mapToPlateDTO).collect(Collectors.toList());
	}

	public List<PlateDTO> getPlatesByBarcode(String barcode) {
		List<Plate> result = plateRepository.findByBarcode(barcode);
		return result.stream().map(this::mapToPlateDTO).collect(Collectors.toList());
	}

	public PlateDTO getPlateById(long plateId) {
		Optional<Plate> result = plateRepository.findById(plateId);
		return result.map(this::mapToPlateDTO).orElse(null);
	}

	public PlateDTO linkPlate(long plateId, long plateTemplateId){
		PlateDTO plateDTO = getPlateById(plateId);
		//get plateTemplate and plate
		PlateTemplateDTO plateTemplateDTO = this.plateTemplateService.getPlateTemplateById(plateTemplateId);
		//Check if they exist
		if (plateDTO==null || plateTemplateDTO==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plate or template can not be found for these ids.");
		//Check for same dimensions
		if (plateDTO.getRows()!=plateTemplateDTO.getRows()||plateDTO.getColumns()!=plateTemplateDTO.getColumns())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plate or template have different dimensions.");

		//Change wells
		this.linkWithPlateTemplate(plateDTO,plateTemplateDTO);

		//Set Link properties of plate
		plateDTO.setLinkTemplateId(plateTemplateDTO.getId().toString());
		plateDTO.setLinkSource("layout-template");
		plateDTO.setLinkStatus(LinkStatus.LINKED);
		plateDTO.setLinkedOn(new Date());
		//Save in DB
		this.updatePlate(plateDTO);
		return plateDTO;
	}

	private void linkWithPlateTemplate(PlateDTO plateDTO, PlateTemplateDTO plateTemplateDTO){
		//Get wells
		List<WellDTO> wells = wellService.getWellsByPlateId(plateDTO.getId());
		List<WellTemplateDTO> wellTemplates  = wellTemplateService.getWellTemplatesByPlateTemplateId(plateTemplateDTO.getId());

		for (int i = 0; i < wells.size(); i++){
			wells.get(i).setWellType(wellTemplates.get(i).getWellType());
			var previousWellSubstance = wellSubstanceService.getWellSubstanceByWellId(wells.get(i).getId());
			//Do we need to create/update substance?
			if (wellTemplates.get(i).getSubstanceType()!=null){
				if (previousWellSubstance==null){
					createNewWellSubstance(wells.get(i),wellTemplates.get(i));
				} else {
					updateExistingWellSubstance(previousWellSubstance, wellTemplates.get(i));
				}
			}
			//If no new substance, delete substance
			else if (previousWellSubstance!=null){
				wellSubstanceService.deleteWellSubstance(previousWellSubstance.getId());
			}
		}
		wellService.updateWells(wells);
	}

	private PlateDTO mapToPlateDTO(Plate plate) {
		var builder = modelMapper.map(plate, PlateDTO.PlateDTOBuilder.class);
		builder.wells(wellService.getWellsByPlateId(plate.getId()));
		return builder.build();
	}

	private void createNewWellSubstance(WellDTO wellDTO, WellTemplateDTO wellTemplateDTO) {
		WellSubstanceDTO wellSubstanceDTO = new WellSubstanceDTO();
		wellSubstanceDTO.setWellId(wellDTO.getId());
		wellSubstanceDTO.setType(wellTemplateDTO.getSubstanceType());
		wellSubstanceDTO.setName(wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setConcentration(wellTemplateDTO.getConcentration());
		wellSubstanceService.createWellSubstance(wellSubstanceDTO);
	}

	private void updateExistingWellSubstance(WellSubstanceDTO wellSubstanceDTO, WellTemplateDTO wellTemplateDTO) {
		wellSubstanceDTO.setType(wellTemplateDTO.getSubstanceType());
		wellSubstanceDTO.setName(wellTemplateDTO.getSubstanceName());
		wellSubstanceDTO.setConcentration(wellTemplateDTO.getConcentration());
		wellSubstanceService.updateWellSubstance(wellSubstanceDTO);
	}
}
