package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Test;

public class APICFLTest {
    @Test
    public void CFLTest() {
        try {

            float save =  CoolClimateApi.CFL("100");
            float save2 =  35000;
            TestCase.assertEquals(save, save2);
            System.out.println(save);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}