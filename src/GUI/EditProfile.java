package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class EditProfile {
    public void start(User user, LinkedHashMap projects) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editprofile.fxml"));
        Parent root = (Parent) loader.load();
        Stage newEditProfileStage = new Stage();
        editProfileController newEditProfileController = loader.getController();
        newEditProfileController.setUser(user);
        newEditProfileController.setProjects(projects);
        newEditProfileController.setLabelUserName(user);
        newEditProfileController.setCurrentFirstName(user);
        newEditProfileController.setCurrentLastName(user);
        newEditProfileController.setProfileImage(user);
        newEditProfileStage.setScene(new Scene(root));
        newEditProfileStage.setTitle("Edit profile");
        newEditProfileStage.setResizable(false);
        newEditProfileStage.show();
    }
}
