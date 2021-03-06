package nl.tudelft.gogreen.server.auth;

import nl.tudelft.gogreen.server.Main;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;


public class LogIn {


    /**
     * log in method that gives access to the database to a user.
     */

    public static boolean log_in(String username, String password) throws SQLException {

        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        PreparedStatement stmt =
                conn.prepareStatement(Main.resource.getString("getPasswordByUsername"));
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return false;
        }
        conn.close();

        String dbpassword = rs.getString("password");

        return BCrypt.checkpw(password, dbpassword);
    }


}
