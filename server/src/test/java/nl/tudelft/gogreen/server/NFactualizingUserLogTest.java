package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingUserLogTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void actualizingUserPoints(){
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("MJ", conn);
            int id2 = NewFeature.getId("Scottie", conn);
            int id3 = NewFeature.getId("Wilt", conn);
            int id4 = NewFeature.getId("Kobe", conn);

            NewFeature.actualizingUserLog(id, "Vegetarian Meal", 20, conn);
            int oldTotal = getTotal(id,  conn);
            NewFeature.actualizingUserLog(id, "Vegetarian Meal", 20, conn);
            int newTotal = getTotal(id,  conn);

            NewFeature.actualizingUserLog(id2, "Usage of Bike", 20, conn);
            int oldTotal2 = getTotal(id2,  conn);
            NewFeature.actualizingUserLog(id2, "Usage of Bike", 20, conn);
            int newTotal2 = getTotal(id2,  conn);

            NewFeature.actualizingUserLog(id3, "Lower Temperature", 20, conn);
            int oldTotal3 =getTotal(id3,  conn);
            NewFeature.actualizingUserLog(id3, "Lower Temperature", 20, conn);
            int newTotal3 = getTotal(id3,  conn);

            NewFeature.actualizingUserLog(id4, "Recycling", 20, conn);
            int oldTotal4 = getTotal(id4,  conn);
            NewFeature.actualizingUserLog(id4, "Recycling", 20, conn);
            int newTotal4 = getTotal(id4,  conn);

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
    public void createUsers() throws Exception {

            CreateUser.create_user("MJ","MJ");
            CreateUser.create_user("Scottie", "Pippen");
            CreateUser.create_user("Wilt", "Chamberlain");
            CreateUser.create_user("Kobe", "Mamba");



    }

    @After
    public void deleteUser() {
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("MJ",conn),conn);
            CreateUser.delete_user(NewFeature.getId("Scottie",conn), conn);
            CreateUser.delete_user(NewFeature.getId("Wilt",conn),conn);
            CreateUser.delete_user(NewFeature.getId("Kobe",conn), conn);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static int getTotal(int id, Connection conn) {
        int total = -1;
        try {
            java.sql.Date date  =  getCurrentDatetime();
            PreparedStatement getTotal = conn.prepareStatement("select total from user_history where user_id = ? and date = ?");
            getTotal.setInt(1,id);
            getTotal.setDate(2, date );
            ResultSet rs = getTotal.executeQuery();

            while(rs.next()){
                total = rs.getInt(1);
            }
            return total;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return total;
        }

    }
    public static java.sql.Date getCurrentDatetime() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
}


