package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

public class APILocalProductTest {



    @Test
    public void LocalProductTest() {
        try {

            float result =  CoolClimateApi.LocalProduct();
            float expected = Float.parseFloat("533.3333");
            TestCase.assertEquals(expected, result);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}