package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Well;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellRepository extends CrudRepository<Well, Long> {

//	@Query("delete from hca_well w where w.plate_id = :plateId")
//	void deleteByPlateId(@Param("plateId") long plateId);
//
//	@Query("delete from hca_well w where w.plate_id in"
//			+ " (select plate_id from hca_plate where experiment_id = :experimentId)")
//	void deleteByExperimentId(@Param("experimentId") long experimentId);
//
//	@Query("delete from hca_well w where w.plate_id in"
//			+ " (select p.plate_id from hca_plate p, hca_experiment e"
//			+ " where p.experiment_id = e.experiment_id and e.project_id = :projectId)")
//	void deleteByProjectId(@Param("projectId") long projectId);

	List<Well> findByPlateId(long plateId);
}
