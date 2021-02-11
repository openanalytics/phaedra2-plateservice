package eu.openanalytics.phaedra.plateservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;
import eu.openanalytics.phaedra.plateservice.util.PlateUtils;
import eu.openanalytics.phaedra.plateservice.util.WellUtils;
import eu.openanalytics.phaedra.util.ObjectCopyUtils;

@Service
public class PlateService {

	public static final String DEFAULT_WELLTYPE_CODE = "EMPTY";
	public static final int DEFAULT_WELL_STATUS = 0;
	
	@Autowired
	private PlateRepository plateRepo;
	@Autowired
	private WellRepository wellRepo;
	
	public Plate createPlate(Plate plate) {
		Plate newPlate = plateRepo.save(plate);
		createWells(newPlate);
		return newPlate;
	}
	
	private List<Well> createWells(Plate plate) {
		List<Well> wells = new ArrayList<>(plate.getRows() * plate.getColumns());
		for (int r = 1; r <= plate.getRows(); r++) {
			for (int c = 1; c <= plate.getColumns(); c++) {
				Well well = new Well();
				well.setPlateId(plate.getId());
				well.setRow(r);
				well.setColumn(c);
				well.setWellType(DEFAULT_WELLTYPE_CODE);
				well.setStatus(DEFAULT_WELL_STATUS);
				wells.add(well);
			}
		}
		wellRepo.saveAll(wells);
		return wells;
	}

	public boolean plateExists(long plateId) {
		return plateRepo.existsById(plateId);
	}
	
	public Optional<Plate> getPlateById(long plateId) {
		return plateRepo.findById(plateId);
	}
	
	public List<Plate> getPlatesByExperimentId(long experimentId) {
		return plateRepo.findByExperimentId(experimentId).stream().sorted(PlateUtils.PLATE_SEQUENCE_SORTER).collect(Collectors.toList());
	}
	
	public List<Well> getWellsByPlateId(long plateId) {
		return wellRepo.findByPlateId(plateId).stream().sorted(WellUtils.WELL_POSITION_SORTER).collect(Collectors.toList());
	}
	
	public void updatePlate(Plate updatedPlate) {
		Plate plate = getPlateById(updatedPlate.getId()).get();
		//TODO Make sure dimensions are not changed.
		ObjectCopyUtils.copyNonNullValues(updatedPlate, plate);
		plateRepo.save(plate);
	}
	
	public void updateWells(long plateId, List<Well> updatedWells) {
		if (updatedWells == null || updatedWells.isEmpty()) throw new IllegalArgumentException("Cannot update wells: no well information specified");
		List<Well> wells = getWellsByPlateId(plateId);
		List<Well> wellsToSave = new ArrayList<>();
		for (Well updatedWell: updatedWells) {
			Well well = WellUtils.findWell(wells, updatedWell.getRow(), updatedWell.getColumn()).orElse(null);
			if (well == null) throw new IllegalArgumentException(String.format("Invalid well coordinates: %d, %d", updatedWell.getRow(), updatedWell.getColumn()));
			ObjectCopyUtils.copyNonNullValues(updatedWell, well);
			wellsToSave.add(well);
		}
		wellRepo.saveAll(wellsToSave);
	}
	
	public void deletePlate(long plateId) {
		wellRepo.deleteByPlateId(plateId);
		plateRepo.deleteById(plateId);
	}
	
	public void deletePlatesByExperimentId(long experimentId) {
		wellRepo.deleteByExperimentId(experimentId);
		plateRepo.deleteByExperimentId(experimentId);
	}
	
	public void deletePlatesByProjectId(long projectId) {
		wellRepo.deleteByProjectId(projectId);
		plateRepo.deleteByProjectId(projectId);
	}

}
