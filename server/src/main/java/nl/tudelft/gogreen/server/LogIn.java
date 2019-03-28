package nl.tudelft.gogreen.server;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class LogIn {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * log in method that gives access to the database to a user.
     */

    public static boolean log_in(String username, String password) throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        PreparedStatement stmt = conn.prepareStatement(resource.getString("getAllUsers"));
        ResultSet rs = stmt.executeQuery();
        String dbpassword = null;
        while (rs.next()) {

            if (rs.getString(2).equals(username)) {
                dbpassword = rs.getString(3);
            }
        }
        System.out.println("the password");

        if (dbpassword == null) {
            System.out.print("You are not registered, click this button to sign in!");
            return false;
        }
        return BCrypt.checkpw(password, dbpassword);
    }

}
