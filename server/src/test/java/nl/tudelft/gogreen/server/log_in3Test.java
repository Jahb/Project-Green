package nl.tudelft.gogreen.server;


import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertFalse;

public class log_in3Test {


    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void log_inTestNull() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"));){

            assertFalse(LogIn.log_in("brr","pa"));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error!");
        }
    }

}