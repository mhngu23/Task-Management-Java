package Others;

import java.sql.*;

public class databaseConnectorCheck {
    Connection connection;
    public databaseConnectorCheck(){
        connection = SqliteConnector.getConnector();
        if (connection == null){
            System.exit(1);
        }
    }

    public static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        return resultSet.next();
    }

    public static void checkTableExist(Connection connection) throws SQLException{
        if (!tableExists(connection, "userinfo")){
            createUserInfoTable(connection);
        };
        if (!tableExists(connection, "projectlist")){
            createProjectListTable(connection);
        };
        if (!tableExists(connection, "columnlist")){
            createColumnListTable(connection);
        };
        if (!tableExists(connection, "tasklist")){
            createTaskListTable(connection);
        };
    }

    private static void createUserInfoTable(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS userinfo " +
                "(username TEXT not null unique, " +
                "password TEXT not null," +
                " firstname TEXT, " +
                " lastname TEXT, " +
                " image BLOB, " +
                " PRIMARY KEY ( username ))";
        stmt.executeUpdate(sql);
    }

    private static void createProjectListTable(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS projectlist " +
                "(username TEXT not null, " +
                "projectname TEXT not null," +
                "projectstatus TEXT not null)";
        stmt.executeUpdate(sql);
    }

    private static void createColumnListTable(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS columnlist " +
                "(username TEXT not null, " +
                "projectname TEXT not null," +
                "columnname TEXT not null)";
        stmt.executeUpdate(sql);
    }

    private static void createTaskListTable(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS tasklist " +
                "(username TEXT not null, " +
                "projectname TEXT not null," +
                "columnname TEXT not null," +
                "taskname TEXT not null," +
                "taskdescription TEXT)";
        stmt.executeUpdate(sql);
    }

}
