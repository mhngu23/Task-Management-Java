package GUI;

import Item.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class renameProjectController extends updateWorkspace{

    private String current_projectName;
    @FXML
    private TextField projectnameTextField;
    @FXML
    private Label newprojectStatus;


    public void closeButtonAction(ActionEvent event) throws SQLException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.closeButton);
        connection.close();
        stage.close();
    }

    public void updateProjectNameOnAction(ActionEvent event) throws Exception {
        if (!projectnameTextField.getText().isEmpty()) {
            if (!user.isProjectExist(projectnameTextField.getText())) {
                Project current_project = user.getProjects().get(current_projectName);
                current_project.setProject_name(projectnameTextField.getText());
                dbHelper.renameProjectDB(current_projectName, projectnameTextField.getText());
                String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
                byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
                ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
                ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
                ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
                accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.closeButton);
            }
            else{
                this.newprojectStatus.setText("This project is existed please enter another name");
            }
        }
        else{
            this.newprojectStatus.setText("Please enter the name of the project!");
        }
    }



    public void setCurrent_project_name(String current_project_name) {
        this.current_projectName = current_project_name;
    }
}
