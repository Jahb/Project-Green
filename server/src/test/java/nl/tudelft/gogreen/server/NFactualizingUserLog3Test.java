package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFactualizingUserLog3Test {


    @Test
    public void actualizingUserPoints(){
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            int id3 = NewFeature.getId("Wilt", conn);



            NewFeature.actualizingUserLog(id3, "Lower Temperature", 20, conn);
            int oldTotal3 =getTotal(id3,  conn);
            NewFeature.actualizingUserLog(id3, "Lower Temperature", 20, conn);
            int newTotal3 = getTotal(id3,  conn);



            assertNotEquals(oldTotal3, newTotal3);
            oldTotal3 += 20;
            assertEquals(oldTotal3, newTotal3);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before
    public void createUsers() throws Exception {

        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

            CreateUser.create_user("Wilt", "Chamberlain");




    }

    @After
    public void deleteUser() {
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            CreateUser.delete_user(NewFeature.getId("Wilt",conn),conn);

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static int getTotal(int id, Connection conn) {
        int total = -1;
        try {
            Date date  =  getCurrentDatetime();
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
    public static Date getCurrentDatetime() {
        java.util.Date today = new java.util.Date();
        return new Date(today.getTime());
    }
}


