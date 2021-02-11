package eu.openanalytics.phaedra.plateservice.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Well;

@Repository
public interface WellRepository extends CrudRepository<Well, Long> {

	List<Well> findByPlateId(long plateId);

	@Query("delete from phaedra.hca_plate_well w where w.plate_id = :plateId")
	void deleteByPlateId(@Param("plateId") long plateId);
	
	@Query("delete from phaedra.hca_plate_well w where w.plate_id in"
			+ " (select plate_id from phaedra.hca_plate where experiment_id = :experimentId)")
	void deleteByExperimentId(@Param("experimentId") long experimentId);
	
	@Query("delete from phaedra.hca_plate_well w where w.plate_id in"
			+ " (select p.plate_id from phaedra.hca_plate p, phaedra.hca_experiment e"
			+ " where p.experiment_id = e.experiment_id and e.project_id = :projectId)")
	void deleteByProjectId(@Param("projectId") long projectId);
}
