package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APILowerTemperatureTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void LowerTemperature() {
        try {

            float result = CoolClimateApi.LowerTemperature("4");
            float expected = Float.parseFloat("32");
            assertEquals(expected,result);



        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}