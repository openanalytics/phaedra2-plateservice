package eu.openanalytics.phaedra.plateservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.openanalytics.phaedra.plateservice.model.Project;
import eu.openanalytics.phaedra.plateservice.service.ProjectService;

public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(projectService).isNotNull();
    }
    
    @Test
    public void createProject() throws Exception {
    	final String prjName = "Test Project 1";
    	final String prjDesc = "This is a test project";
    	final String prjOwner = "testProjectOwner";
    	
    	Project newProject = new Project();
    	newProject.setName(prjName);
    	newProject.setDescription(prjDesc);
    	newProject.setOwner(prjOwner);
    	newProject = projectService.createProject(newProject);
    	
    	assertThat(newProject).isNotNull();
    	assertThat(newProject.getId()).isGreaterThan(0);
    	assertEquals(prjName, newProject.getName());
    	assertEquals(prjDesc, newProject.getDescription());
    	assertEquals(prjOwner, newProject.getOwner());
    }

}
