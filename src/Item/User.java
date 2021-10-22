package Item;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class User {
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private byte[] profile_picture; //Byte array
    private final LinkedHashMap<String, Project> projects = new LinkedHashMap<>();


    public User(String username, String password, String first_name, String last_name, byte[] profile_picture){
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_picture = profile_picture;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public byte [] getProfile_picture() throws IOException {
        return this.profile_picture;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setProfile_picture(byte[] profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void addProject(String project_name, Project project){

        this.projects.put(project_name, project);
    }
    public LinkedHashMap<String, Project> getProjects() {
        return projects;
    }

    //TODO What if project name is already exist???
    public void newProject(String project_name){
        Project newProject;
        if (!project_name.strip().strip().isEmpty()) {
            if (this.getProjects().isEmpty()) {
                newProject = new Project(project_name.strip(), this.getUsername(), "default");
            } else {
                newProject = new Project(project_name.strip(), this.getUsername(), "not default");
            }
            this.addProject(newProject.getProject_name(), newProject);
        }
        else{
            System.out.println("New project need a name");
        }
    }

    public boolean isProjectExist(String project_name){
        return this.projects.containsKey(project_name);
    }

    public void deleteProject(String project_name){
        if (this.getProjects().containsKey(project_name.strip())){
            Boolean deleteDefaultProject = false;
            if (this.getProjects().get(project_name).getStatus().equals("default")){
                deleteDefaultProject = true;
            }
            this.getProjects().remove(project_name.strip());
            Iterator<String> iterator = this.getProjects().keySet().iterator();
            if (deleteDefaultProject && iterator.hasNext()){
                String key = iterator.next();
                this.getProjects().get(key).setStatus("default");
            }
        }
    }

    public void editProjectName(String project_name, String new_project_name){
        // get project name from GUI
        if (this.getProjects().containsKey(project_name.strip())) {
            if (!new_project_name.strip().isEmpty()) {
                Project project_edited = this.getProjects().get(project_name.strip());
                project_edited.setProject_name(new_project_name.strip());
                this.addProject(project_edited.getProject_name(), project_edited);
                this.getProjects().remove(project_name.strip());
            }
            else{
                System.out.println("New project name cannot be empty");
            }
        } else {
            System.out.println("The project you want to edit does not exist");
        }
    }

    public void setDefaultProject(String project_name) {
        if (this.getProjects().containsKey(project_name.strip())) {
            for (Project project : this.getProjects().values()) {
                if (project.getStatus().equals("default")) {
                    project.setStatus("not default");
                }
            }
            Project project_want_to_change_default = this.getProjects().get(project_name);
            project_want_to_change_default.setStatus("default");
        }
    }

    public String displayDefaultProject() {
        for (Project project : this.getProjects().values()) {
            if (project.getStatus().equals("default")) {
                return project.getProject_name();
            }
        }
        return null;
    }
}
