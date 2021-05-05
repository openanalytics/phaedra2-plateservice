package eu.openanalytics.phaedra.plateservice.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Plate;

@Repository
public interface PlateRepository extends CrudRepository<Plate, Long> {

	List<Plate> findByExperimentId(long experimentId);
	
	@Query("delete from hca_plate p where p.experiment_id = :experimentId")
	void deleteByExperimentId(@Param("experimentId") long experimentId);

	@Query("delete from hca_plate p where p.experiment_id in"
			+ " (select experiment_id from hca_experiment where project_id = :projectId)")
	void deleteByProjectId(@Param("projectId") long projectId);
}
