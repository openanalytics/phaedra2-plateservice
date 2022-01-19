package eu.openanalytics.phaedra.plateservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.ProjectAccess;

@Repository
public interface ProjectAccessRepository extends CrudRepository<ProjectAccess, Long> {

	List<ProjectAccess> findByProjectId(Long projectId);
	
}
