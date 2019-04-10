package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class APIfetchTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void UsageBikeTest() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            CoolClimateApi.fetchApiData(null,null);
            PreparedStatement fetch = conn.prepareStatement(resource.getString("qVegetarianMeal"));
            ResultSet rs = fetch.executeQuery();
            float result = 0;
            while (rs.next()) {
                result = rs.getFloat(1);
            }
            assertEquals(result,698.3132,698.3132);

            PreparedStatement fetch2 = conn.prepareStatement(resource.getString("qLocalProduct"));
            ResultSet rs2 = fetch2.executeQuery();
            float result2 = 0;
            while (rs2.next()) {
                result2 = rs2.getFloat(1);
            }
            assertEquals(result2,1461.1871,1461.1871);
            System.out.print(result2);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}