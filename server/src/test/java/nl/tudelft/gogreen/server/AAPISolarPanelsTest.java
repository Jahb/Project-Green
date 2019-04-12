package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class AAPISolarPanelsTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void SolarPanels() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul","paul");

            float result = CoolClimateApi.SolarPanels(NewFeature.getId("paul",conn));
            float expected = Float.parseFloat("461.9278");
            assertEquals(expected, result);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}