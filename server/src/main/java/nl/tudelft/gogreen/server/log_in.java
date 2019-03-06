package nl.tudelft.gogreen.server;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;


public class log_in {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * log in method that gives access to the database to a user.
     */

    public static String log_in() {

        try {


            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            Scanner scanner = new Scanner(System.in);
            boolean loop = false;
            boolean loged = false;
            String username = null;
            int intents = 3;
           

        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
        return null;
    }
    public static boolean  log_in(String username, String password,  Connection conn) {
        try {


            PreparedStatement stmt = conn.prepareStatement(resource.getString("getAllUsers"));
            ResultSet rs = stmt.executeQuery();
            String dbpassword = null;
            while (rs.next()) {

                if (rs.getString(2).equals(username)) {
                    dbpassword = rs.getString(3);
                }
            }
            boolean access = BCrypt.checkpw(password, dbpassword);

            if (dbpassword == null) {
                System.out.print("You are not registered, click this button to sign in!");
                create_user.create_user();
                return false;
            }
            return access;
        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
        return false;
    }

}
