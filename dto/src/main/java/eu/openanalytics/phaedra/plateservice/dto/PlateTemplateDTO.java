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
package eu.openanalytics.phaedra.plateservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlateTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private Integer rows;
    private Integer columns;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private Date createdOn;
    private String createdBy;
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss.SS")
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private Date updatedOn;
    private String updatedBy;
    private List<WellTemplateDTO> wells;
    private List<String> tags;
    private List<PropertyDTO> properties;
}
