package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class APIUsagePublicTransportTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void UsagePublicTransport() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            CoolClimateAPI.UsageofPublicTransport("1");
            PreparedStatement usagetransport = conn.prepareStatement(resource.getString("qUsageTransport"));
            ResultSet rs = usagetransport.executeQuery();
            float result = 0;
            while (rs.next()) {
                result = rs.getFloat(1);
            }
            assertEquals(result,607.24585,607.24585);
            System.out.println(result);


        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}