/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.dto.PlateDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.PlateTemplateDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.plateservice.enumeration.LinkStatus;
import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.exceptions.WellNotFoundException;
import eu.openanalytics.phaedra.plateservice.service.PlateMeasurementService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.PlateTemplateService;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PlateGraphQLController {

  private final PlateService plateService;
  private final WellService wellService;
  private final PlateMeasurementService plateMeasurementService;
  private final PlateTemplateService plateTemplateService;

  public PlateGraphQLController(
      PlateService plateService,
      WellService wellService,
      PlateMeasurementService plateMeasurementService,
      PlateTemplateService plateTemplateService
  ) {
    this.plateService = plateService;
    this.wellService = wellService;
    this.plateMeasurementService = plateMeasurementService;
    this.plateTemplateService = plateTemplateService;
  }

  @QueryMapping
  public List<PlateDTO> getPlates(@Argument List<Long> plateIds) {
    List<PlateDTO> result = plateService.getPlates(plateIds);
    if (CollectionUtils.isNotEmpty(result)) {
      result.parallelStream().forEach(this::enrichLinkedPlateDTOInfo);
    }
    return result;
  }

  @QueryMapping
  public List<PlateDTO> getPlatesByExperimentId(@Argument Long experimentId) {
    List<PlateDTO> result =
        ObjectUtils.isNotEmpty(experimentId) ?
            plateService.getPlatesByExperimentId(experimentId) : new ArrayList<>();
    if (CollectionUtils.isNotEmpty(result)) {
      result.parallelStream().forEach(this::enrichLinkedPlateDTOInfo);
    }
    return result;
  }

  @QueryMapping
  public List<PlateDTO> getPlatesByExperimentIds(@Argument List<Long> experimentIds) {
    if (CollectionUtils.isEmpty(experimentIds)) {
      return Collections.emptyList();
    }
    List<PlateDTO> result =
        CollectionUtils.isNotEmpty(experimentIds) ?
            plateService.getPlatesByExperimentIds(experimentIds) : Collections.emptyList();
    if (CollectionUtils.isNotEmpty(result)) {
      result.parallelStream().forEach(this::enrichLinkedPlateDTOInfo);
    }
    return result;
  }

  @QueryMapping
  public List<PlateDTO> getPlatesByBarcode(@Argument String barcode) {
    List<PlateDTO> result = StringUtils.isNotEmpty(barcode) ?
        plateService.getPlatesByBarcode(barcode) : new ArrayList<>();
    if (CollectionUtils.isNotEmpty(result)) {
      result.parallelStream().forEach(this::enrichLinkedPlateDTOInfo);
    }
    return result;
  }

  @QueryMapping
  public PlateDTO getPlateById(@Argument Long plateId) {
    try {
      PlateDTO result = plateService.getPlateById(plateId);
      if (result != null) {
        enrichLinkedPlateDTOInfo(result);
      }
      return result;
    } catch (PlateNotFoundException e) {
      return null;
    }
  }

  @QueryMapping
  public List<WellDTO> getPlateWells(@Argument Long plateId) {
    try {
      return wellService.getWellsByPlateId(plateId);
    } catch (PlateNotFoundException e) {
      return new ArrayList<>();
    }
  }

  @QueryMapping
  public List<PlateMeasurementDTO> getMeasurementsByPlateId(@Argument Long plateId) {
    return plateMeasurementService.getPlateMeasurements(plateId);
  }

  @QueryMapping
  public PlateMeasurementDTO getActiveMeasurementByPlateId(@Argument Long plateId) {
    return plateMeasurementService.getPlateMeasurement(plateId, true);
  }

  @QueryMapping
  public List<PlateMeasurementDTO> getActiveMeasurementByPlateIds(@Argument List<Long> plateIds) {
    return plateMeasurementService.getPlateMeasurements(plateIds, true);
  }

  @MutationMapping
  public PlateMeasurementDTO linkMeasurement(@Argument Long plateId, @Argument Long measurementId) {
    return plateMeasurementService.linkMeasurement(plateId, measurementId);
  }

  @QueryMapping
  public List<PlateMeasurementDTO> getActiveMeasurementsByExperimentId(
      @Argument Long experimentId) {
    return plateMeasurementService.getPlateMeasurementsByExperimentId(experimentId, true);
  }


  @QueryMapping
  public List<WellDTO> getWells(@Argument List<Long> wellIds) {
    return wellService.getWells(wellIds);
  }

  @QueryMapping
  public WellDTO getWellById(@Argument Long wellId)
      throws WellNotFoundException, PlateNotFoundException {
    return wellService.getWellById(wellId);
  }

  @QueryMapping
  public List<WellDTO> getWellsByPlateIds(@Argument List<Long> plateIds) {
    if (CollectionUtils.isEmpty(plateIds)) {
      return Collections.emptyList();
    }
    return wellService.getWellsByPlateIds(plateIds);
  }

  @QueryMapping
  public List<WellDTO> getWellsByExperimentId(@Argument Long experimentId) {
    return wellService.getWellsbyExperimentId(experimentId);
  }

  @QueryMapping
  public List<WellDTO> getWellsByExperimentIds(@Argument List<Long> experimentIds) {
    return wellService.getWellsbyExperimentIds(experimentIds);
  }

  private void enrichLinkedPlateDTOInfo(PlateDTO plateDTO) {
    if (LinkStatus.LINKED.equals(plateDTO.getLinkStatus())) {
      PlateTemplateDTO plateTemplate = plateTemplateService.getPlateTemplateById(
          Long.parseLong(plateDTO.getLinkTemplateId()));
      if (plateTemplate != null) {
        plateDTO.setLinkTemplateName(plateTemplate.getName());
      }
    }
  }
}
