package eu.openanalytics.phaedra.plateservice.record;

public record PlateProjectionRecord(
    Long id,
    String barcode,
    String description,
    Integer experimentId,
    Integer rows,
    Integer columns
) {
}
