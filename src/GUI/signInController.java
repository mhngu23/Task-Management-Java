package GUI;

import Others.SqliteConnector;
import Others.databaseConnectorCheck;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class signInController extends updateWorkspace implements Initializable{
    public databaseConnectorCheck databaseConnectorChecker = new databaseConnectorCheck();
    Connection connection = SqliteConnector.getConnector();
    @FXML
    private Button closeButton;
    @FXML
    private Label signInMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            databaseConnectorCheck.checkTableExist(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeButtonAction(ActionEvent event) throws SQLException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        connection.close();
        stage.close();
    }

    public void signInButtonAction(ActionEvent event)  {
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            try {
                if (dbHelper.validateLogin(usernameTextField.getText(), passwordTextField.getText())){
                    signInMessage.setText("Correct");
                    String [] userinfo = dbHelper.getUserData(this.connection, usernameTextField.getText());
                    byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, usernameTextField.getText());
                    ArrayList<String> projectList = dbHelper.getProjectData(this.connection, usernameTextField.getText());
                    ArrayList<String> columnList = dbHelper.getColumnData(this.connection, usernameTextField.getText());
                    ArrayList<String> taskList = dbHelper.getTaskData(this.connection, usernameTextField.getText());
                    connection.close();
                    accessWorkspace(userinfo[0], userinfo[1], userinfo[2], userinfo[3], profile_picture, projectList, columnList, taskList, this.closeButton);
                }
                else{
                    signInMessage.setText("Incorrect username or password. Please re-enter!");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            signInMessage.setText("Please enter your username and password!");
        }
    }

    public void createNewUserAction(){
        createRegisterPage();
    }

    public void createRegisterPage(){
        Register register = new Register();
        try {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            connection.close();
            stage.close();
            register.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
