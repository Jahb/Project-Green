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
        PreparedStatement stmt = conn.prepareStatement(resource.getString("getPasswordByUsername"));
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return false;
        }


        String dbpassword = rs.getString("password");

        return BCrypt.checkpw(password, dbpassword);
    }


}
