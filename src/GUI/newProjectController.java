package GUI;

import Item.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class newProjectController extends updateWorkspace {
    @FXML
    private TextField newprojectTextBox;
    @FXML
    private Label newprojectStatus;

    private String newProjectName;

    public void OKButtonAction(ActionEvent event) throws Exception {
        if (!newprojectTextBox.getText().isEmpty()){
            if (!user.isProjectExist(newprojectTextBox.getText())) {
                newProjectName = newprojectTextBox.getText();
                updateDatabase();
            }
            else{
                newprojectStatus.setText("This project is existed please enter another name");
            }
        }
        else{
            newprojectStatus.setText("Please enter the name of the project!");
        }
    }

    public void updateDatabase() throws Exception {
        Workspace workspace = new Workspace();
        if (projects.isEmpty()){
            Project newProject = new Project(newProjectName, user.getUsername(), "default");
            dbHelper.registerProjectDB(user.getUsername(), newProject.getProject_name(), newProject.getStatus());
            user.addProject(newProject.getProject_name(), newProject);
            workspace.start(user, user.getProjects());
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }else{
            Project newProject = new Project(newProjectName, user.getUsername(), "not default");
            dbHelper.registerProjectDB(user.getUsername(), newProject.getProject_name(), newProject.getStatus());
            user.addProject(newProject.getProject_name(), newProject);
            workspace.start(user, user.getProjects());
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }
}
