package eu.openanalytics.phaedra.plateservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

}
