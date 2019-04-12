package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class ZApiFecthDataTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void apiFecthDataTest() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul","paul");

            float result = CoolClimateApi.SolarPanels(NewFeature.getId("paul",conn));


            float expected = Float.parseFloat("461.9278");

            TestCase.assertEquals((int)expected,(int) result);
            System.out.println(result);

            float result2 =  CoolClimateApi.fetchApiData("Local Product",NewFeature.getId("paul",conn));
            float expected2 = Float.parseFloat("533.3333");
            TestCase.assertEquals((int)expected2, (int) result2);



        } catch (Exception exception) {
            System.out.println("hey" + exception.getMessage());
        }
    }

}