package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class APIRecyclingTest {



    @Test
    public void RecyclingTest() {
        try {

           float result =  CoolClimateApi.Recycling("3");
            float expected = 18;
            assertEquals(expected, result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}