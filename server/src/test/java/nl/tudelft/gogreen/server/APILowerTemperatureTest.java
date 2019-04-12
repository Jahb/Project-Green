package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APILowerTemperatureTest {

    @Before
    public void fix() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void LowerTemperature() {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul", "paul");
            PreparedStatement p = conn.prepareStatement(Main.resource.getString("qQuizData"));
            p.setInt(1, NewFeature.getId("paul", conn));
            p.setInt(2, 10);
            p.setInt(3, 10);
            p.setBoolean(4, true);
            p.setInt(5, 10);
            p.setInt(6, 10);
            p.execute();
            float result = CoolClimateApi.LowerTemperature("4", 0);
            float expected = Float.parseFloat("32");
            assertEquals((int) expected, (int) result);

            CoolClimateApi.keysremapping();

            float result2 = CoolClimateApi.fetchApiData("Lower Temperature", "4", 0);
            float expected2 = Float.parseFloat("32");
            assertEquals((int) expected2, (int) result2);


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}