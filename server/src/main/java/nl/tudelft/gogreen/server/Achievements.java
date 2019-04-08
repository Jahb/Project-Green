package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Achievements {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void addAchievement(int id, int achievementid) throws Exception {

            Connection conn = DriverManager.getConnection(
                    resource.getString("Postgresql.datasource.url"),
                    resource.getString("Postgresql.datasource.username"),
                    resource.getString("Postgresql.datasource.password"));

            PreparedStatement add = conn.prepareStatement("insert into achievements values(?,?)");
            add.setInt(1,id);
            add.setInt(2,achievementid);
            add.execute();


    }
    public static void deleteAchievement(int id, int achievementid)  throws  Exception{
            Connection conn = DriverManager.getConnection(
                    resource.getString("Postgresql.datasource.url"),
                    resource.getString("Postgresql.datasource.username"),
                    resource.getString("Postgresql.datasource.password"));

            PreparedStatement delete = conn.prepareStatement("delete from achievements where user_id = ? and achievement_id = ?");
            delete.setInt(1,id);
            delete.setInt(2,achievementid);
            delete.execute();

    }

    public static ArrayList<String> showAllAchievements() throws Exception {
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        PreparedStatement show = conn.prepareStatement("select achievement_name from achievements_names");
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = show.executeQuery();
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }

    public static ArrayList<Integer> showAchievementsForUser(int id)  throws  Exception{
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        PreparedStatement show = conn.prepareStatement("select achievement_id from achievements where user_id = ?");
        show.setInt(1,id);
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = show.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt(1));
        }
        return result;
    }
}
