package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ResourceBundle;

public class APIUsagePublicTransportTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void UsagePublicTransport() {
        try {

            float result = CoolClimateApi.UsageofPublicTransport("1");
            float expected = Float.parseFloat("43");
            TestCase.assertEquals(expected, result);

            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}