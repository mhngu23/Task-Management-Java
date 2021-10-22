package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class RenameColumn {
    public void start(User user, LinkedHashMap projects, String projectName, String columnName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/renamecolumn.fxml"));
        Parent root = (Parent) loader.load();
        Stage newRenameColumnStage = new Stage();
        renameColumnController renameColumnController = loader.getController();
        renameColumnController.setUser(user);
        renameColumnController.setProjects(projects);
        renameColumnController.setProject_name(projectName);
        renameColumnController.setCurrent_column_name(columnName);
        newRenameColumnStage.setScene(new Scene(root));
        newRenameColumnStage.setResizable(false);
        newRenameColumnStage.setTitle("Edit column name");
        newRenameColumnStage.show();
    }
}
