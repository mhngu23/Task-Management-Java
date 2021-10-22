package GUI;

import Item.Column;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class addColumnController  extends updateWorkspace{
    private String project_name;
    private String newColumnName;
    @FXML
    private TextField newcolumnTextBox;
    @FXML
    private Label newcolumnStatus;

    public void OKButtonAction(ActionEvent event) throws Exception {
        if (!newcolumnTextBox.getText().isEmpty()){
            if (!user.getProjects().get(project_name).isColumnExist(newcolumnTextBox.getText())) {
                newColumnName = newcolumnTextBox.getText();
                updateDatabase();
            }
            else{
                newcolumnStatus.setText("This column is existed please enter another name");
            }
        }
        else{
            newcolumnStatus.setText("Please enter the name of the column!");
        }
    }

    public void updateDatabase() throws Exception {
        Column newColumn = new Column(newColumnName);
        user.getProjects().get(project_name).addColumns(newColumn.getColumn_name(), newColumn);
        dbHelper.registerColumnDB(user.getUsername(), project_name, newColumnName);
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
}
