package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ResourceBundle;

public class APIVegetarianMealTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void VegetarianMealTest() {
        try {

            float expected = Float.parseFloat("1.24721");
            float result = CoolClimateApi.VegetarianMeal("1020");

            TestCase.assertEquals((int) expected, (int) result);
            System.out.println(result);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}