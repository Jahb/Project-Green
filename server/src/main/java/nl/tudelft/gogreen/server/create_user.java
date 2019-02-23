package nl.tudelft.gogreen.server;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class create_user {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void create_user() {
        try {
            System.out.println("Im create user page");

            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.next();
            PreparedStatement stmt = conn.prepareStatement(resource.getString("getAllUsers"));
            ResultSet rs = stmt.executeQuery();
            String password2 = null;
            while (rs.next()) {

                if (rs.getString(2).equals(username)) password2 = rs.getString(3);
            }
            if (password2 != null) {
                System.out.println("You are already registered, click this button to log in!");
                log_in.log_in();

            } else {
                PreparedStatement stmt0 = conn.prepareStatement(resource.getString("getMaxId"));
                ResultSet rs0 = stmt0.executeQuery();
                int id = 0;
                while (rs0.next()) {
                    id = rs0.getInt(1) + 1;
                }

                username = "'" + username + "'";
                System.out.print("Insert your password: ");
                String password = scanner.next();
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                String hashpass = "'" + hashed + "'";

                PreparedStatement user = conn.prepareStatement(resource.getString("user_table"));
                user.execute();
                //PreparedStatement obj = conn.prepareStatement(resource.getString("objective"));
                //obj.execute();
                //PreparedStatement hab1 = conn.prepareStatement(resource.getString("hab1"));
                //hab1.execute();
                //PreparedStatement hab2 = conn.prepareStatement(resource.getString("hab2"));
                //hab2.execute();
                //PreparedStatement hab3 = conn.prepareStatement(resource.getString("hab3"));
                //hab3.execute();
                //PreparedStatement hab4 = conn.prepareStatement(resource.getString("hab4"));
                //hab4.execute();
                //PreparedStatement streak = conn.prepareStatement(resource.getString("streak"));
                //streak.execute();
                //PreparedStatement userpoints = conn.prepareStatement(resource.getString("user_points"));
                //userpoints.execute();
                System.out.println("User created");
            }
        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
    }
}
