package eu.openanalytics.phaedra.plateservice.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Experiment;

@Repository
public interface ExperimentRepository extends CrudRepository<Experiment, Long> {

	List<Experiment> findByProjectId(long projectId);
	
	@Query("delete from hca_experiment e where e.project_id = :projectId")
	void deleteByProjectId(@Param("projectId") long projectId);
}
