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
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Table("hca_well_template")
public class WellTemplate {
    @Id
    @NotNull
    private Long id;
    private String description;
    @NotNull
    private Integer row;
    @NotNull
    private Integer column;
    @Column("plate_template_id")
    @NotNull
    private Long plateTemplateId;
    //Default EMPTY wellType.
    @Column("well_type")
    @NotNull
    private String wellType = "EMPTY";
    //Default true
    @NotNull
    private boolean skipped = true;
    @Column("substance_name")
    private String substanceName;
    @Column("substance_type")
    private String substanceType;
    private double concentration;
    public WellTemplate(Long plateTemplateId) { this.plateTemplateId = plateTemplateId; }
}