package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APIUsageBikeTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void UsageBikeTest() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            float result = CoolClimateApi.UsageofBike("20");
            float expected = Float.parseFloat("106.17402");
            assertEquals(result,expected);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}