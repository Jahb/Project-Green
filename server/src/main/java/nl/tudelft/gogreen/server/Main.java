package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ResourceBundle;

@SpringBootApplication
public class Main {
    public static ResourceBundle resource = ResourceBundle.getBundle("db");


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        CoolClimateAPI.fetchApiData();
    }
}