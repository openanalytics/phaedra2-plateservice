package eu.openanalytics.phaedra.plateservice.dao;

import java.util.Collections;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.util.caching.CacheFactory;
import eu.openanalytics.phaedra.util.caching.model.ICache;

@Repository
public class ProjectDAO extends BaseCachingDAO<Project> {

	@Override
	protected ICache<Project> initializeCache() {
		return CacheFactory.createCache("project");
	}
	
	public Optional<Project> getProject(long id) {
		String sql = "select proj.project_id, proj.name as project_name, proj.description as project_description,"
				+ " proj.owner as project_owner, proj.team_code as project_team, proj.access_scope"
				+ " from phaedra.hca_project proj where proj.project_id = " + id;
		
		return findValueById(id, sql, (rs, rownum) -> {
				Project proj = new Project();
				proj.setId(rs.getLong("project_id"));
				proj.setName(rs.getString("project_name"));
				proj.setDescription(rs.getString("project_description"));
				proj.setOwner(rs.getString("project_owner"));
				proj.setTeam(rs.getString("project_team"));
				proj.setAccessScope(rs.getString("access_scope"));
				return proj;
		});
	}
	
	public long createProject(Project project) {
		if (project.getName() == null || project.getName().trim().isEmpty())
			throw new IllegalArgumentException("Project must have a non-empty name");
		if (project.getId() != 0)
			throw new IllegalArgumentException("New projects must not have an existing ID");
		
		String sql = "insert into phaedra.hca_project (project_id, name, description, owner, team_code, access_scope)"
				+ " values (nextval('hca_project_s'), " + String.join(",", Collections.nCopies(5, "?")) + ")"
				+ " returning project_id";
		
		return createNewValue(project, sql, ps -> {
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setString(3, project.getOwner());
			ps.setString(4, project.getTeam());
			ps.setString(5, project.getAccessScope());
		});
	}
	
	public void updateProject(long projectId, Project project) {
		String sql = "update phaedra.hca_project"
				+ " set name = ?, description = ?, owner = ?, team_code = ?, access_scope = ?"
				+ " where project_id = ?";
		
		updateValue(projectId, project, sql, ps -> {
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setString(3, project.getOwner());
			ps.setString(4, project.getTeam());
			ps.setString(5, project.getAccessScope());
			ps.setLong(6, project.getId());
		});
	}
	
	public void deleteProject(long projectId) {
		deleteValue(projectId, "delete from phaedra.hca_project where project_id = " + projectId);
	}
}