package eu.openanalytics.phaedra.plateservice.record;

public record PlateProjection(
    Long id,
    String barcode,
    String description,
    Integer experimentId,
    Integer rows,
    Integer columns
) {

}
