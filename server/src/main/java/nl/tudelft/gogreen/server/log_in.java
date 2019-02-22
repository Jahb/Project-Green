package nl.tudelft.gogreen.server;
import org.springframework.security.crypto.bcrypt.*;
import java.sql.*;
import java.util.Scanner;
public class log_in {

    public static void log_in(String username) {

        try{
            System.out.println("Im in log in page");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gogreen", "postgres", "pablo.rodrigo");
            Scanner scanner = new Scanner(System.in);
            String password = null;
            PreparedStatement stmt = conn.prepareStatement("select * from user_table");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                if(rs.getString(2).equals(username)) {
                    password = rs.getString(3);

                }
            }
            if(password == null){
                System.out.print("You are not registered, click this button to sign in!");
                create_user.createuser(username);
            }
            else {

                while(true) {
                    System.out.print("Insert your password: ");
                    String dbpassword = scanner.next();
                    boolean pass = BCrypt.checkpw(dbpassword, password);


                    if(pass) {
                        System.out.print("You are in!");
                        return;
                    }
                    else {
                        System.out.print("Wrong!Try again!");

                    }
                }
            }
        }
        catch (Exception exception){
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
    }

}
