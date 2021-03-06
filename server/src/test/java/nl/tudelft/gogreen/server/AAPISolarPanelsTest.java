package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class AAPISolarPanelsTest {
    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void SolarPanels() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul","paul");

            float result = CoolClimateApi.solarPanels(NewFeature.getId("paul",conn));
            float expected = Float.parseFloat("461.9278");
            assertEquals(expected, result, expected);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}