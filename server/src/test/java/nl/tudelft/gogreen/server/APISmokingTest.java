package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class APISmokingTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }


    @Test
    public void LocalProductTest() {
        try {

            float save =  CoolClimateApi.smoking("100");
            float save2 = (float) 60.000004;
            TestCase.assertEquals(save, save2);
            System.out.println(save);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}