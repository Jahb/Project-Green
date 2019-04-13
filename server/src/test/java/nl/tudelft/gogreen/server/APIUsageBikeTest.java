package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APIUsageBikeTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }
    @Test
    public void UsageBikeTest() throws Exception {

            float result = CoolClimateApi.usageofBike("20");
            float expected = Float.parseFloat("106.17402");
            assertEquals(result,expected);
            System.out.println(result);

    }

}