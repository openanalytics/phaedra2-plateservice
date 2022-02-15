/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table("hca_plate_template")
public class PlateTemplate {
    @Id
    @NotNull
    private long id;
    private String name;
    private String description;
    @NotNull
    private Integer rows;
    @NotNull
    private Integer columns;
    @Column("created_on")
    @NotNull
    private Date createdOn;
    @Column("created_by")
    @NotNull
    private String createdBy;
    @Column("updated_on")
    private Date updatedOn;
    @Column("updated_by")
    private String updatedBy;
}
