package nl.tudelft.gogreen.server;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingUserPointsTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void actualizingUserPoints(){
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("MJ", conn);
            NewFeature.actualizingUserPoints(id, "Vegetarian Meal", 20, conn);
            int oldTotal = NewFeature.getTotal("MJ");
            NewFeature.actualizingUserPoints(id, "Vegetarian Meal", 20, conn);
            int newTotal = NewFeature.getTotal("MJ");

            assertNotEquals(oldTotal, newTotal);
            oldTotal += 20;
            assertEquals(oldTotal, newTotal);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before
    public void createOnlyUser() throws Exception {

            CreateUser.create_user("MJ","MJ");



    }


}