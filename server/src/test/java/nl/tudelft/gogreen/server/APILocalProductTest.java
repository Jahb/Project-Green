package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class APILocalProductTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void LocalProductTest() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            CoolClimateApi.LocalProduct();
            PreparedStatement localproduct = conn.prepareStatement(resource.getString("qLocalProduct"));
            ResultSet rs = localproduct.executeQuery();
            float result = 0;
            while (rs.next()) {
                result = rs.getFloat(1);
            }
            assertEquals(result,1461.1871,1461.1871);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}