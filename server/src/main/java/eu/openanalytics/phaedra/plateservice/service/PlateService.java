package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlateService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final PlateRepository plateRepository;
	private final WellService wellService;

	public PlateService(PlateRepository plateRepository, WellService wellService) {
		this.plateRepository = plateRepository;
		this.wellService = wellService;
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
	
//	private List<Well> createWells(Plate plate) {
//		List<Well> wells = new ArrayList<>(plate.getRows() * plate.getColumns());
//		for (int r = 1; r <= plate.getRows(); r++) {
//			for (int c = 1; c <= plate.getColumns(); c++) {
//				Well well = new Well();
//				well.setPlateId(plate.getId());
//				well.setRow(r);
//				well.setColumn(c);
//				well.setWellType(DEFAULT_WELLTYPE_CODE);
//				well.setStatus(DEFAULT_WELL_STATUS);
//				wells.add(well);
//			}
//		}
//		wellRepository.saveAll(wells);
//		return wells;
//	}

//	public boolean plateExists(long plateId) {
//		return plateRepository.existsById(plateId);
//	}
	
//	public List<PlateDTO> getPlatesByExperimentId(long experimentId) {
//		return plateRepository.findByExperimentId(experimentId).stream().sorted(PlateUtils.PLATE_SEQUENCE_SORTER).collect(Collectors.toList());
//	}
	
//	public List<Well> getWellsByPlateId(long plateId) {
//		return wellRepository.findByPlateId(plateId).stream().sorted(WellUtils.WELL_POSITION_SORTER).collect(Collectors.toList());
//	}
	
//	public void updateWells(long plateId, List<Well> updatedWells) {
//		if (updatedWells == null || updatedWells.isEmpty()) throw new IllegalArgumentException("Cannot update wells: no well information specified");
//		List<Well> wells = getWellsByPlateId(plateId);
//		List<Well> wellsToSave = new ArrayList<>();
//		for (Well updatedWell: updatedWells) {
//			Well well = WellUtils.findWell(wells, updatedWell.getRow(), updatedWell.getColumn()).orElse(null);
//			if (well == null) throw new IllegalArgumentException(String.format("Invalid well coordinates: %d, %d", updatedWell.getRow(), updatedWell.getColumn()));
//			ObjectCopyUtils.copyNonNullValues(updatedWell, well);
//			wellsToSave.add(well);
//		}
//		wellRepository.saveAll(wellsToSave);
//	}
	
//	public void deletePlatesByExperimentId(long experimentId) {
//		wellRepository.deleteByExperimentId(experimentId);
//		plateRepository.deleteByExperimentId(experimentId);
//	}
	
//	public void deletePlatesByProjectId(long projectId) {
//		wellRepository.deleteByProjectId(projectId);
//		plateRepository.deleteByProjectId(projectId);
//	}

	private PlateDTO mapToPlateDTO(Plate plate) {
		PlateDTO plateDTO = new PlateDTO();
		modelMapper.typeMap(Plate.class, PlateDTO.class)
				.map(plate, plateDTO);
		return plateDTO;
	}
}
