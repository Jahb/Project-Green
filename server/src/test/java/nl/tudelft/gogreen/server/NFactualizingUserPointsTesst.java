package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingUserPointsTesst {



    @Test
    public void actualizingUserLog(){
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("MJ", conn);
            NewFeature.actualizingUserPoints(id, "Vegetarian Meal", 20, conn);
            int oldTotal = NewFeature.getTotal("MJ");
            NewFeature.actualizingUserPoints(id, "Vegetarian Meal", 20, conn);
            int newTotal = NewFeature.getTotal("MJ");

            NewFeature.actualizingUserPoints(id, "Usage of Bike", 20, conn);
            int oldTotal2 = NewFeature.getTotal("MJ");
            NewFeature.actualizingUserPoints(id, "Usage of Bike", 20, conn);
            int newTotal2 = NewFeature.getTotal("MJ");

            NewFeature.actualizingUserPoints(id, "Lower Temperature", 20, conn);
            int oldTotal3 = NewFeature.getTotal("MJ");
            NewFeature.actualizingUserPoints(id, "Lower Temperature", 20, conn);
            int newTotal3 = NewFeature.getTotal("MJ");

            NewFeature.actualizingUserPoints(id, "Recycling", 20, conn);
            int oldTotal4 = NewFeature.getTotal("MJ");
            NewFeature.actualizingUserPoints(id, "Recycling", 20, conn);
            int newTotal4 = NewFeature.getTotal("MJ");

            assertNotEquals(oldTotal, newTotal);
            assertNotEquals(oldTotal2, newTotal2);
            assertNotEquals(oldTotal3, newTotal3);
            assertNotEquals(oldTotal4, newTotal4);
            oldTotal += 20;
            oldTotal2 += 20;
            oldTotal3 += 20;
            oldTotal4 += 20;
            assertEquals(oldTotal, newTotal);
            assertEquals(oldTotal2, newTotal2);
            assertEquals(oldTotal3, newTotal3);
            assertEquals(oldTotal4, newTotal4);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before
    public void createOnlyUser() throws Exception {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        CreateUser.create_user("MJ","MJ");



    }

    @After
    public void deleteUser() throws Exception{
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("MJ",conn),conn);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



}