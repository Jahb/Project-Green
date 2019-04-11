package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

public class APISmokingTest {

    @Test
    public void LocalProductTest() {
        try {

            float save =  CoolClimateApi.Smoking("100");
            float save2 = (float) 60.000004;
            TestCase.assertEquals(save, save2);
            System.out.println(save);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}