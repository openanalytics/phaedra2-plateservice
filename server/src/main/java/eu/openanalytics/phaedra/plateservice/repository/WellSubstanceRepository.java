/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.enumeration.SubstanceType;
import eu.openanalytics.phaedra.plateservice.model.WellSubstance;

@Repository
public interface WellSubstanceRepository extends CrudRepository<WellSubstance, Long> {

    WellSubstance findByWellId(long wellId);
    @Query("select s.* from hca_well_substance s inner join hca_well w on s.well_id = w.id where w.plate_id = :plateId and name = :name")
    List<WellSubstance> findWellSubstanceByPlateIdAndName(long plateId, String name);
    @Query("select s.* from hca_well_substance s inner join hca_well w on s.well_id = w.id where w.plate_id = :plateId and type = :type")
    List<WellSubstance> findWellSubstanceByPlateIdAndType(long plateId, SubstanceType type);
    @Query("select s.* from hca_well_substance s inner join hca_well w on s.well_id = w.id where w.plate_id = :plateId and name = :name and type = :type")
    List<WellSubstance> findWellSubstanceByPlateIdAndNameAndType(long plateId, String name, SubstanceType type);

    @Query("select s.* from hca_well_substance s inner join hca_well w on s.well_id = w.id where w.plate_id = :plateId")
    List<WellSubstance> findByPlateId(long plateId);
    @Query("select s.* from hca_well_substance s inner join hca_well w on s.well_id = w.id where w.plate_id = :plateId and w.welltype in (:wellTypes)")
    List<WellSubstance> findByPlateIdAndWellType(long plateId, Collection<String> wellTypes);

}
