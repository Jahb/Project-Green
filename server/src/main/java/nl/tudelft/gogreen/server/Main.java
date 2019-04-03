package nl.tudelft.gogreen.server;

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