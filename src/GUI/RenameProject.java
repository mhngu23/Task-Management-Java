package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class RenameProject {
    public void start(User user, LinkedHashMap projects, String projectName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/renameproject.fxml"));
        Parent root = (Parent) loader.load();
        Stage newRenameProjectStage = new Stage();
        renameProjectController renameProjectController = loader.getController();
        renameProjectController.setUser(user);
        renameProjectController.setProjects(projects);
        renameProjectController.setCurrent_project_name(projectName);
        newRenameProjectStage.setScene(new Scene(root));
        newRenameProjectStage.setResizable(false);
        newRenameProjectStage.setTitle("Edit project name");
        newRenameProjectStage.show();
    }
}
