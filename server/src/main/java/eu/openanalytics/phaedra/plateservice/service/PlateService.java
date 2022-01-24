package eu.openanalytics.phaedra.plateservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.platservice.dto.WellDTO;
import eu.openanalytics.phaedra.platservice.dto.WellSubstanceDTO;
import eu.openanalytics.phaedra.platservice.dto.WellTemplateDTO;
import eu.openanalytics.phaedra.platservice.enumartion.LinkStatus;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class PlateService {
	private final ModelMapper modelMapper = new ModelMapper();

	private final PlateRepository plateRepository;
	private final WellService wellService;
	private final ExperimentService experimentService;
	private final ProjectAccessService projectAccessService;
	
	@Autowired
	private IAuthorizationService authService;
	
	private final PlateTemplateService plateTemplateService;
	private final WellTemplateService wellTemplateService;
	private final WellSubstanceService wellSubstanceService;

	public PlateService(PlateRepository plateRepository, @Lazy WellService wellService, ExperimentService experimentService, ProjectAccessService projectAccessService,
			PlateTemplateService plateTemplateService, WellTemplateService wellTemplateService, WellSubstanceService wellSubstanceService) {
		
		this.plateRepository = plateRepository;
		this.wellService = wellService;
		this.experimentService = experimentService;
		this.projectAccessService = projectAccessService;
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
		long projectId = Optional.ofNullable(experimentService.getExperimentById(plateDTO.getExperimentId()))
				.map(ExperimentDTO::getProjectId)
				.orElse(0L);
		if (projectId == 0L) return null; // Experiment not found or not accessible.
		
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Write);
		
		Plate plate = new Plate();
		modelMapper.typeMap(PlateDTO.class, Plate.class).map(plateDTO, plate);
		plate.setCreatedBy(authService.getCurrentPrincipalName());
		plate.setCreatedOn(new Date());
		plate = plateRepository.save(plate);

		// Automatically create the corresponding wells
		wellService.createWells(plate);

		return mapToPlateDTO(plate);
	}

	public PlateDTO updatePlate(PlateDTO plateDTO) {
		Optional<Plate> plate = plateRepository.findById(plateDTO.getId());
		plate.ifPresent(p -> {
			projectAccessService.checkAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Write);
			modelMapper.typeMap(PlateDTO.class, Plate.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(plateDTO, p);
			p.setUpdatedBy(authService.getCurrentPrincipalName());
			p.setUpdatedOn(new Date());
			plateRepository.save(p);
		});
		return plateDTO;
	}

	public void deletePlate(long plateId) {
		projectAccessService.checkAccessLevel(getProjectIdByPlateId(plateId), ProjectAccessLevel.Write);
		plateRepository.deleteById(plateId);
	}

	public List<PlateDTO> getAllPlates() {
		List<Plate> result = (List<Plate>) plateRepository.findAll();
		return result.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(this::mapToPlateDTO)
				.toList();
	}

	public List<PlateDTO> getPlatesByExperimentId(long experimentId) {
		List<Plate> result = plateRepository.findByExperimentId(experimentId);
		return result.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(this::mapToPlateDTO)
				.toList();
	}

	public List<PlateDTO> getPlatesByBarcode(String barcode) {
		List<Plate> result = plateRepository.findByBarcode(barcode);
		return result.stream()
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(this::mapToPlateDTO)
				.toList();
	}

	public PlateDTO getPlateById(long plateId) {
		Optional<Plate> result = plateRepository.findById(plateId);
		return result
				.filter(p -> projectAccessService.hasAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Read))
				.map(this::mapToPlateDTO)
				.orElse(null);
	}
	
	@Cacheable("plate_project_id")
	public Long getProjectIdByPlateId(long plateId) {
		return plateRepository.findProjectIdByPlateId(plateId);
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
			if (wellTemplates.get(i).getSubstanceType()!=null && !wellTemplates.get(i).getSubstanceType().equals("")){
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
