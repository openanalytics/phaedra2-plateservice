package eu.openanalytics.phaedra.plateservice.repository;

import eu.openanalytics.phaedra.plateservice.model.WellTemplate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellTemplateRepository extends CrudRepository<WellTemplate, Long> {

    /*@Query("delete from hca_well_template p where p.plate_template_id=:plateTemplateId")
    void deleteByPlateTemplateId(@Param("plateTemplateId") long plateTemplateId);*/

    List<WellTemplate> findByPlateTemplateId(long plateTemplateId);
}
