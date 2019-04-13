package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APIRecyclingTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void RecyclingTest() {
        try {

           float result =  CoolClimateApi.recycling("3");
            float expected = 18;
            assertEquals(expected, result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}