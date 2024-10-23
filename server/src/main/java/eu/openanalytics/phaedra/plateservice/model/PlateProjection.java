package eu.openanalytics.phaedra.plateservice.model;

public interface PlateProjection {

  Long getId();

  String getBarcode();

  String getDescription();

  Integer getExperimentId();

  Integer getRows();

  Integer getColumns();
}
