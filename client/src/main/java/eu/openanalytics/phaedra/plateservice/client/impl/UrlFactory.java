/**
 * Phaedra II
 * <p>
 * Copyright (C) 2016-2024 Open Analytics
 * <p>
 * ===========================================================================
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * Apache License as published by The Apache Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Apache
 * License for more details.
 * <p>
 * You should have received a copy of the Apache License along with this program.  If not, see
 * <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.client.impl;

public class UrlFactory {

  private String baseURL;

  public UrlFactory(String baseURL) {
    this.baseURL = baseURL;
  }

  public String projects() {
    return String.format("%s/projects", baseURL);
  }

  public String plate(Long plateId) {
    if (plateId != null) {
      return String.format("%s/plates/%s", baseURL, plateId);
    } else {
      return String.format("%s/plates", baseURL);
    }
  }

  public String plateWells(Long plateId) {
    return String.format("%s/plates/%s/wells", baseURL, plateId);
  }

  public String nWells(Integer n) {
    return String.format("%s/wells?n=%s", baseURL, n);
  }

  public String wellSubstances(Long plateId) {
    return String.format("%s/wellsubstances?plateId=%s", baseURL, plateId);
  }

  public String plateMeasurements(Long plateId) {
    return String.format("%s/plates/%s/measurements", baseURL, plateId);
  }

  public String experiment(Long experimentId) {
    if (experimentId == null) {
      return String.format("%s/experiments", baseURL);
    } else {
      return String.format("%s/experiments/%s", baseURL, experimentId);
    }
  }

  public String experiments(Long projectId) {
    return String.format("%s/projects/%s/experiments", baseURL, projectId);
  }

  public String platesByBarcode(String barcode) {
    return String.format("%s/plates?barcode=%s", baseURL, barcode);
  }

  public String experimentPlates(long eperimentId) {
    return String.format("%s/experiments/%s/plates", baseURL, eperimentId);
  }

  public String plateTemplatesByName(String name) {
    return String.format("%s/platetemplates?name=%s", baseURL, name);
  }
}
