package nl.tudelft.gogreen.server;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class APIVegetarianMealTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void VegetarianMealTest() throws Exception{
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul", "paul");

            float result = CoolClimateApi.VegetarianMeal("1020",NewFeature.getId("paul", conn));


                float expected = Float.parseFloat("1.24721");

                TestCase.assertEquals((int) expected, (int) result);
                System.out.println(result);

            } catch(Exception exception){
                System.out.println("Error!");
            }
        }

    }