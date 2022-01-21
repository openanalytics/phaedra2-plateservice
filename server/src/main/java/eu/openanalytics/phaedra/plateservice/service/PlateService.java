package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;

@Service
public class PlateService {
	private final ModelMapper modelMapper = new ModelMapper();

	private final PlateRepository plateRepository;
	private final WellService wellService;
	private final ExperimentService experimentService;
	private final ProjectAccessService projectAccessService;
	
	public PlateService(PlateRepository plateRepository, @Lazy WellService wellService, ExperimentService experimentService, ProjectAccessService projectAccessService) {
		this.plateRepository = plateRepository;
		this.wellService = wellService;
		this.experimentService = experimentService;
		this.projectAccessService = projectAccessService;

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
			projectAccessService.checkAccessLevel(getProjectIdByPlateId(p.getId()), ProjectAccessLevel.Write);
			modelMapper.typeMap(PlateDTO.class, Plate.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(plateDTO, p);
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

	private PlateDTO mapToPlateDTO(Plate plate) {
		var builder = modelMapper.map(plate, PlateDTO.PlateDTOBuilder.class);
		builder.wells(wellService.getWellsByPlateId(plate.getId()));
		return builder.build();
	}
}
