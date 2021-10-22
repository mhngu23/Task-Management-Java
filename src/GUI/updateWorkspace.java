package GUI;

import Item.*;
import Others.SqliteConnector;
import Others.dbHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class updateWorkspace extends Window {
    public Others.dbHelper dbHelper = new dbHelper();
    public LinkedHashMap projects;
    public User user;
    public Connection connection = SqliteConnector.getConnector();
    @FXML
    public Button closeButton;

    public void closeButtonAction(ActionEvent event) throws Exception {
        /*
        Function to trigger the close button
         */
        Stage stage = (Stage) this.closeButton.getScene().getWindow();
        Workspace workspace = new Workspace();
        workspace.start(user, user.getProjects());
        stage.close();
    }
    public void accessWorkspace(String username, String password, String firstname, String lastname, byte[] profile_picture, ArrayList<String> projectList,  ArrayList<String> columnlist, ArrayList<String> taskList, Button button){
        User user = new User(username, password, firstname, lastname, profile_picture);
        setUpProject(user, projectList);
        LinkedHashMap<String, Project> projects = user.getProjects();
        setUpColumn(user, projects, columnlist);
        setUpTask(user, projects, columnlist, taskList);
        try {
            Workspace workspace = new Workspace();
            workspace.start(user, projects);
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addButtonToEmptyPane(AnchorPane pane, Button button, double right, double top){
        /*
        Function use to add button on en empty pane
         */
        pane.getChildren().add(button);
        pane.setRightAnchor(button, right);
        pane.setTopAnchor(button, top);
    }

    public void configureLabel(Label label, String name, Integer height, Integer width, Pos position, String text_size){
        /*
        Function used to configure the created Label
         */
        label.setText("   " + name);
        label.setAlignment(position);
        label.setPrefHeight(height);
        label.setPrefWidth(width);
        label.setStyle("-fx-border-color: grey;-fx-font-size: " + text_size);

    }


    public void configureButton(Button button, String text){
        /*
        Function used to configure the created Button
         */
        DropShadow shadow = new DropShadow();
        button.setBackground(null);
        button.setText(text);
        button.setStyle("-fx-text-fill: #6767d4");
        // Adding effect to click event
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setEffect(shadow);
                    }
                });
        // Adding effect to leave event
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        button.setEffect(null);
                    }
                });
        button.setUnderline(true);
        button.setPrefWidth(50);
        button.setPrefHeight(50);
    }

    public void setUpProject(User user, ArrayList<String> projectList){
        if (projectList.size()!=0){
            for (int counter = 0; counter < projectList.size(); counter += 3) {
                Project project = new Project( projectList.get(counter+1), projectList.get(counter), projectList.get(counter+2));
                user.addProject(project.getProject_name(), project);
            }
        }
    }

    public void setUpColumn(User user, LinkedHashMap<String, Project> projects, ArrayList<String> columnlist){
        if (columnlist.size()!=0){
            for (int counter = 0; counter < columnlist.size(); counter += 3) {
                Column column = new Column( columnlist.get(counter+2));
                for (String project_name: projects.keySet()){
                    if (project_name.equals(columnlist.get(counter+1))){
                        user.getProjects().get(project_name).addColumns(column.getColumn_name(), column);
                    }
                }
            }
        }
    }


    public void setUpTask(User user, LinkedHashMap<String, Project> projects, ArrayList<String> columnlist, ArrayList<String> taskList) {
        if (taskList.size()!=0) {
            for (int counter = 0; counter < taskList.size(); counter += 5) {
                Task task = new Task(taskList.get(counter + 3), taskList.get(counter + 4));
                for (String project_name : projects.keySet()) {
                    for (String column_name : user.getProjects().get(project_name).getColumns().keySet()) {
                        if (project_name.equals(taskList.get(counter + 1))) {
                            if (column_name.equals(taskList.get(counter + 2))) {
                                user.getProjects().get(project_name).getColumns().get(column_name).addTasks(task.getTask_name(), task);
                            }
                        }
                    }
                }
            }
        }
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setProjects(LinkedHashMap projects) {
        this.projects = projects;
    }


}
