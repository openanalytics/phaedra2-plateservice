package eu.openanalytics.phaedra.plateservice.service;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.ProjectAccess;
import eu.openanalytics.phaedra.plateservice.repository.ProjectAccessRepository;
import eu.openanalytics.phaedra.platservice.dto.ProjectAccessDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import eu.openanalytics.phaedra.util.auth.AuthorizationHelper;

@Service
public class ProjectAccessService {

	private final ProjectAccessRepository projectAccessRepository;

	private static final ModelMapper modelMapper = new ModelMapper();
	
	public ProjectAccessService(ProjectAccessRepository projectAccessRepository) {
		this.projectAccessRepository = projectAccessRepository;
	}
	
	public void checkCanCreateProjects() {
		AuthorizationHelper.performAccessCheck(AuthorizationHelper::hasUserAccess, e -> "Not authorized to create new projects");
	}
	
	/**
	 * Test if the current principal has an access level on the specified project that
	 * is at least equal to requiredLevel.
	 * If this is not the case, an AccessDeniedException will be thrown.
	 * 
	 * @param projectId The ID of the project to check.
	 * @param requiredLevel The minimum access level that is needed.
	 */
	public void checkAccessLevel(long projectId, ProjectAccessLevel requiredLevel) {
		AuthorizationHelper.performAccessCheck(principal -> {
			ProjectAccessLevel level = getAccessLevel(projectId);
			return (level != null && level.compareTo(requiredLevel) >= 0);
		}, e -> String.format("Not authorized: requires access level %s on project %d", requiredLevel, projectId));
	}
	
	/**
	 * Test if the current principal has an access level on the specified project that
	 * is at least equal to requiredLevel.
	 * 
	 * @param projectId The ID of the project to check.
	 * @param requiredLevel The minimum access level that is needed.
	 * @return True if the principal has the required access level, false otherwise.
	 */
	public boolean hasAccessLevel(long projectId, ProjectAccessLevel requiredLevel) {
		ProjectAccessLevel level = getAccessLevel(projectId);
		return (level != null && level.compareTo(requiredLevel) >= 0);
	}
	
	/**
	 * Test if the current principal has access to the specified project, and if so,
	 * what their highest access level is.
	 * 
	 * @param projectId The ID of the project to check.
	 * @return The highest access level, or null if the project is not accessible.
	 */
	public ProjectAccessLevel getAccessLevel(long projectId) {
		// Note: administrators automatically have admin-level access to all projects.
		if (AuthorizationHelper.hasAdminAccess()) return ProjectAccessLevel.Admin;
		
		return getProjectAccessForProject(projectId)
			.stream()
			.filter(pa -> AuthorizationHelper.hasTeamAccess(pa.getTeamName()))
			.collect(Collectors.reducing(BinaryOperator.maxBy((pa1, pa2) -> pa1.getAccessLevel().compareTo(pa2.getAccessLevel()))))
			.map(ProjectAccessDTO::getAccessLevel)
			.orElse(null);
	}
	
	/* Basic CRUD */
	
	@CacheEvict(value = "project_access", key = "#projectAccess?.projectId")
	public ProjectAccessDTO createProjectAccess(ProjectAccessDTO projectAccess) {
		ProjectAccess newAccess = projectAccessRepository.save(modelMapper.map(projectAccess, ProjectAccess.class));
		return modelMapper.map(newAccess, ProjectAccessDTO.class);
	}
	
	public void deleteProjectAccess(long projectAccessId) {
		projectAccessRepository.deleteById(projectAccessId);
	}
	
	@Cacheable("project_access")
	public List<ProjectAccessDTO> getProjectAccessForProject(long projectId) {
		return projectAccessRepository.findByProjectId(projectId)
				.stream()
				.map(pa -> modelMapper.map(pa, ProjectAccessDTO.class))
				.toList();
	}
}
