package GUI;

import Item.User;
import Item.ImageProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class editProfileController extends updateWorkspace implements Initializable {
    private FileChooser fileChooser;
    private File file;
    private final ImageProcessor imageHandling = new ImageProcessor();
    @FXML
    private ImageView profileImage;
    @FXML
    private Label labelUserName;
    @FXML
    private TextField firstnameRegisterField;
    @FXML
    private TextField lastnameRegisterField;
    @FXML
    private Label registerMessage;
    @FXML
    private Button confirmButton;


    @Override
    public void initialize(URL location, ResourceBundle resources){
    }

    public void closeButtonAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        String username = userinfo[0]; String password = userinfo[1]; String firstname  = userinfo[2]; String lastname = userinfo[3];
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, username);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.closeButton);
        connection.close();
    }

    public void updateUserButtonAction(ActionEvent event) throws Exception {
        if (!firstnameRegisterField.getText().isEmpty() && !lastnameRegisterField.getText().isEmpty()) {
            byte[] image = imageHandling.imageToByteArray(file);
            if (image == null){
                image = user.getProfile_picture();
            }
            dbHelper.updateUserDB(user.getUsername() ,firstnameRegisterField.getText(), lastnameRegisterField.getText(), image);
            String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
            byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
            ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
            ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
            ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
            accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.closeButton);
            connection.close();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } else {
            registerMessage.setText("Please enter all required information!");
        }
    }

    public void insertImageButtonAction(){
        /*
        Function to trigger Image Button --> Result in updating the profileImage
         */
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Field", "*.jpg", "*.png", "*.gif")
        );
        file = fileChooser.showOpenDialog(this);
        if (file != null){
            Image image = new Image(file.toURI().toString(), 120, 120, true ,true);
            profileImage.setImage(image);
            profileImage.setFitWidth(120);
            profileImage.setFitHeight(120);
            profileImage.setPreserveRatio(true);
        }
    }



    public void setProfileImage(User user) throws IOException {
        this.profileImage.setImage(imageHandling.profileImageProcessing(user));
    }

    public void setLabelUserName(User user){
        this.labelUserName.setText(user.getUsername());
    }

    public void setCurrentFirstName(User user){
        this.firstnameRegisterField.setText(user.getFirst_name());
    }

    public void setCurrentLastName(User user){
        this.lastnameRegisterField.setText(user.getLast_name());
    }
}
