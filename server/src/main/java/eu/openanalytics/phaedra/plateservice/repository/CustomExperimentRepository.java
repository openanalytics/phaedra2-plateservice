package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.Experiment;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomExperimentRepository {

  Experiment findById(long id);
  List<Experiment> findAll();
  List<Experiment> findAllByIdIn(List<Long> ids);
  List<Experiment> findNMostRecentExperiments(int n);
  List<Experiment> findAllByProjectId(long projectId);
  List<Experiment> findAllByProjectIdIn(Collection<Long> projectIds);

}
