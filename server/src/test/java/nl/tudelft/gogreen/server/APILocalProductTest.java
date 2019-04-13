package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class APILocalProductTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void LocalProductTest() {
        try {

            float result =  CoolClimateApi.localProduct();
            float expected = Float.parseFloat("533.3333");
            TestCase.assertEquals(expected, result);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}