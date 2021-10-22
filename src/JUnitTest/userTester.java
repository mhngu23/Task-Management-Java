package JUnitTest;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Item.User;
import Item.Project;

public class userTester {
    private User user_1;

    @After
    public void tearDown() {user_1 = null;}

    @Test
    public void  test_addProject(){
        /*
        Testing user.addProject function adds correct numbers of project.
         */
        Project project = new Project("Assignment 1", "mhngu23", "default");
        Project project_2 = new Project( "Assignment 2" ,"mhngu23", "not default");
        this.user_1.addProject(project.getProject_name(), project);
        this.user_1.addProject(project_2.getProject_name(), project_2);
        Assert.assertEquals(2, this.user_1.getProjects().size());
    }




    @Test
    public void test_newProject_emptyProjectHashMap(){
        /*
        Testing user.newProject function
        --> Will add the new project to the Item.Project list and make this project default if the project list is empty
         */
        this.user_1.newProject("Assignment 3");
        Assert.assertEquals("mhngu23", this.user_1.getProjects().get("Assignment 3").getUsername());
        Assert.assertEquals("default", this.user_1.getProjects().get("Assignment 3").getStatus());
    }

    @Test
    public void test_newProject_notEmptyProjectHashMap(){
        /*
        Testing user.newProject function
        --> Will add the new project to the Item.Project list and make this project default if the project list is empty
        --> Item.Project from the 2nd one is not default
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 3");
        Assert.assertEquals("mhngu23", this.user_1.getProjects().get("Assignment 3").getUsername());
        Assert.assertEquals("not default", this.user_1.getProjects().get("Assignment 3").getStatus());
    }

    @Test
    public void test_deleteProject_emptyProjectHashMap(){
        /*
        Testing user.deleteProject function when projectHashMap is empty
        --> If the projectHashMap is empty will print "The project you want to delete does not exist"
         */
        this.user_1.deleteProject("Assignment 3");
        Assert.assertEquals(0, this.user_1.getProjects().size());
    }

    @Test
    public void test_deleteProject_notEmptyProjectHashMap_deleteProjectExist(){
        /*
        Testing user.deleteProject function when projectHashMap is not empty
        The delete project exist
        --> Return correct numbers of item in the HashMap
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 3");
        this.user_1.deleteProject("Assignment 3");
        Assert.assertEquals(1, this.user_1.getProjects().size());
    }

    @Test
    public void test_deleteProject_notEmptyProjectHashMap_deleteProjectNotExist(){
        /*
        Testing user.deleteProject function when projectHashMap is not empty
        The delete project does not exist
        --> If the delete project does not exist will print "The project you want to delete does not exist"
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 3");
        this.user_1.deleteProject("Assignment 2");
        Assert.assertEquals(2, this.user_1.getProjects().size());
    }

    @Test
    public void test_editProjectName_emptyProjectHashMap(){
        /*
        Testing user.editProjectName function when projectHashMap is empty
        --> If the projectHashMap is empty will print "The project you want to edit does not exist"
         */
        this.user_1.editProjectName("Assignment 3", "Assignment 1");
        Assert.assertEquals(0, this.user_1.getProjects().size());
    }

    @Test
    public void test_editProjectName_notEmptyProjectHashMap_editProjectNotExist(){
        /*
        Testing user.editProjectName function when projectHashMap is not empty
        --> If the project does not exist will print "The project you want to edit does not exist"
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.editProjectName("Assignment 2", "Assignment 3");
        assertEquals(1, this.user_1.getProjects().size());
        Assert.assertEquals("Assignment 1", this.user_1.getProjects().get("Assignment 1").getProject_name());
    }

    @Test
    public void test_editProjectName_notEmptyProjectHashMap_editProjectExist(){
        /*
        Testing user.editProjectName function when projectHashMap is not empty
        --> If the project exist will change the project_name to new_project_name
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.editProjectName("Assignment 1", "Assignment 3");
        assertEquals(1, this.user_1.getProjects().size());
        Assert.assertEquals("Assignment 3", this.user_1.getProjects().get("Assignment 3").getProject_name());
    }

    @Test
    public void test_editProjectName_notEmptyProjectHashMap_editProjectExist_newProjectNameEmpty(){
        /*
        Testing user.editProjectName function when projectHashMap is not empty
        --> If the project exist but the newProjectName is empty will print "New project name cannot be empty"
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.editProjectName("Assignment 1", "    ");
        assertEquals(1, this.user_1.getProjects().size());
        Assert.assertEquals("Assignment 1", this.user_1.getProjects().get("Assignment 1").getProject_name());
    }

    @Test
    public void test_setDefaultProject_emptyProjectHashMap(){
        /*
        Testing user.setDefaultProject function when projectHashMap is empty
        --> If the projectHashMap is empty will print "The project you want to set as default does not exist"
         */
        this.user_1.setDefaultProject("Assignment 3");
        Assert.assertEquals(0, this.user_1.getProjects().size());
    }

    @Test
    public void test_setDefaultProject_notEmptyProjectHashMap_defaultProjectNotExist(){
        /*
        Testing user.editProjectName function when projectHashMap is not empty
        --> If the project does not exist will print "The project you want to set as default does not exist"
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.setDefaultProject("Assignment 3");
        assertEquals(1, this.user_1.getProjects().size());
        Assert.assertEquals("default", this.user_1.getProjects().get("Assignment 1").getStatus());
    }

    @Test
    public void test_setDefaultProject_notEmptyProjectHashMap_defaultProjectExist(){
        /*
        Testing user.setDefaultProject function when projectHashMap is not empty
        --> If the project exist will set the project as default and set the default project to non default
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 3");
        this.user_1.setDefaultProject("Assignment 3");
        assertEquals(2, this.user_1.getProjects().size());
        Assert.assertEquals("default", this.user_1.getProjects().get("Assignment 3").getStatus());
        Assert.assertEquals("not default", this.user_1.getProjects().get("Assignment 1").getStatus());
    }

    @Test
    public void test_displayDefaultProject_projectIsEmpty(){
        /*
        Testing user.displayDefaultProject
        --> If the project is empty print "This project is empty"
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 2");
        this.user_1.displayDefaultProject();
    }

    @Test
    public void test_displayDefaultProject_columnIsEmpty(){
        /*
        Testing user.displayDefaultProject
        --> If the column in a Project is empty print column_name and "This column is empty"
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 2");
        this.user_1.getProjects().get("Assignment 1").newColumn("to do");
        this.user_1.getProjects().get("Assignment 1").newColumn("in progress");
        this.user_1.getProjects().get("Assignment 2").newColumn("to do");
        this.user_1.displayDefaultProject();
    }

    @Test
    public void test_displayDefaultProject_columnIsNotEmpty(){
        /*
        Testing user.displayDefaultProject
        --> If the column in a Project is not empty print column_name and task_name and task_description
         */
        this.user_1.newProject("Assignment 1");
        this.user_1.newProject("Assignment 2");
        this.user_1.getProjects().get("Assignment 1").newColumn("to do");
        this.user_1.getProjects().get("Assignment 1").newColumn("in progress");
        this.user_1.getProjects().get("Assignment 2").newColumn("to do");
        this.user_1.getProjects().get("Assignment 1").getColumns().get("to do").newTask("Reading", "Read the brief");
        this.user_1.getProjects().get("Assignment 1").getColumns().get("in progress").newTask("Reading", "Research");
        this.user_1.displayDefaultProject();
    }
}
