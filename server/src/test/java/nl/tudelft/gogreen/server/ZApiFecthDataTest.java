package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

public class ZApiFecthDataTest {


    @Test
    public void apiFecthDataTest() {
        try {


            float result = CoolClimateApi.fetchApiData("Solar Panels");
            float expected = Float.parseFloat("461.9278");

            TestCase.assertEquals((int)expected,(int) result);
            System.out.println(result);

            float result2 =  CoolClimateApi.fetchApiData("Local Product");
            float expected2 = Float.parseFloat("533.3333");
            TestCase.assertEquals((int)expected2, (int) result2);



        } catch (Exception exception) {
            System.out.println("hey" + exception.getMessage());
        }
    }

}