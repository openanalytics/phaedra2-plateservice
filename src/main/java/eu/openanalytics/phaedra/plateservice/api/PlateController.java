package eu.openanalytics.phaedra.plateservice.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.plateservice.dao.PlateDAO;
import eu.openanalytics.phaedra.plateservice.model.Plate;

@RestController
public class PlateController {

	@Autowired
	private PlateDAO plateDAO;
	
	@RequestMapping(value="/plate/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Plate> getPlate(@PathVariable long id) {
		Optional<Plate> plate = plateDAO.getPlate(id);
		return ResponseEntity.of(plate);
	}

	@RequestMapping(value="/plate/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Plate> updatePlate(@PathVariable long id, @RequestBody Plate plate) {
		plateDAO.updatePlate(id, plate);
		return ResponseEntity.ok(plate);
	}
	
	@RequestMapping(value="/plate/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletePlate(@PathVariable long id) {
		plateDAO.deletePlate(id);
		return ResponseEntity.noContent().build();
	}
	
}