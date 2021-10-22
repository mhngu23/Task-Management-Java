package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class AddColumn {
    public void start(User user, LinkedHashMap projects, String project_name) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addcolumn.fxml"));
        Parent root = (Parent) loader.load();
        Stage addColumnStage = new Stage();
        addColumnController addColumnController = loader.getController();
        addColumnController.setProjects(projects);
        addColumnController.setUser(user);
        addColumnController.setProject_name(project_name);
        addColumnStage.setScene(new Scene(root));
        addColumnStage.setTitle("Add new column to" + " " + project_name);
        addColumnStage.setResizable(false);
        addColumnStage.show();
    }
}
