package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

public class APISolarPanelsTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void UsageBikeTest() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            CoolClimateApi.SolarPanels();
            PreparedStatement solar = conn.prepareStatement(resource.getString("qSolarPanels"));
            ResultSet rs = solar.executeQuery();
            float result = 0;
            while (rs.next()) {
                result = rs.getFloat(1);
            }
            assertEquals(result,1425.7097,1425.7097);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}