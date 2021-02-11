package eu.openanalytics.phaedra.plateservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.repository.PlateRepository;
import eu.openanalytics.phaedra.plateservice.repository.WellRepository;

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
		return plateRepo.findByExperimentId(experimentId);
	}
	
	public List<Well> getWellsByPlateId(long plateId) {
		return wellRepo.findByPlateId(plateId);
	}
	
	public void updatePlate(Plate plate) {
		plateRepo.save(plate);
	}
	
	public void updateWells(long plateId, List<Well> wells) {
		if (wells == null || wells.isEmpty()) throw new IllegalArgumentException("Cannot update wells: no well information specified");
		List<Well> plateWells = getWellsByPlateId(plateId);
		for (Well well: wells) {
			well.setPlateId(plateId);
			Well plateWell = findWell(plateWells, well.getRow(), well.getColumn());
			well.setId(plateWell.getId());
		}
		wellRepo.saveAll(wells);
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
	
	private Well findWell(List<Well> wells, int row, int column) {
		return wells.stream().filter(w -> w.getRow() == row && w.getColumn() == column).findAny().orElse(null);
	}
}
