package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class APIVegetarianMealTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void VegetarianMealTest() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            CoolClimateAPI.VegetarianMeal("1");
            PreparedStatement vegetarian = conn.prepareStatement(resource.getString("qVegetarianMeal"));
            ResultSet rs = vegetarian.executeQuery();
            float result = 0;
            while (rs.next()) {
                result = rs.getFloat(1);
            }
            assertEquals(result,698.3132,698.3132);
            System.out.println(result);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}