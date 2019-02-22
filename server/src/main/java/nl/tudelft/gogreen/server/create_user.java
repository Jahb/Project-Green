package nl.tudelft.gogreen.server;
import org.springframework.security.crypto.bcrypt.*;
import java.sql.*;
import java.util.Scanner;
public class create_user {
    public static void createuser(String username) {
        try {
            System.out.println("Im create user page");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gogreen", "postgres", "pablo.rodrigo");

            Scanner scanner = new Scanner(System.in);
            PreparedStatement stmt0 = conn.prepareStatement("select user_id from user_table order by user_id desc limit 1;");
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
            PreparedStatement user = conn.prepareStatement("insert into user_table values (" + id + ", " + username + ", " + hashpass + ");");
            user.execute();
            PreparedStatement obj = conn.prepareStatement("insert into objective values (" + id + ", NULL); ");
            obj.execute();
            PreparedStatement hab1 = conn.prepareStatement("insert into initial_habits values (" + id + ", 'smoke', FALSE); ");
            hab1.execute();
            PreparedStatement hab2 = conn.prepareStatement("insert into initial_habits values (" + id + ", 'recycling person', FALSE); ");
            hab2.execute();
            PreparedStatement hab3 = conn.prepareStatement("insert into initial_habits values (" + id + ", 'use of recycle paper', FALSE); ");
            hab3.execute();
            PreparedStatement hab4 = conn.prepareStatement("insert into initial_habits values (" + id + ", 'eco-friendly clothes usage', FALSE); ");
            hab4.execute();
            PreparedStatement streak = conn.prepareStatement("insert into streak values (" + id + ", " + "current_date" + ", " + 0 + ");");
            streak.execute();
            PreparedStatement userpoints = conn.prepareStatement("insert into user_points values (" + id + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
            userpoints.execute();
            System.out.println("User created");
        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
    }
}
