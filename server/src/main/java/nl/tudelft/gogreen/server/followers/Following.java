package nl.tudelft.gogreen.server.followers;

import nl.tudelft.gogreen.server.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Following {



    public static void Follow(int id1, int id2) throws Exception {

        Connection conn = getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        if (!isFollowing(id1, id2, conn)) {
            PreparedStatement follow = conn.prepareStatement(Main.resource.getString("qFollow"));
            follow.setInt(1, id1);
            follow.setInt(2, id2);
            follow.execute();
        }
        conn.close();

    }

    public static void Unfollow(int id1, int id2) throws Exception {

        Connection conn = getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        if (isFollowing(id1, id2, conn)) {
            PreparedStatement unfollow = conn.prepareStatement(Main.resource.getString("qUnfollow"));
            unfollow.setInt(1, id1);
            unfollow.setInt(2, id2);
            unfollow.execute();
        }
        conn.close();
    }

    public static boolean isFollowing(int id1, int id2, Connection conn) throws Exception {

        PreparedStatement isFollowing = conn.prepareStatement(Main.resource.getString("qIsFollowing"));
        isFollowing.setInt(1, id1);
        isFollowing.setInt(2, id2);
        int count = -1;
        ResultSet counting = isFollowing.executeQuery();
        while (counting.next()) {
            count = counting.getInt(1);
        }
        System.out.println(count);
        return (count == 1);


    }

    public static void deleteAllFollows(int id1, Connection conn) throws Exception {

        PreparedStatement delFollowers = conn.prepareStatement(Main.resource.getString("qDelFollowing"));
        delFollowers.setInt(1, id1);
        delFollowers.setInt(2, id1);
        delFollowers.execute();
    }

    public static ArrayList<Integer> showAllFollowing(int id1, Connection conn) throws Exception {

        PreparedStatement showFollowing = conn.prepareStatement(Main.resource.getString("qShowFollowing"));
        showFollowing.setInt(1, id1);
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = showFollowing.executeQuery();
        while (rs.next()) {
            result.add(rs.getInt(1));
        }
        return result;
    }

    public static ArrayList<Integer> showAllFollowers(int id1, Connection conn) throws Exception {

        PreparedStatement showFollowers = conn.prepareStatement(Main.resource.getString("qShowFollowers"));
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
            getUsername = conn.prepareStatement(Main.resource.getString("getUsernameById"));
            getUsername.setInt(1, id);
            ResultSet res = getUsername.executeQuery();
            res.next();
            return res.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }

}