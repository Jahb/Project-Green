package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingUserLogTest {



    @Test
    public void actualizingUserPoints(){
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("MJ", conn);


            System.out.println("MJ -----");
            NewFeature.actualizingUserLog(id, "Vegetarian Meal", 20, conn);
            System.out.println("1st done");
            int oldTotal = getTotal(id,  conn);
            System.out.println("2nd done");
            NewFeature.actualizingUserLog(id, "Vegetarian Meal", 20, conn);
            int newTotal = getTotal(id,  conn);

            assertNotEquals(oldTotal, newTotal);
            oldTotal += 20;
            assertEquals(oldTotal, newTotal);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before
    public void createUsers() throws Exception {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

            CreateUser.create_user("MJ","MJ");




    }

    @After
    public void deleteUser() {
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("MJ",conn),conn);

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


