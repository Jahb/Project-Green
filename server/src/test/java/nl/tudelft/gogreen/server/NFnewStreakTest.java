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
        try( Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {

            int id = NewFeature.getId("coco",conn);



            PreparedStatement updateStreakDate = conn.prepareStatement(resource.getString("qInsertStreakDate"));
            java.util.Date date =  getYesterday();
            java.sql.Date date2 = convertUtilToSql(date);
            updateStreakDate.setDate(1,  date2);

            updateStreakDate.execute();
            NewFeature.newStreak(id,conn);
            PreparedStatement returnDate = conn.prepareStatement(resource.getString("qReturnDate"));
            returnDate.setInt(1,id);
            ResultSet rs = returnDate.executeQuery();
            Date Date = null;
            while (rs.next()) {
                Date = rs.getDate(1);
            }
            System.out.println("The date that should be: " + date2 + " and the actual dates is: " + Date);
            assertTrue(Date.toString().equals(date2.toString()));

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
    public static java.util.Date  getYesterday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Create a calendar object with today date. Calendar is in java.util pakage.
        Calendar calendar = Calendar.getInstance();

        /* Move calendar to yesterday */
        calendar.add(Calendar.DATE, -1);

        // Get current date of calendar which point to the yesterday now
        java.util.Date yesterdayDate =  calendar.getTime();

        return yesterdayDate;
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

}

