package nl.tudelft.gogreen.server;
import org.springframework.security.crypto.bcrypt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class db_main {
    public static void initial_menu(String username){
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gogreen", "postgres", "pablo.rodrigo");
            PreparedStatement stmt = conn.prepareStatement("select * from user_table");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                if (rs.getString(2).equals(username)) {
                    log_in.log_in(username);
                    return;
                }
            }

            create_user.createuser(username);


        }
        catch (Exception exception){
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }

    }
}
