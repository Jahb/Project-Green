package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Test;

public class APIUsagePublicTransportTest {


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