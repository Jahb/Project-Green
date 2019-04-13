package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class APICFLTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }
    @Test
    public void CFLTest() {
        try {

            float save =  CoolClimateApi.cfl("100");
            float save2 =  35000;
            TestCase.assertEquals(save, save2);
            System.out.println(save);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}