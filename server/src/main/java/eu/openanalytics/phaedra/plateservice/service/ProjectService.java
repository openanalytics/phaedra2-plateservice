package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.repository.ProjectRepository;
import eu.openanalytics.phaedra.platservice.dto.ProjectDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
	private static final ModelMapper modelMapper = new ModelMapper();

	private final ProjectRepository projectRepository;
	
	private final ExperimentService experimentService;

	public ProjectService(ProjectRepository projectRepository, ExperimentService experimentService) {
		this.projectRepository = projectRepository;
		this.experimentService = experimentService;
	}

	public ProjectDTO createProject(ProjectDTO projectDTO) {
		Project project = new Project();
		modelMapper.typeMap(ProjectDTO.class, Project.class)
				.map(projectDTO, project);
		project = projectRepository.save(project);
		return mapToProjectDTO(project);
	}

	public void updateProject(ProjectDTO projectDTO) {
		Optional<Project> project = projectRepository.findById(projectDTO.getId());
		project.ifPresent(p -> {
			modelMapper.typeMap(ProjectDTO.class, Project.class)
					.setPropertyCondition(Conditions.isNotNull())
					.map(projectDTO, p);
			projectRepository.save(p);
		});
	}

	public void deleteProject(long projectId) {
//		experimentService.deleteExperimentsByProjectId(projectId);
		projectRepository.deleteById(projectId);
	}

	public List<ProjectDTO> getAllProjects() {
		List<Project> projects = (List<Project>) projectRepository.findAll();
		return projects.stream()
				.map(this::mapToProjectDTO)
				.collect(Collectors.toList());
	}
	
	public ProjectDTO getProjectById(long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		return result.map(this::mapToProjectDTO).orElse(null);
	}

	private ProjectDTO mapToProjectDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		modelMapper.typeMap(Project.class, ProjectDTO.class).map(project, projectDTO);
		return projectDTO;
	}
}
