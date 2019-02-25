package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            actualizingfeatures(conn, feature);
            addingToLog(id, conn, feature);
            actualizingUserPoints(id, feature, 20, conn);
            actualizingUserLog(id, feature, 20, conn);


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

    private static void actualizingUserPoints(int id, String feature, int points, Connection conn) {
        try {
            //actualize user points, join with features table to know which category the feature is and add to total

            int category = getCategory(feature, conn);

            switch (category) {

                case 1:
                    PreparedStatement updatec1 =
                            conn.prepareStatement("update user_points set c1 = c1 +" + points +
                                    " where user_id = " + id + ";");
                    updatec1.execute();
                    break;

                case 2:
                    PreparedStatement updatec2 =
                            conn.prepareStatement("update user_points set c2 = c2 +" + points +
                                    " where user_id = " + id + ";");
                    updatec2.execute();
                    break;

                case 3:
                    PreparedStatement updatec3 =
                            conn.prepareStatement("update user_points set c3 = c3 +" + points +
                                    " where user_id = " + id + ";");
                    updatec3.execute();
                    break;

                case 4:
                    PreparedStatement updatec4 =
                            conn.prepareStatement("update user_points set c4 = c4 +" + points +
                                    " where user_id = " + id + ";");
                    updatec4.execute();
                    break;
            }
            PreparedStatement updatectotal =
                    conn.prepareStatement(resource.getString("update_total_user_points"));

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    private static int getCategory(String feature, Connection conn) {
        try {

            PreparedStatement getcategoryId = conn.prepareStatement("select category from features where feature_name = '" + feature + "';");
            ResultSet rs = getcategoryId.executeQuery();

            int category = -1;

            while (rs.next()) {
                category = rs.getInt(1);
            }

            return category;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static void actualizingUserLog(int id, String feature, int points, Connection conn) {
        try {
            //actualize user points, join with features table to know which category the feature is and add to total + current_date
            int category = getCategory(feature, conn);

            PreparedStatement getLastDay = conn.prepareStatement("select date from user_history order by date desc limit 1;");
            ResultSet rs = getLastDay.executeQuery();

            String LastDate = null;
            while (rs.next()) {
                LastDate = rs.getString(1);
            }

            if (LastDate == null || !isToday(LastDate)) {
                switch (category) {

                    case 1:
                        PreparedStatement createc1 =
                                conn.prepareStatement("insert into user_history values (" + id + ",current_date," + points + ",0,0,0," + points + ");");
                        createc1.execute();
                        break;

                    case 2:
                        PreparedStatement createc2 =
                                conn.prepareStatement("insert into user_history values (" + id + ",current_date,0," + points + ",0,0," + points + ");");
                        createc2.execute();
                        break;

                    case 3:
                        PreparedStatement createc3 =
                                conn.prepareStatement("insert into user_history values (" + id + ",current_date,0,0," + points + ",0," + points + ");");
                        createc3.execute();
                        break;

                    case 4:
                        PreparedStatement createc4 =
                                conn.prepareStatement("insert into user_history values (" + id + ",current_date,0,0,0," + points + "," + points + ");");
                        createc4.execute();
                        break;


                }

            } else {
                switch (category) {

                    case 1:

                        PreparedStatement updatec1_history =
                                conn.prepareStatement("update user_history set c1 = c1 +" + points +
                                        " where user_id = " + id + "and date = current_date;");
                        updatec1_history.execute();

                        break;

                    case 2:

                        PreparedStatement updatec2_history =
                                conn.prepareStatement("update user_history set c2 = c2 +" + points +
                                        " where user_id = " + id + "and date = current_date;");
                        updatec2_history.execute();

                        break;

                    case 3:
                        PreparedStatement updatec3_history =
                                conn.prepareStatement("update user_history set c3 = c3 +" + points +
                                        " where user_id = " + id + "and date = current_date;");
                        updatec3_history.execute();
                        break;

                    case 4:
                        PreparedStatement updatec4_history =
                                conn.prepareStatement("update user_history set c4 = c4 +" + points +
                                        " where user_id = " + id + "and date = current_date;");
                        updatec4_history.execute();

                        break;
                }

                PreparedStatement hupdatectotal =
                        conn.prepareStatement(resource.getString("update_total_user_history"));
                hupdatectotal.execute();


            }
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    private static void actualizingfeatures(Connection conn, String feature) {
        try {
            PreparedStatement getId = conn.prepareStatement("update features set access = access + 1 where feature_name = '" + feature + "' ;");
            getId.execute();

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    private static void addingToLog(int id, Connection conn, String feature) {
        try {

            PreparedStatement addToLog = conn.prepareStatement("insert into features_history values( " + id + ", current_date , (select feature_id from features where feature_name ='" + feature + "') );");
            addToLog.execute();

        } catch (Exception exception) {
            System.out.println("Error!");
        }
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