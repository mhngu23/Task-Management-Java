package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class NewProject {
    public void start(LinkedHashMap projects, User user) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newproject.fxml"));
        Parent root = (Parent) loader.load();
        Stage newProjectStage = new Stage();
        newProjectController newProjectController = loader.getController();
        newProjectController.setProjects(projects);
        newProjectController.setUser(user);
        newProjectStage.setScene(new Scene(root));
        newProjectStage.setTitle("Create new project");
        newProjectStage.setResizable(false);
        newProjectStage.show();
    }
}
