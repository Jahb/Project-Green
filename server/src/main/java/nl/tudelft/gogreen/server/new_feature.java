
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

public class new_feature {


    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void adding_feature(String username, String feature) {
        try {

            Connection conn = getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            int id = getId(username, conn);
            newStreak(id, conn);
//            actualizingfeatures(conn, feature);
//            addingToLog(id, conn, feature);
//            actualizingUserPoints(id, feature, 20, conn);
//            actualizingUserLog(id, feature, 20, conn);


        } catch (Exception exception) {
            System.out.print("There has been an error accessing the database");
        }

    }

    public static int getId(String username, Connection conn) {
        int id  = -1;
        try {
            System.out.println("the username is: " + username);
            PreparedStatement getId = conn.prepareStatement("select user_id from user_table where username = '" + username + "';");
            ResultSet rs = getId.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        } catch (Exception exception) {
            System.out.println("Error!");
        }
        return id;
    }

    private static void newStreak(int id, Connection conn) {
        try {
            PreparedStatement lastDayStreak = conn.prepareStatement("select date from streak where user_id = " + id + ";");
            ResultSet rs = lastDayStreak.executeQuery();
            String lastDay = null;
            while (rs.next()) {
                lastDay = rs.getString(1);
            }
            System.out.println(isToday(lastDay));
            if (!isToday(lastDay) && !isYesterday(lastDay)) {
                PreparedStatement resetStreak = conn.prepareStatement("insert into streak values (" + id + ", current_date, 1);");
                resetStreak.execute();
            } else if (isYesterday(lastDay)) {
                PreparedStatement addOneToStreak = conn.prepareStatement("update streak set number_of_days  = number_of_days + 1 where user_id = " + id + ";");
                addOneToStreak.execute();
            }
        } catch (Exception exception) {
            System.out.print("There has been an error accessing the database");
        }

    }


    /**
     * @param day which we want to check
     * @return : true if the date send is the date of today
     */
    private static boolean isToday(String day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date2 = dateFormat.format(date);
        System.out.println("the date send as parameter is: " + day + " and the suppose today's date is: " + date2);
        return day.equals(date2);
    }

    /**
     * @param day which we want to check
     * @return : true if the date send is the date of yesterday
     */
    private static boolean isYesterday(String day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Create a calendar object with today date. Calendar is in java.util pakage.
        Calendar calendar = Calendar.getInstance();

        /* Move calendar to yesterday */
        calendar.add(Calendar.DATE, -1);

        // Get current date of calendar which point to the yesterday now
        Date yesterdayDate = calendar.getTime();
        String yesterday = dateFormat.format(yesterdayDate);

        return day.equals(yesterday);
    }
}