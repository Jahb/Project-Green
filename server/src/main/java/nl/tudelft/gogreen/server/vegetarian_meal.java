package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class vegetarian_meal {


    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void vegetarian_meal(String username) {
        try {

            Connection conn = getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));

            newStreak(username, conn);

        } catch (Exception exception) {
            System.out.print("There has been an error accessing the database");
        }

    }

    public static void actualizeFeatures(String username, Connection conn) {

        try {
            PreparedStatement lastDayStreak = conn.prepareStatement(resource.getString("addVMtoLog"));


        } catch (Exception exception) {
            System.out.print("There has been an error accessing the database");
        }
    }

    public static void newStreak(String username, Connection conn) {
        try {
            PreparedStatement lastDayStreak = conn.prepareStatement(resource.getString("lastDayStreak"));

            ResultSet rs = lastDayStreak.executeQuery();
            String lastDay = null;
            while (rs.next()) {
                lastDay = rs.getString(0);
            }
            if (!isToday(lastDay) && !isYesterday(lastDay)) {
                PreparedStatement resetStreak = conn.prepareStatement(resource.getString("resetStreak"));
                // QUERY NOT DONE RESET TO 0 resetStreak.execute();
            } else if (isToday(lastDay)) {
                return;
            } else if (isYesterday(lastDay)) {
                PreparedStatement addOneToStreak = conn.prepareStatement(resource.getString("AddOneToStreak"));
                // QUERY NOT DONE RESET TO 0 addOneToStreak.execute();
            }
        } catch (Exception exception) {
            System.out.print("There has been an error accessing the database");
        }


    }

    public static boolean isToday(String day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String date2 = dateFormat.format(date).toString();
        if (day.equals(date2)) return true;
        return false;
    }

    public static boolean isYesterday(String day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        // Create a calendar object with today date. Calendar is in java.util pakage.
        Calendar calendar = Calendar.getInstance();

        // Move calendar to yesterday
        calendar.add(Calendar.DATE, -1);

        // Get current date of calendar which point to the yesterday now
        Date yesterday = calendar.getTime();

        if (day.equals(yesterday)) return true;
        return false;
    }
}
