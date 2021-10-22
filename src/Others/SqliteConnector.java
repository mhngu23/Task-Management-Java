package Others;

import java.sql.*;

public class SqliteConnector<connector> {

    public static Connection getConnector(){
        try{
            Class.forName("org.sqlite.JDBC");
            String dataBaseName = "userinfoDB.db";
            String url = "jdbc:sqlite:" + dataBaseName;
            return DriverManager.getConnection(url);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
            return null;
        }
    }


}
