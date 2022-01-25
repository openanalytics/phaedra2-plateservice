package eu.openanalytics.phaedra.plateservice.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.WellSubstance;

@Repository
public interface WellSubstanceRepository extends CrudRepository<WellSubstance, Long> {

    WellSubstance findByWellId(long wellId);
    
    @Query("select s.* from hca_well_substance s, hca_well w where s.well_id = w.id and w.plate_id = :plateId")
    List<WellSubstance> findByPlateId(long plateId);

}
