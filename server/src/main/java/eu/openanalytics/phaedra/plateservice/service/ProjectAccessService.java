package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.ProjectAccess;
import eu.openanalytics.phaedra.plateservice.repository.ProjectAccessRepository;
import eu.openanalytics.phaedra.platservice.dto.ExperimentDTO;
import eu.openanalytics.phaedra.platservice.dto.PlateDTO;
import eu.openanalytics.phaedra.platservice.dto.ProjectAccessDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import eu.openanalytics.phaedra.util.auth.AuthorizationHelper;

@Service
public class ProjectAccessService {

	private final ProjectAccessRepository projectAccessRepository;
	private final PlateService plateService;
	private final ExperimentService experimentService;
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	public ProjectAccessService(ProjectAccessRepository projectAccessRepository,
			PlateService plateService,
			ExperimentService experimentService) {
		
		this.projectAccessRepository = projectAccessRepository;
		this.plateService = plateService;
		this.experimentService = experimentService;
	}
	
	/**
	 * Test if the specified principal has an access level on the specified project that
	 * is at least equal to requiredLevel.
	 * 
	 * @param principal The authenticated principal to check.
	 * @param projectId The ID of the project to check.
	 * @param requiredLevel The minimum access level that is needed.
	 * @return True if the principal has the required access level, false otherwise.
	 */
	public boolean hasAccessLevel(Object principal, long projectId, ProjectAccessLevel requiredLevel) {
		ProjectAccessLevel level = getAccessLevel(principal, projectId);
		return (level != null && level.compareTo(requiredLevel) >= 0);
	}
	
	public boolean hasAccessLevelForExperiment(Object principal, long experimentId, ProjectAccessLevel requiredLevel) {
		ExperimentDTO experiment = experimentService.getExperimentById(experimentId);
		return (experiment == null) ? false : hasAccessLevel(principal, experiment.getProjectId(), requiredLevel);
	}
	
	public boolean hasAccessLevelForPlate(Object principal, long plateId, ProjectAccessLevel requiredLevel) {
		PlateDTO plate = plateService.getPlateById(plateId);
		return (plate == null) ? false : hasAccessLevelForExperiment(principal, plate.getExperimentId(), requiredLevel);
	}
	
	/**
	 * Test if the specified principal has access to the specified project, and if so,
	 * what their highest access level is.
	 * 
	 * @param principal The authenticated principal to check.
	 * @param projectId The ID of the project to check.
	 * @return The highest access level, or null if the project is not accessible.
	 */
	public ProjectAccessLevel getAccessLevel(Object principal, long projectId) {
		// Note: administrators automatically have admin-level access to all projects.
		if (AuthorizationHelper.hasAdminAccess(principal)) return ProjectAccessLevel.Admin;
		
		return getProjectAccessForProject(projectId)
			.stream()
			.filter(pa -> AuthorizationHelper.hasTeamAccess(principal, pa.getTeamName()))
			.collect(Collectors.reducing(BinaryOperator.maxBy((pa1, pa2) -> pa1.getAccessLevel().compareTo(pa2.getAccessLevel()))))
			.map(ProjectAccessDTO::getAccessLevel)
			.orElse(null);
	}
	
	/* Basic CRUD */
	
	public ProjectAccessDTO createProjectAccess(ProjectAccessDTO projectAccess) {
		ProjectAccess newAccess = projectAccessRepository.save(modelMapper.map(projectAccess, ProjectAccess.class));
		return modelMapper.map(newAccess, ProjectAccessDTO.class);
	}
	
	public void deleteProjectAccess(long projectAccessId) {
		projectAccessRepository.deleteById(projectAccessId);
	}
	
	public List<ProjectAccessDTO> getProjectAccessForProject(long projectId) {
		return projectAccessRepository.findByProjectId(projectId)
				.stream()
				.map(pa -> modelMapper.map(pa, ProjectAccessDTO.class))
				.toList();
	}
}
