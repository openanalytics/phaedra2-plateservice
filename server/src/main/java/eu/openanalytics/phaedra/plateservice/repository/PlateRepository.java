package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Plate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateRepository extends CrudRepository<Plate, Long> {

	@Query("delete from hca_plate p where p.experiment_id = :experimentId")
	void deleteByExperimentId(@Param("experimentId") long experimentId);

	@Query("delete from hca_plate p where p.experiment_id in"
			+ " (select experiment_id from hca_experiment where project_id = :projectId)")
	void deleteByProjectId(@Param("projectId") long projectId);

	@Query("select e.project_id from hca_plate p, hca_experiment e where p.experiment_id = e.id and p.id = :plateId")
	Long findProjectIdByPlateId(@Param("plateId") long plateId);

	List<Plate> findByExperimentId(long experimentId);

	List<Plate> findByBarcode(String barcoce);
}
