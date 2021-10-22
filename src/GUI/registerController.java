package GUI;

import Others.SqliteConnector;
import Item.ImageProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;

public class registerController extends updateWorkspace implements Initializable {
    private FileChooser fileChooser;
    private File file;
    Connection connection = SqliteConnector.getConnector();
    private final ImageProcessor imageHandling = new ImageProcessor();
    @FXML
    private Button closeButton;
    @FXML
    private Label registerMessage;
    @FXML
    private TextField usernameRegisterField;
    @FXML
    private PasswordField passwordRegisterField;
    @FXML
    private TextField firstnameRegisterField;
    @FXML
    private TextField lastnameRegisterField;
    @FXML
    private ImageView profileImage;


    public void initialize(URL location, ResourceBundle resources){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath() +  "\\resource_folder\\empty-profile-picture-png-2-2.png";
        try {
            InputStream stream = new FileInputStream(s);
            Image image = new Image(stream);
            profileImage.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void closeButtonAction(ActionEvent event) throws SQLException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        connection.close();
        stage.close();
        Main main = new Main();
        Stage primaryStage = new Stage();
        try {
            main.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void registerButtonAction(ActionEvent event) throws SQLException {
        if(dbHelper.checkUserNameNotAvailable(usernameRegisterField.getText())) {
            if (!usernameRegisterField.getText().isEmpty() && !passwordRegisterField.getText().isEmpty() && !firstnameRegisterField.getText().isEmpty() && !lastnameRegisterField.getText().isEmpty()) {
                byte[] image = imageHandling.imageToByteArray(file);
                if (image == null){
                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath() +  "\\resource_folder\\empty-profile-picture-png-2-2.png";
                    File file = new File(s);
                    image = this.imageHandling.imageToByteArray(file);
                }
                dbHelper.registerUser(usernameRegisterField.getText(), passwordRegisterField.getText(), firstnameRegisterField.getText(), lastnameRegisterField.getText(), image);
                Stage stage = (Stage) closeButton.getScene().getWindow();
                connection.close();
                stage.close();
                Main main = new Main();
                Stage primaryStage = new Stage();
                try {
                    main.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                registerMessage.setText("Please enter all required information!");
            }
        }
        else{
            registerMessage.setText("Username has already been used. Please select another one!");
        }
    }




}
