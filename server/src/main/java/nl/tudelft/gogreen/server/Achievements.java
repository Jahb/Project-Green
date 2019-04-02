package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class Achievements {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void addAchievement(int id, String achievementName) throws Exception {

            Connection conn = DriverManager.getConnection(
                    resource.getString("Postgresql.datasource.url"),
                    resource.getString("Postgresql.datasource.username"),
                    resource.getString("Postgresql.datasource.password"));

            PreparedStatement add = conn.prepareStatement("insert into achievements values(?,?)");
            add.setInt(1,id);
            add.setString(2,achievementName);
            add.execute();


    }
    public static void deleteAchievement(int id, String achievementName)  throws  Exception{
            Connection conn = DriverManager.getConnection(
                    resource.getString("Postgresql.datasource.url"),
                    resource.getString("Postgresql.datasource.username"),
                    resource.getString("Postgresql.datasource.password"));

            PreparedStatement delete = conn.prepareStatement("delete from achievements where user_id = ?");
            delete.setInt(1,id);
            delete.execute();


    }
}
