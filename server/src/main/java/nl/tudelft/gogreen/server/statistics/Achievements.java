package nl.tudelft.gogreen.server.statistics;

import nl.tudelft.gogreen.server.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Achievements {


    /**
     * addAchievement for an user.
     * @param id id is the user id
     * @param achievementid achievement id
     * @throws Exception raises when error accessing the database
     */
    public static void addAchievement(int id, int achievementid) throws Exception {

        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement add = conn.prepareStatement(Main.resource.getString("qAddAchievement"));
        add.setInt(1, id);
        add.setInt(2, achievementid);
        add.execute();


    }

    /**
     * Delete an achievement of an user.
     * @param id id of the user
     * @param achievementid achievements id
     * @throws Exception raises when error accessing the database
     */
    public static void deleteAchievement(int id, int achievementid) throws Exception {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement delete = conn.prepareStatement(Main.resource.getString("qDeleteAchievement"));
        delete.setInt(1, id);
        delete.setInt(2, achievementid);
        delete.execute();

    }

    /**
     * Shows the names of all achievements in the table.
     * @return returns a list with all the achievements names
     * @throws Exception raises error when unable to access database
     */
    public static ArrayList<String> showAllAchievements() throws Exception {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement show = conn.prepareStatement(Main.resource.getString("qSelectName"));
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = show.executeQuery();
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }

    /**
     * Show the list of achievements for a user.
     * @param id the user's id
     * @return returns the list of achievement names
     * @throws Exception raises error when unable to access database
     */
    public static ArrayList<Integer> showAchievementsForUser(int id) throws Exception {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement show = conn.prepareStatement(Main.resource.getString("qShowAchievement"));
        show.setInt(1, id);
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = show.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt(1));
        }
        return result;
    }
}
