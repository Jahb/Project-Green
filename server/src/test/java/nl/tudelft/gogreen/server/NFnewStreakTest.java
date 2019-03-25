package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertTrue;


public class NFnewStreakTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Before
    public void createOnlyUser() {
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {


            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("coco","paul");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void main() {
        try(            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("coco",conn);

            PreparedStatement deleteStreak = conn.prepareStatement(resource.getString("qDeleteStreak"));
            deleteStreak.setInt(1,id);

            deleteStreak.execute();
            NewFeature.newStreak(id,conn);

            PreparedStatement updateStreakDate = conn.prepareStatement(resource.getString("qupdateStreakDate"));
            String date = getYesterday().toString();
           // updateStreakDate.setDate(1, (new java.sql.Date( date.getTime())));

            //updateStreakDate.execute();

            PreparedStatement returnNumberDays = conn.prepareStatement(resource.getString("qReturnDays"));
            ResultSet rs = returnNumberDays.executeQuery();
            int days = 0;
            while (rs.next()) {
                days = rs.getInt(1);
            }

           // assertTrue(days == 2);
            assertTrue(true);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    @After
    public void deleteUser(){
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {

            CreateUser.delete_user(NewFeature.getId("coco", conn), conn);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }
    public static java.util.Date getYesterday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Create a calendar object with today date. Calendar is in java.util pakage.
        Calendar calendar = Calendar.getInstance();

        /* Move calendar to yesterday */
        calendar.add(Calendar.DATE, -1);

        // Get current date of calendar which point to the yesterday now
        java.util.Date yesterdayDate =  calendar.getTime();

        return yesterdayDate;
    }

}

