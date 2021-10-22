package Others;

import Item.Project;
import Item.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class dbHelper {
    Connection connection = SqliteConnector.getConnector();


    public String[] getUserData(Connection connection, String text) throws SQLException {
    /*
    Function to return the user data from database after log in was processed
     */
        String query = "SELECT username, password, firstname, lastname, image FROM userinfo";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getString("username").equals(text)) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String[] userinfo = {userName, password, firstName, lastName};
                return userinfo;
            }
        }
        return new String[0];
    }

    public ArrayList<String> getProjectData(Connection connection, String text) throws SQLException {
    /*
    Function to return the project data from database after log in was processed
     */
        String query = "SELECT username, projectname, projectstatus FROM projectlist";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> projectLists = new ArrayList<String>();
        while (resultSet.next()) {
            if (resultSet.getString("username").equals(text)) {
                String username = resultSet.getString("username");
                String projectName = resultSet.getString("projectname");
                String projectStatus = resultSet.getString("projectstatus");
                String[] projectInfo = {username, projectName, projectStatus};
                Collections.addAll(projectLists, projectInfo);
            }
        }
        return projectLists;
    }

    public ArrayList<String> getColumnData(Connection connection, String text) throws SQLException {
    /*
    Function to return the project data from database after log in was processed
     */
        String query = "SELECT username, projectname, columnname FROM columnlist";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> columnLists = new ArrayList<String>();
        while (resultSet.next()) {
            if (resultSet.getString("username").equals(text)) {
                String userName = resultSet.getString("username");
                String projectName = resultSet.getString("projectname");
                String columnName = resultSet.getString("columnname");
                String[] columnInfo = {userName, projectName, columnName};
                Collections.addAll(columnLists, columnInfo);
            }
        }
        return columnLists;
    }

    public ArrayList<String> getTaskData(Connection connection, String text) throws SQLException {
    /*
    Function to return the project data from database after log in was processed
     */
        String query = "SELECT username, projectname, columnname, taskname, taskdescription FROM tasklist";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> taskLists = new ArrayList<String>();
        while (resultSet.next()) {
            if (resultSet.getString("username").equals(text)) {
                String userName = resultSet.getString("username");
                String projectName = resultSet.getString("projectname");
                String columnName = resultSet.getString("columnname");
                String taskName = resultSet.getString("taskname");
                String taskDescription = resultSet.getString("taskdescription");
                String[] taskInfo = {userName, projectName, columnName, taskName, taskDescription};
                Collections.addAll(taskLists, taskInfo);
            }
        }
        return taskLists;
    }

    public byte[] getUserProfilePicture(Connection connection, String text) throws SQLException {
    /*
    Function to return the profile picture data from database after log in was processed
     */
        String query = "SELECT username, image FROM userinfo";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getString("username").equals(text)) {
                byte[] profile_picture = resultSet.getBytes("image");
                return profile_picture;
            }
        }
        return new byte[0];
    }
    public boolean validateLogin(String username, String password) throws SQLException {
        String query = "SELECT * FROM userinfo WHERE username = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet queryResult = null;
        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2 , password);
            queryResult = statement.executeQuery();
            return queryResult.next();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }finally {
            statement.close();
            queryResult.close();
        }
        return false;
    }

    public void updateDefaultProject(LinkedHashMap<String, Project> projects, User user){
        String query = "UPDATE projectlist SET projectstatus=? WHERE projectname=?";
        try{
            for (String project : projects.keySet()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user.getProjects().get(project).getStatus());
                statement.setString(2, project);
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteProject(User user, String project_name){
        for (String column_name : user.getProjects().get(project_name).getColumns().keySet()){
            deleteColumn(user, project_name, column_name);
        }
        String query = "DELETE FROM projectlist WHERE projectname = ? AND username = ?";
        try{ PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, project_name);
            statement.setString(2, user.getUsername());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteColumn(User user, String project_name, String column_name){
        for (String task_name : user.getProjects().get(project_name).getColumns().keySet()){
            deleteTask(user.getUsername(), task_name, project_name, column_name);
        }
        String query = "DELETE FROM columnlist WHERE columnname = ? AND projectname = ? AND username = ?";
        try{ PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, column_name);
            statement.setString(2, project_name);
            statement.setString(3, user.getUsername());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteTask(String username, String task_name, String project_name, String column_name){
        String query = "DELETE FROM tasklist WHERE taskname = ? AND projectname = ? AND columnname  = ? AND username = ?";
        try{ PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task_name);
            statement.setString(2, project_name);
            statement.setString(3, column_name);
            statement.setString(4, username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateUserDB(String username, String firstname, String lastname, byte[] image){
        String query = "UPDATE userinfo SET firstname=?, lastname=?, image=? WHERE username=?";
        try{
            PreparedStatement statement  = connection.prepareStatement(query);
            statement.setString(1, firstname);
            statement.setString(2 , lastname);
            statement.setBytes(3, image);
            statement.setString(4 , username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void renameColumnDB(String old_columnname, String new_columnname){
        String query = "UPDATE columnlist SET columnname=? WHERE columnname=?";
        try{
            PreparedStatement statement  = connection.prepareStatement(query);
            statement.setString(1, new_columnname);
            statement.setString(2, old_columnname);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void renameProjectDB(String old_projectname, String new_projectname){
        String query = "UPDATE projectlist SET projectname=? WHERE projectname=?";
        try{
            PreparedStatement statement  = connection.prepareStatement(query);
            statement.setString(1, new_projectname);
            statement.setString(2, old_projectname);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registerUser(String username, String password, String firstname, String lastname, byte[] image){
        String query = "INSERT INTO userinfo(username, password, firstname, lastname, image) VALUES(?,?,?,?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2 , password);
            statement.setString(3, firstname);
            statement.setString(4 , lastname);
            statement.setBytes(5, image);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registerProjectDB(String username, String projectName, String projectStatus){
        String query = "INSERT INTO projectlist(username, projectname, projectstatus) VALUES(?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2 , projectName);
            statement.setString(3 , projectStatus);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registerColumnDB(String username, String projectName, String columnName){
        String query = "INSERT INTO columnlist(username, projectname, columnname) VALUES(?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2 , projectName);
            statement.setString(3 , columnName);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registerTaskDB(String username, String projectName, String columnName, String taskName, String taskDescription){
        String query = "INSERT INTO tasklist(username, projectname, columnname, taskname, taskdescription) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2 , projectName);
            statement.setString(3 , columnName);
            statement.setString(4 , taskName);
            statement.setString(5 , taskDescription);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkUserNameNotAvailable(String text) throws SQLException {
        /*
        Function to check if username is already available
         */
        String query = "SELECT username FROM userinfo";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            if (resultSet.getString("username").equals(text)){
                return false;
            }
        }
        return true;
    }


}
