package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class ApiFecthData2Test {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void apiFecthDataTest() {
        try {

            float result = CoolClimateApi.fetchApiData("Lower Temperature","4");
            float expected = Float.parseFloat("32");
            assertEquals(expected,result);

            float result2 =  CoolClimateApi.fetchApiData("Recycling", "3");
            float expected2 = 18;
            assertEquals(expected2, result2);

            float result3 = CoolClimateApi.fetchApiData("Usage of Bike", "20");
            float expected3 = Float.parseFloat("106.17402");
            assertEquals(result3,expected3);

            float result4 = CoolClimateApi.fetchApiData("Usage of Public Transport", "1");
            float expected4 = Float.parseFloat("43");
            TestCase.assertEquals(expected4, result4);

            float expected5 = Float.parseFloat("0.124721");
            float result5 = CoolClimateApi.VegetarianMeal("102");

            TestCase.assertEquals(expected5,result5);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}