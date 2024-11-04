package eu.openanalytics.phaedra.plateservice.record;

public record ExperimentProjection(
    Long id,
    String name,
    String description,
    Long projectId
) {
}
