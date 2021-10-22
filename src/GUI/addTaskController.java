package GUI;

import Item.Column;
import Item.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class addTaskController extends updateWorkspace{
    private String project_name;
    private String column_name;
    private String newTaskName;
    private String newTaskDescription;
    @FXML
    private TextField nameTaskTextBox;
    @FXML
    private TextField descriptionTaskTextBox;
    @FXML
    private Label newTaskStatus;

    public void OKButtonAction(ActionEvent event) throws Exception {
        if (!nameTaskTextBox.getText().isEmpty()){
            // Intentionally not checking same name of task assuming one project can have multiple tasks of the same name
            newTaskName = nameTaskTextBox.getText();
            newTaskDescription = descriptionTaskTextBox.getText();
            updateDatabase();
        }
        else{
            newTaskStatus.setText("Please enter the name of the task!");
        }
    }

    public void updateDatabase() throws Exception {
        Task newTask = new Task(newTaskName, newTaskDescription);
        user.getProjects().get(project_name).getColumns().get(column_name).addTasks(newTask.getTask_name(), newTask);
        dbHelper.registerTaskDB(user.getUsername(), project_name, column_name, newTaskName, newTaskDescription);
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.closeButton);
        connection.close();
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setColumn_name(String column_name){ this.column_name = column_name;}
}
