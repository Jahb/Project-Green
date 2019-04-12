package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Test;

import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class AAPISolarPanelsTest {


    @Test
    public void SolarPanels() {
        try {

            float result = CoolClimateApi.SolarPanels();
            float expected = Float.parseFloat("461.9278");
            assertEquals(expected, result);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}