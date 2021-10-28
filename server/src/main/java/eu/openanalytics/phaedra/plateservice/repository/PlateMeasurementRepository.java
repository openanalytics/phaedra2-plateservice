package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.PlateMeasurement;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateMeasurementRepository extends CrudRepository<PlateMeasurement, Long> {
    List<PlateMeasurement> findByPlateId(long plateId);

    PlateMeasurement findByPlateIdAndMeasurementId(long plateId, long measId);
}
