package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomPlateRepository {

  Plate findById(long id);

  List<Plate> findAll();

  List<Plate> findAllByBarcode(String barcode);

  List<Plate> findAllByIdIn(List<Long> ids);

  List<Plate> findByExperimentId(long experimentId);

  List<Plate> findAllByBarcodeAndExperimentId(String barcode, long experimentId);

  List<Plate> findAllByExperimentIdIn(List<Long> experimentIds);

  List<Plate> findByProjectId(long projectId);

  List<Plate> findAllByProjectIdIn(List<Long> projectIds);
}
