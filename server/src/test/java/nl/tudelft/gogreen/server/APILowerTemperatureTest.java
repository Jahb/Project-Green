package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APILowerTemperatureTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void LowerTemperature() {
        try(  Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul","paul");
            PreparedStatement p = conn.prepareStatement(resource.getString("qQuizData"));
            p.setInt(1, NewFeature.getId("paul",conn));
            p.setInt(2, 10);
            p.setInt(3,10);
            p.setBoolean(4, true);
            p.setInt(5,10);
            p.setInt(6,10);
            p.execute();
            float result = CoolClimateApi.LowerTemperature("4",NewFeature.getId("paul",conn));
            float expected = Float.parseFloat("32");
            assertEquals( (int) expected, (int)result);

            CoolClimateApi.keysremapping();

            float result2 = CoolClimateApi.fetchApiData("Lower Temperature","4", NewFeature.getId("paul",conn));
            float expected2 = Float.parseFloat("32");
            assertEquals((int) expected2,(int)result2);


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}