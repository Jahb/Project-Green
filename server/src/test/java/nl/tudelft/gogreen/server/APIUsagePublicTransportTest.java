package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class APIUsagePublicTransportTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }
    @Test
    public void UsagePublicTransport() {
        try {

            float result = CoolClimateApi.usageofPublicTransport("1");
            float expected = Float.parseFloat("43");
            TestCase.assertEquals(expected, result);

            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}