package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APIUsageBikeTest {


    @Test
    public void UsageBikeTest() throws Exception {

            float result = CoolClimateApi.UsageofBike("20");
            float expected = Float.parseFloat("106.17402");
            assertEquals(result,expected);
            System.out.println(result);

    }

}