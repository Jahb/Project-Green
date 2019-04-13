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

public class ZApiFecthDataTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void apiFecthDataTest() {

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul", "paul");


            float result = CoolClimateApi.fetchApiData("Solar Panels", null, 0);
            float expected = Float.parseFloat("461.9278");

            //TestCase.assertEquals((int)expected,(int) result);
            System.out.println(result);

            float result2 = CoolClimateApi.fetchApiData("Local Product", null, 0);

            float expected2 = Float.parseFloat("533.3333");
            //TestCase.assertEquals((int)expected2, (int) result2);


        } catch (Exception exception) {
            System.out.println("hey" + exception.getMessage());
        }
    }

}