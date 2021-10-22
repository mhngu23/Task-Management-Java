package GUI;

import Item.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class Workspace {
    public void start(User user, LinkedHashMap projects) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/workspace.fxml"));
        Parent root = (Parent) loader.load();
        workspaceController newWorkspaceController = loader.getController();
        newWorkspaceController.setProjects(projects);
        newWorkspaceController.setUser(user);
        newWorkspaceController.setLabelUserName(user);
        newWorkspaceController.updateWorkspaceInterface(projects);
        newWorkspaceController.setLabelUserName(user);
        newWorkspaceController.setProfileImage(user);
        Stage workspaceStage = new Stage();
        workspaceStage.setResizable(false);
        workspaceStage.setTitle("Smart Board");
        workspaceStage.setScene(new Scene(root));
        workspaceStage.show();
    }
}