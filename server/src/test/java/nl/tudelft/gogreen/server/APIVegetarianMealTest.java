package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import nl.tudelft.gogreen.server.api.CoolClimateApi;
import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

public class APIVegetarianMealTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }
    @Test
    public void VegetarianMealTest() throws Exception{
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul", "paul");

            float result = CoolClimateApi.VegetarianMeal("1020", NewFeature.getId("paul", conn));


                float expected = Float.parseFloat("1.24721");

                TestCase.assertEquals((int) expected, (int) result);
                System.out.println(result);

            } catch(Exception exception){
                System.out.println("Error!");
            }
        }

    }