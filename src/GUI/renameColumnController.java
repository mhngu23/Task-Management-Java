package GUI;

import Item.Column;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class renameColumnController extends updateWorkspace{
    private String current_column_name;
    private String project_name;
    @FXML
    private TextField columnnameTextField;
    @FXML
    private Label newColumnStatus;

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

    public void updateColumnNameOnAction(ActionEvent event) throws Exception {
        if (!columnnameTextField.getText().isEmpty()) {
            if (!user.getProjects().get(project_name).isColumnExist(columnnameTextField.getText())) {
                Column current_column = user.getProjects().get(project_name).getColumns().get(current_column_name);
                current_column.setColumn_name(columnnameTextField.getText());
                dbHelper.renameColumnDB(current_column_name, columnnameTextField.getText());
                String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
                byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
                ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
                ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
                ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
                accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.closeButton);
            } else {
                this.newColumnStatus.setText("This column is existed please enter another name");
            }
        }
        else{
            this.newColumnStatus.setText("Please enter the name of the column!");
        }
    }


    public void setCurrent_column_name(String current_column_name) {
        this.current_column_name = current_column_name;
    }
    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
