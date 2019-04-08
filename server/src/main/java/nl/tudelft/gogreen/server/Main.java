package nl.tudelft.gogreen.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

@SpringBootApplication
public class Main {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        //SpringApplication.run(Main.class, args);

        CoolClimateAPI.UsageofPublicTransport("1");
    }
}