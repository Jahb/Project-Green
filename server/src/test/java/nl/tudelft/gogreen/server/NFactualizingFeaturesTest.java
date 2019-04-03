package nl.tudelft.gogreen.server;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingFeaturesTest {



    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void actualizingFeaturesTest() {

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int previous = getAccess(conn);
            NewFeature.actualizingFeatures(conn, "Vegetarian Meal");
            int actual = getAccess(conn);
            assertNotEquals(previous, actual);
            actual = actual - 1;
            assertEquals(previous, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getAccess(Connection conn) throws Exception {
        PreparedStatement access = conn.prepareStatement(Main.resource.getString("getAccesses"));
        ResultSet accessNumber = access.executeQuery();
        int number = -1;
        while (accessNumber.next()) {
            number = accessNumber.getInt(1);
        }
        return number;
    }

}