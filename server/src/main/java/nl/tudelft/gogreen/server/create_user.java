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

                username = "'" + username + "'";
                System.out.print("Insert your password: ");
                String password = scanner.next();
                boolean created = false;
                created = create_user(username,password,conn);
                System.out.println("User created");
            }
        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
    }
    public static boolean create_user( String username, String password, Connection conn){
        try{
            PreparedStatement stmt0 = conn.prepareStatement(resource.getString("getMaxId"));
            ResultSet rs0 = stmt0.executeQuery();
            int id = 0;
            while (rs0.next()) {
                id = rs0.getInt(1) + 1;
            }

            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            String hashpass = "'" + hashed + "'";

            PreparedStatement user = conn.prepareStatement("insert into user_table values ( " +  id + ", " + username + ", " + hashpass + ");");
            user.execute();
            PreparedStatement obj = conn.prepareStatement("insert into objective values (" + id + ", NULL);");
            obj.execute();
            PreparedStatement hab1 = conn.prepareStatement("insert into initial_habits values ( "+ id + ", 'smoke', FALSE);" );
            hab1.execute();
            PreparedStatement hab2 = conn.prepareStatement("insert into initial_habits values (" + id + ", 'recycling person', FALSE); ");
            hab2.execute();
            PreparedStatement hab3 = conn.prepareStatement("insert into initial_habits values("+ id + ", 'use of recycle paper', FALSE);");
            hab3.execute();
            PreparedStatement hab4 = conn.prepareStatement("insert into initial_habits values (" + id + ", 'eco-friendly clothes usage', FALSE);" );
            hab4.execute();
            PreparedStatement streak = conn.prepareStatement("insert into streak values (" + id + ", current_date  ,   1  );");
            streak.execute();
            PreparedStatement userpoints = conn.prepareStatement("insert into user_points values (" + id + ",  0  ,   0  ,   0  ,   0  ,   0  );");
            userpoints.execute();
        }
        catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
        return false;

    }
}
