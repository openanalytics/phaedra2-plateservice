package eu.openanalytics.phaedra.plateservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.repository.ProjectRepository;
import eu.openanalytics.phaedra.platservice.dto.ProjectDTO;
import eu.openanalytics.phaedra.platservice.enumartion.ProjectAccessLevel;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Service
public class ProjectService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ProjectRepository projectRepository;
	
	private final ExperimentService experimentService;
	private final ProjectAccessService projectAccessService;
	private final IAuthorizationService authService;
	
	public ProjectService(ProjectRepository projectRepository, ExperimentService experimentService,
			ProjectAccessService projectAccessService, IAuthorizationService authService) {
		
		this.projectRepository = projectRepository;
		this.experimentService = experimentService;
		this.projectAccessService = projectAccessService;
		this.authService = authService;
	}

	public ProjectDTO createProject(ProjectDTO projectDTO) {
		projectAccessService.checkCanCreateProjects();
		
		Project project = new Project();
		modelMapper.typeMap(ProjectDTO.class, Project.class).map(projectDTO, project);
		project.setCreatedBy(authService.getCurrentPrincipalName());
		project.setCreatedOn(new Date());
		project = projectRepository.save(project);
		
		return mapToProjectDTO(project);
	}

	public void updateProject(ProjectDTO projectDTO) {
		Optional<Project> project = projectRepository.findById(projectDTO.getId());
		project.ifPresent(p -> {
			projectAccessService.checkAccessLevel(p.getId(), ProjectAccessLevel.Write);
			modelMapper.typeMap(ProjectDTO.class, Project.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(projectDTO, p);
			p.setUpdatedBy(authService.getCurrentPrincipalName());
			p.setUpdatedOn(new Date());
			projectRepository.save(p);
		});
	}

	public void deleteProject(long projectId) {
		projectAccessService.checkAccessLevel(projectId, ProjectAccessLevel.Admin);
//		experimentService.deleteExperimentsByProjectId(projectId);
		projectRepository.deleteById(projectId);
	}

	public List<ProjectDTO> getAllProjects() {
		List<Project> projects = (List<Project>) projectRepository.findAll();
		return projects.stream()
				.filter(p -> projectAccessService.hasAccessLevel(p.getId(), ProjectAccessLevel.Read))
				.map(this::mapToProjectDTO)
				.collect(Collectors.toList());
	}
	
	public ProjectDTO getProjectById(long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		return result
				.map(this::mapToProjectDTO)
				.filter(p -> projectAccessService.hasAccessLevel(p.getId(), ProjectAccessLevel.Read))
				.orElse(null);
	}

	private ProjectDTO mapToProjectDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		modelMapper.typeMap(Project.class, ProjectDTO.class).map(project, projectDTO);
		return projectDTO;
	}
}
