package eu.openanalytics.phaedra.plateservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.service.PlateService;

@RestController
public class PlateController {
	
	@Autowired
	private PlateService plateService;
	
	@RequestMapping(value="/plate/{plateId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Plate> getPlate(@PathVariable long plateId) {
		return ResponseEntity.of(plateService.getPlateById(plateId));
	}
	
	@RequestMapping(value="/plate/{plateId}/wells", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Well>> getWells(@PathVariable long plateId) {
		List<Well> wells = plateService.getWellsByPlateId(plateId);
		if (wells.isEmpty()) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(wells);
	}
	
	@RequestMapping(value="/plate/{plateId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updatePlate(@PathVariable long plateId, @RequestBody Plate plate) {
		if (!plateService.plateExists(plateId)) return ResponseEntity.notFound().build();
		plate.setId(plateId);
		plateService.updatePlate(plate);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/plate/{plateId}/wells", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateWells(@PathVariable long plateId, @RequestBody List<Well> wells) {
		if (!plateService.plateExists(plateId)) return ResponseEntity.notFound().build();
		plateService.updateWells(plateId, wells);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/plate/{plateId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletePlate(@PathVariable long plateId) {
		if (plateId <= 0) return ResponseEntity.notFound().build();
		plateService.deletePlate(plateId);
		return ResponseEntity.noContent().build();
	}
	
}