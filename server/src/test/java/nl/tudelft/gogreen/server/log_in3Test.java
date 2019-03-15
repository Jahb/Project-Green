package nl.tudelft.gogreen.server;


import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertFalse;

public class log_in3Test {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Test
    public void log_inTestNull() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            assertFalse(LogIn.log_in("brr","pa"));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error!");
        }
    }

}