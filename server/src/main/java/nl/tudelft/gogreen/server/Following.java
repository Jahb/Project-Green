package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Following {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void Follow(int id1, int id2) throws Exception {

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

    }

    public static void Unfollow(int id1, int id2) throws Exception {

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

    }

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
        return (count == 1);


    }

    public static void deleteAllFollows(int id1, Connection conn) throws Exception {

        PreparedStatement delFollowers = conn.prepareStatement(resource.getString("qDelFollowing"));
        delFollowers.setInt(1, id1);
        delFollowers.setInt(2, id1);
        delFollowers.execute();
    }

    public static void showAllFollowing(int id1, Connection conn) throws Exception {

        PreparedStatement showFollowing = conn.prepareStatement(resource.getString("qShowFollowing"));
        showFollowing.setInt(1, id1);
        showFollowing.execute();
    }
    

}
