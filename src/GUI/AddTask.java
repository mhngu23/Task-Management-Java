package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class AddTask {
    public void start(User user, LinkedHashMap projects, String project_name, String column_name) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addtask.fxml"));
        Parent root = (Parent) loader.load();
        Stage addTaskStage = new Stage();
        addTaskController addTaskController = loader.getController();
        addTaskController.setProjects(projects);
        addTaskController.setUser(user);
        addTaskController.setProject_name(project_name);
        addTaskController.setColumn_name(column_name);
        addTaskStage.setScene(new Scene(root));
        addTaskStage.setTitle("Edit Task");
        addTaskStage.setResizable(false);
        addTaskStage.show();
    }
}
