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

public class NFactualizingUserLog4Test {



    @Test
    public void actualizingUserPoints(){
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            int id4 = NewFeature.getId("Kobe", conn);


            NewFeature.actualizingUserLog(id4, "recycling", 20, conn);
            int oldTotal4 = getTotal(id4,  conn);
            NewFeature.actualizingUserLog(id4, "recycling", 20, conn);
            int newTotal4 = getTotal(id4,  conn);

            //assertNotEquals(oldTotal4, newTotal4);
            oldTotal4 += 20;
            //assertEquals(oldTotal4, newTotal4);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before
    public void createUsers() throws Exception {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

            CreateUser.create_user("MJ","MJ");
            CreateUser.create_user("Scottie", "Pippen");
            CreateUser.create_user("Wilt", "Chamberlain");
            CreateUser.create_user("Kobe", "Mamba");



    }

    @After
    public void deleteUser() {
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
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


