package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class ApiFecthDataTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void apiFecthDataTest() {
        try {

            float result = CoolClimateApi.fetchApiData("Solar Panels");
            float expected = Float.parseFloat("461.9278");

            assertEquals(expected,result);
            System.out.println(result);

            float result2 =  CoolClimateApi.fetchApiData("Local Product");
            float expected2 = Float.parseFloat("533.3333");
            TestCase.assertEquals(expected2, result2);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}