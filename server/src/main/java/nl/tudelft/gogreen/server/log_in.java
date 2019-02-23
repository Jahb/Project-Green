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
            System.out.print("Enter your username: ");
            String username = scanner.next();
            String password = null;
            PreparedStatement stmt = conn.prepareStatement(resource.getString("getAllUsers"));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                if (rs.getString(2).equals(username)) {
                    password = rs.getString(3);

                }
            }

            if (password == null) {
                System.out.print("You are not registered, click this button to sign in!");
                create_user.create_user();
            } else {
                boolean loop1 = true;
                while (loop1) {
                    System.out.print("Insert your password: ");
                    String dbpassword = scanner.next();
                    boolean pass = BCrypt.checkpw(dbpassword, password);


                    if (pass) {
                        System.out.println("You are in!");
                        return username;
                    } else {
                        System.out.print("Wrong!Try again!");

                    }
                }

            }
        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
