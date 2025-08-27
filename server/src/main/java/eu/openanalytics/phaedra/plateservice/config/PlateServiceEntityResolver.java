package eu.openanalytics.phaedra.plateservice.config;

import eu.openanalytics.phaedra.plateservice.exceptions.PlateNotFoundException;
import eu.openanalytics.phaedra.plateservice.exceptions.WellNotFoundException;
import eu.openanalytics.phaedra.plateservice.service.ExperimentService;
import eu.openanalytics.phaedra.plateservice.service.PlateService;
import eu.openanalytics.phaedra.plateservice.service.ProjectService;
import eu.openanalytics.phaedra.plateservice.service.WellService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlateServiceEntityResolver {

    private final ProjectService projectService;
    private final ExperimentService experimentService;
    private final PlateService plateService;
    private final WellService wellService;

    public PlateServiceEntityResolver(
        ProjectService projectService,
        ExperimentService experimentService,
        PlateService plateService,
        WellService wellService) {
        this.projectService = projectService;
        this.experimentService = experimentService;
        this.plateService = plateService;
        this.wellService = wellService;
    }

    public List<Object> resolveEntities(List<Map<String, Object>> representations) {
        List<Object> entities = new ArrayList<>();

        for (Map<String, Object> representation : representations) {
            String typename = (String) representation.get("__typename");
            Object id = representation.get("id");

            if (id == null) continue;

            try {
                Object entity = resolveEntity(typename, id);
                if (entity != null) {
                    entities.add(entity);
                }
            } catch (Exception e) {
                System.err.println("Failed to resolve entity " + typename + " with id " + id + ": " + e.getMessage());
            }
        }

        return entities;
    }

    private Object resolveEntity(String typename, Object id)
        throws PlateNotFoundException, WellNotFoundException {
        Long entityId = parseId(id);
        if (entityId == null) return null;

        return switch (typename) {
            case "ProjectDTO" -> projectService.getProjectById(entityId);
            case "ExperimentDTO" -> experimentService.getExperimentById(entityId);
            case "PlateDTO" -> plateService.getPlateById(entityId);
            case "WellDTO" -> wellService.getWellById(entityId);
            default -> null;
        };
    }

    private Long parseId(Object id) {
        try {
            if (id instanceof Number) {
                return ((Number) id).longValue();
            }
            if (id instanceof String) {
                return Long.parseLong((String) id);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid ID format: " + id);
        }
        return null;
    }
}
