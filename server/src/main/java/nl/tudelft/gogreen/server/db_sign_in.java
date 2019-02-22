package nl.tudelft.gogreen.server;
import org.springframework.security.crypto.bcrypt.*;
import java.sql.*;
import java.util.Scanner;
public class create_user{
    public static void main(String[] args){
        try{

            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gogreen", "postgres", "pablo.rodrigo");
            Scanner scanner = new Scanner(System.in);
            PreparedStatement stmt0 = conn.prepareStatement("select user_id from user_table order by user_id desc limit 1;");
            ResultSet rs0 = stmt0.executeQuery();
            int id = 0;
            while(rs0.next()){
                id = rs0.getInt(1) + 1;
            }
            System.out.print("Insert your username: ");
            String username = scanner.next() ;
            int temp = 0;
            boolean availability = true;
            while(temp == 0) {
                PreparedStatement stmt = conn.prepareStatement("select username from user_table");
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    if(rs.getString(1).equals(username)) availability = false;
                }
                if(availability == false) {
                    System.out.print("The username introduced is not available, please enter a new one: ");
                    username = scanner.next();
                    availability = true;
                }
                else {
                    temp++;
                }
            }
            username = "'" + username + "'";
            System.out.print("Insert your password: ");
            String password = scanner.next();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            String hashpass = "'" +  hashed + "'";
            PreparedStatement stmt2 = conn.prepareStatement("insert into user_table values (" + id +", " + username + ", " + hashpass + ");");
            stmt2.execute();
            System.out.println("User created");
        }
        catch (Exception exception){
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }
    }
