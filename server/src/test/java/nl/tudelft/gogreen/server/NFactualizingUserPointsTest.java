package nl.tudelft.gogreen.server;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingUserPointsTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void actualizingUserPoints() throws Exception{
        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        int id = NewFeature.getId("MJ",conn);
        NewFeature.actualizingUserPoints(id,"Vegetarian Meal", 20, conn);
        int oldTotal = getTotal(id,conn);
        NewFeature.actualizingUserPoints(id,"Vegetarian Meal", 20, conn);
        int newTotal = getTotal(id,conn);

        assertNotEquals(oldTotal,newTotal);
        oldTotal += 20;
        assertEquals(oldTotal,newTotal);

    }

    @Before
    public void createOnlyUser() throws Exception {

            CreateUser.create_user("MJ","MJ");



    }

    public static int getTotal(int id, Connection conn) throws  Exception{
        PreparedStatement OldUserPoints = conn.prepareStatement(resource.getString("qgetTotalUP"));
        OldUserPoints.setInt(1,id);
        ResultSet OUP = OldUserPoints.executeQuery();
        int total = -1;
        while (OUP.next()){
            total = OUP.getInt(1);
        }
        return total;
    }
}