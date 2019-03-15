package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingFeaturesTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void actualizingFeaturesTest() throws Exception {

        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        int previous = getAccess(conn);
        NewFeature.actualizingFeatures(conn,"Vegetarian Meal");
        int actual = getAccess(conn);
        assertNotEquals(previous,actual);
        actual = actual- 1;
        assertEquals(previous,actual);

    }
    @After
    public void restoreAccesses() throws Exception{


            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                    resource.getString("Postgresql.datasource.username"),
                    resource.getString("Postgresql.datasource.password"));
            PreparedStatement getId = conn.prepareStatement("update features " +
                    "set access = access - 1 where feature_name = 'Vegetarian Meal' ;");
            getId.execute();


    }

    public static int getAccess(Connection conn) throws  Exception{
        PreparedStatement access = conn.prepareStatement(resource.getString("getAccesses"));
        ResultSet accessNumber = access.executeQuery();
        int number = -1;
        while (accessNumber.next()){
            number = accessNumber.getInt(1);
        }
        return number;
    }

}