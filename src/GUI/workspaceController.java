package GUI;

import Item.User;
import Item.ImageProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class workspaceController extends updateWorkspace implements Initializable {
    private ImageProcessor imageHandling = new ImageProcessor();
    ObservableList<String> empty_project_list = FXCollections.observableArrayList("New Project");
    ObservableList<String> project_list = FXCollections.observableArrayList("New Project");
    ObservableList<String> project_action_list = FXCollections.observableArrayList("Add Column", "Rename", "Set as default", "Unset default", "Delete");
    ObservableList<String> column_action_list = FXCollections.observableArrayList( "Add Task", "Rename", "Delete");
    @FXML
    private ComboBox workspaceComboBox;
    @FXML
    private ComboBox workspaceComboBox1;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab defaulttab;
    @FXML
    private Label workspaceStatus;
    @FXML
    private Label workspaceStatus1;
    @FXML
    private Button logOutButton;
    @FXML
    private Button imageButtonDisable;
    @FXML
    private Label labelUserName;
    @FXML
    private ImageView profileImage;

    public void initialize(URL location, ResourceBundle resources) {
        workspaceComboBox.setItems(empty_project_list);
        imageButtonDisable.setDisable(true);
    }

    public void logOutButtononAction(ActionEvent event) throws Exception {
        Main newMain = new Main();
        Stage primaryState = new Stage();
        newMain.start(primaryState);
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();
    }

    public void profileButtononAction(ActionEvent event) throws Exception {
        EditProfile editProfile = new EditProfile();
        editProfile.start(user, projects);
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();
    }

    public void updateWorkspaceInterface(LinkedHashMap projects) {
        if (!projects.isEmpty()) {
            for (Object project : projects.keySet()) {
                project_list.add((String) project);
            }
        }
        if (project_list.size() == 1) {
            // If new user enable prompts: workspaceStatus and workspaceStatus1
            defaulttab.setText("Empty Workspace");
            workspaceComboBox1.setVisible(false);
            workspaceStatus.setVisible(true);
            workspaceStatus1.setVisible(true);
        } else {
            workspaceComboBox1.setVisible(true);
            workspaceComboBox1.setItems(project_action_list);
            // If current user disable prompts: workspaceStatus and workspaceStatus1
            String default_project_name = user.displayDefaultProject() + "(*)";
            defaulttab.setText(default_project_name);
            addColumnOnTab(defaulttab, user.displayDefaultProject());
            workspaceStatus.setVisible(false);
            workspaceStatus1.setVisible(false);
            if (project_list.size() >= 2) {
                for (String project_nameFrom2 : project_list) {
                    if (!project_nameFrom2.equals(user.displayDefaultProject()) &&
                            !project_nameFrom2.equals("New Project")) {
                        Tab tab = new Tab(project_nameFrom2);
                        tabPane.getTabs().add(tab);
                        addColumnOnTab(tab, project_nameFrom2);
                    }
                }
            }
        }
    }

    public void addColumnOnTab(Tab tab, String project_name) {
        /*
        Function used to add column on to the current project tab
         */
        // scrollPane will store the gridPaneProject
        ScrollPane scrollPane = new ScrollPane();
        // gridPaneProject will store gridPaneColumn and gridPaneTab
        GridPane gridPaneProject = new GridPane();
        GridPane gridPaneColumn = new GridPane();
        GridPane gridPaneTab = new GridPane();
        for (int index = 0; index<user.getProjects().get(project_name).getColumns().size(); index++) {
            // Adding column name and column editing box on to the gridPaneColumn
            String column_name = (String) user.getProjects().get(project_name).getColumns().keySet().toArray()[index];
            // Configure the column label
            Label label = new Label();
            configureLabel(label, column_name, 25, 132, Pos.CENTER, "12px;");
            // configure the editing box for the label
            ComboBox labelEditingBox = new ComboBox(column_action_list);
            labelEditingBox.setValue("Edit Column");
            labelEditingBox.setOnAction(event -> {
                try {
                    doColumnAction(labelEditingBox.getValue().toString(), column_name, project_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            labelEditingBox.setPrefHeight(10);
            labelEditingBox.setPrefWidth(132);
            // Adding the label editing box and the label on to the gridPane with specific index position
            gridPaneColumn.add(labelEditingBox, index*2+1, 0);
            gridPaneColumn.add(label, index*2, 0);
            addTaskOnTab(gridPaneTab, project_name, column_name, index);
        }
        gridPaneProject.add(gridPaneTab,0 ,1);
        gridPaneProject.add(gridPaneColumn,0 ,0);
        scrollPane.setContent(gridPaneProject);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        tab.setContent(scrollPane);
    }

    public void addTaskOnTab( GridPane gridPaneTab, String project_name, String column_name, Integer column_index){
        /*
        Function used to add task on to the current tab and under the right column
         */
        for (int index = 0; index<user.getProjects().get(project_name).getColumns().get(column_name).getTasks().size(); index++) {
            String task_name = (String) user.getProjects().get(project_name).getColumns().get(column_name).getTasks().keySet().toArray()[index];
            // Configuring the label
            Label label = new Label();
            configureLabel(label, task_name, 100, 264, Pos.CENTER_LEFT, "13px;");
            Integer grid_index = index + 1;
            // Configuring edit button
            Button editButton = new Button();
            configureButton(editButton, "Edit");
//            editButton.setOnAction(event -> {
//                try {
//                    editTask(task_name, column_name, project_name);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
            // Configuring delete button
            Button deleteButton = new Button();
            configureButton(deleteButton, "Delete");
            deleteButton.setOnAction(event -> {
                try {
                    deleteTask(task_name, column_name, project_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // Configuring the AnchorPane
            AnchorPane pane = new AnchorPane();
            pane.getChildren().add(label);
            addButtonToEmptyPane(pane, editButton, 40d, 0d);
            addButtonToEmptyPane(pane, deleteButton, 0d, 0d);

            gridPaneTab.add(pane, column_index+1, grid_index);
        }
    }



    private void doColumnAction(String action, String column_name, String project_name) throws Exception {
        /*
        Function to choose the action you want to perform on the column
         */
        if (action.equals("Rename")) {
            renameColumn(column_name, project_name);
        }else if(action.equals("Delete")) {
            deleteColumn(column_name, project_name);
        }else if (action.equals("Add Task")){
            addTask(column_name, project_name);
        }
    }

    public void deleteTask(String task_name, String column_name, String project_name) throws Exception {
        dbHelper.deleteTask(user.getUsername(), task_name, project_name, column_name);
        user.getProjects().get(project_name).getColumns().get(column_name).deleteTask(task_name);
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.logOutButton);
        connection.close();
    }

    public void addTask(String column_name, String project_name) throws Exception {
        AddTask addTask = new AddTask();
        addTask.start(user, projects, project_name, column_name);
        Stage stage = (Stage) workspaceComboBox.getScene().getWindow();
        stage.close();
    }

    public void renameColumn(String column_name, String project_name) throws Exception {
        RenameColumn renameColumn = new RenameColumn();
        renameColumn.start(user, projects, project_name, column_name);
        Stage stage = (Stage) workspaceComboBox.getScene().getWindow();
        stage.close();
    }

    public void deleteColumn(String column_name, String project_name) throws Exception {
        dbHelper.deleteColumn(user, project_name, column_name);
        user.getProjects().get(project_name).deleteColumn(column_name);
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.logOutButton);
        connection.close();
    }

    public void workspaceComboBoxOnAction1(ActionEvent event) throws Exception {
        /*
         */
        if (workspaceComboBox1.getValue() == "Rename"){
            renameProject();
        }
        else if (workspaceComboBox1.getValue() == "Set as default"){
            setProjectAsDefault();
        }
        else if (workspaceComboBox1.getValue() == "Delete"){
            deleteProject();
        }
        else if (workspaceComboBox1.getValue() == "Add Column"){
            addColumn();
        }

    }

    public void workspaceComboBoxOnAction(ActionEvent event) throws Exception {
        /*
        Function to handle workspaceComboBox action.
        --> "New Project": call Newproject Window
        --> "Empty Workspace": Remove current project, display Empty Workspace and prompt workspaceStatus2
        --> Others: display the project selected.
         */
        if (workspaceComboBox.getValue() == "New Project"){
            NewProject newproject = new NewProject();
            newproject.start(projects, user);
            Stage stage = (Stage) workspaceComboBox.getScene().getWindow();
            stage.close();
        }
    }

    public void addColumn() throws Exception {
        AddColumn addColumn = new AddColumn();
        String project_name = getCurrentTabName();
        addColumn.start(user, projects, project_name);
        Stage stage = (Stage) workspaceComboBox.getScene().getWindow();
        stage.close();
    }

    public void renameProject() throws Exception {
        RenameProject renameProject = new RenameProject();
        String project_name = getCurrentTabName();
        renameProject.start(user, projects, project_name);
        Stage stage = (Stage) workspaceComboBox.getScene().getWindow();
        stage.close();
    }

    public void setProjectAsDefault() throws Exception {
        String project_name = getCurrentTabName();
        user.setDefaultProject(project_name);
        dbHelper.updateDefaultProject(user.getProjects(), user);
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.logOutButton);

        connection.close();
    }

    public void deleteProject() throws Exception {
        String project_name = getCurrentTabName();
        dbHelper.deleteProject(user, project_name);
        user.deleteProject(project_name);
        dbHelper.updateDefaultProject(user.getProjects(), user);
        String [] userinfo = dbHelper.getUserData(this.connection, user.getUsername());
        byte[] profile_picture = dbHelper.getUserProfilePicture(this.connection, user.getUsername());
        ArrayList<String> projectlist = dbHelper.getProjectData(this.connection, userinfo[0]);
        ArrayList<String> columnlist = dbHelper.getColumnData(this.connection,  userinfo[0]);
        ArrayList<String> taskList = dbHelper.getTaskData(this.connection, userinfo[0]);
        accessWorkspace(userinfo[0], userinfo[1], userinfo[2],userinfo[3], profile_picture, projectlist, columnlist, taskList, this.logOutButton);
        connection.close();
    }

    public String getCurrentTabName(){
        String project_name = tabPane.getSelectionModel().getSelectedItem().getText();
        Integer activeTabIndex = tabPane.getSelectionModel().getSelectedIndex();
        if (activeTabIndex == 0){
            project_name = project_name.substring(0, project_name.length() - 3);
        }
        return project_name;
    }

    public void setProfileImage(User user) throws IOException {
        this.profileImage.setImage(this.imageHandling.profileImageProcessing(user));
    }
    public void setLabelUserName(User user){
        this.labelUserName.setText(user.getUsername());
    }

}
