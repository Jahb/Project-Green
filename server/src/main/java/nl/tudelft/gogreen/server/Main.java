package nl.tudelft.gogreen.server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

@SpringBootApplication
public class Main {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        SpringApplication.run(Main.class, args);
<<<<<<< Updated upstream
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        CreateUser.create_user("vlad", "vladyy");
=======
        CreateUser.create_user("paul","password");
        int id = NewFeature.getId("paul",conn);
        EventsMain.create_event("paul",id,"karnaval",conn);
>>>>>>> Stashed changes
    }
}