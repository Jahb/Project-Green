package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Following {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * Method for following someone.
     * @param id1 id of user1
     * @param id2 id of user2
     * @throws Exception raises exception if unable to access database
     */
    public static void follow(int id1, int id2) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        if (!isFollowing(id1, id2, conn)) {
            PreparedStatement follow = conn.prepareStatement(resource.getString("qFollow"));
            follow.setInt(1, id1);
            follow.setInt(2, id2);
            follow.execute();
        }
        conn.close();

    }

    /**
     * method for user1 to unfollow user 2.
     * @param id1 id for user 1
     * @param id2 if for user 2
     * @throws Exception raises exception if unable to access database
     */
    public static void unfollow(int id1, int id2) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        if (isFollowing(id1, id2, conn)) {
            PreparedStatement unfollow = conn.prepareStatement(resource.getString("qUnfollow"));
            unfollow.setInt(1, id1);
            unfollow.setInt(2, id2);
            unfollow.execute();
        }
        conn.close();
    }

    /**
     * Method that checks if user1 is following user2.
     * @param id1 id for user1
     * @param id2 id for user2
     * @param conn the connection
     * @return returns true if user1 is following user2 or false otherwise
     * @throws Exception raises exception if unable to access database
     */
    public static boolean isFollowing(int id1, int id2, Connection conn) throws Exception {

        PreparedStatement isFollowing = conn.prepareStatement(resource.getString("qIsFollowing"));
        isFollowing.setInt(1, id1);
        isFollowing.setInt(2, id2);
        int count = -1;
        ResultSet counting = isFollowing.executeQuery();
        while (counting.next()) {
            count = counting.getInt(1);
        }
        System.out.println(count);
        return count == 1;


    }

    /**
     * Method deletes all the following relations of user1.
     * @param id1 id of user
     * @param conn connection to database
     * @throws Exception raises exception if unable to access database
     */
    public static void deleteAllFollows(int id1, Connection conn) throws Exception {

        PreparedStatement delFollowers = conn.prepareStatement(resource.getString("qDelFollowing"));
        delFollowers.setInt(1, id1);
        delFollowers.setInt(2, id1);
        delFollowers.execute();
    }

    /**
     * Shows a list of all the people a user follows.
     * @param id1 id of the user
     * @param conn connection to database
     * @return returns a list of id's which user follows
     * @throws Exception raises exception if unable to access database
     */
    public static ArrayList<Integer> showAllFollowing(int id1, Connection conn) throws Exception {

        PreparedStatement showFollowing =
                conn.prepareStatement(resource.getString("qShowFollowing"));
        showFollowing.setInt(1, id1);
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = showFollowing.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt(1));
        }
        return result;
    }

    /**
     * Shows a list of all the people that follow a user.
     * @param id1 user's id
     * @param conn connection to database
     * @return returns a list of id's of people who follow the user
     * @throws Exception raises exception if unable to access database
     */
    public static ArrayList<Integer> showAllFollowers(int id1, Connection conn) throws Exception {

        PreparedStatement showFollowers =
                conn.prepareStatement(resource.getString("qShowFollowers"));
        showFollowers.setInt(1, id1);
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = showFollowers.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt(1));
        }
        return result;
    }

    public static int countAllFollowing(int id1, Connection conn) throws Exception {
        return showAllFollowing(id1, conn).size();
    }

    public static int countAllFollowers(int id1, Connection conn) throws Exception {
        return showAllFollowers(id1, conn).size();
    }

    static String toUsername(int id, Connection conn) {
        PreparedStatement getUsername;
        try {
            getUsername = conn.prepareStatement(resource.getString("getUsernameById"));
            getUsername.setInt(1, id);
            ResultSet res = getUsername.executeQuery();
            res.next();
            return res.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * returns a list of all users.
     * @return returns the list
     * @throws Exception raises exception if unable to access database
     */
    public static List<String> gettingAllUsers() throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        List<String> users = new ArrayList<String>();
        PreparedStatement getUsers = conn.prepareStatement("select username from user_table");
        ResultSet result = getUsers.executeQuery();
        while (result.next()) {
            users.add(result.getString(1));
        }
        return users;
    }

}
