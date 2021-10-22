package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Register{
    public void start() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/register.fxml")));
        Stage registerStage = new Stage();
        registerStage.setTitle("Create a new user");
        registerStage.setScene(new Scene(root, 400, 444));
        registerStage.setResizable(false);
        registerStage.show();
    }

}
