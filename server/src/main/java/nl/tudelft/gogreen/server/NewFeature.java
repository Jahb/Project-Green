

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


public class NewFeature {


    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * Initial Structure for the adding a feature functionalities.
     *
     * @param username name of the user
     * @param feature  name of the feature to be added
     * @return returns the name of the feature
     * @throws Exception raised if an error occurs while accessing the database
     */
    public static String adding_feature(String username, String feature) throws Exception {
        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        int id = getId(username, conn);
        newStreak(id, conn);
        actualizingfeatures(conn, feature);
        addingToLog(id, conn, feature);
        actualizingUserPoints(id, feature, 20, conn);
        actualizingUserLog(id, feature, 20, conn);
        return feature;
    }

    /**
     * Method which given a username returns its id.
     *
     * @param username given username
     * @param conn     Connection to the database
     * @return returns the id
     * @throws Exception raised if an error occurs while accessing the database
     */
    public static int getId(String username, Connection conn) throws Exception {
        int id = -1;

        System.out.println("the username is: " + username);
        PreparedStatement getId = conn.prepareStatement("select user_id " +
                "from user_table where username = ?;");
        getId.setString(1, username);
        ResultSet rs = getId.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    private static int getCategory(String feature, Connection conn) throws Exception {

        PreparedStatement getcategoryId = conn.prepareStatement("select category " +
                "from features where feature_name = ?;");
        getcategoryId.setString(1, feature);
        ResultSet rs = getcategoryId.executeQuery();

        int category = -1;

        while (rs.next()) {
            category = rs.getInt(1);
        }

        return category;


    }

    private static void actualizingUserPoints(int id, String feature,
                                              int points, Connection conn) throws Exception {
        //actualize user points, join with features table to know which category
        // the feature is and add to total

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
            default:
                System.out.println("Wrong category");
        }
        PreparedStatement updatectotal =
                conn.prepareStatement(resource.getString("update_total_user_points"));


    }

    private static void actualizingUserLog(int id, String feature,
                                           int points, Connection conn) throws Exception {

        //actualize user points, join with features table to
        // know which category the feature is and add to total + current_date
        int category = getCategory(feature, conn);

        PreparedStatement getLastDay = conn.prepareStatement("select date " +
                "from user_history order by date desc limit 1;");
        ResultSet rs = getLastDay.executeQuery();

        String lastDate = null;
        while (rs.next()) {
            lastDate = rs.getString(1);
        }

        if (lastDate == null || !isToday(lastDate)) {
            switch (category) {

                case 1:
                    PreparedStatement createc1 = conn.prepareStatement("insert into user_history" +
                            " values (?,current_date,?,0,0,0,?);");
                    createc1.setInt(1, id);
                    createc1.setInt(2, points);
                    createc1.setInt(3, points);
                    createc1.execute();
                    break;

                case 2:
                    PreparedStatement createc2 =
                            conn.prepareStatement("insert into user_history " +
                                    "values (?,current_date,0,?,0,0,?);");
                    createc2.setInt(1, id);
                    createc2.setInt(2, points);
                    createc2.setInt(3, points);
                    createc2.execute();
                    break;

                case 3:
                    PreparedStatement createc3 =
                            conn.prepareStatement("insert into user_history " +
                                    "values (?,current_date,0,0,?,0,?);");
                    createc3.setInt(1, id);
                    createc3.setInt(2, points);
                    createc3.setInt(3, points);
                    createc3.execute();
                    break;

                case 4:
                    PreparedStatement createc4 =
                            conn.prepareStatement("insert into user_history " +
                                    "values (?,current_date,0,0,0,?,?);");
                    createc4.setInt(1, id);
                    createc4.setInt(2, points);
                    createc4.setInt(3, points);
                    createc4.execute();
                    break;
                default:
                    System.out.println("Wrong category");

            }

        } else {
            switch (category) {

                case 1:

                    PreparedStatement upd1History =
                            conn.prepareStatement("update user_history " +
                                    "set c1 = c1 + ? where user_id =? and date = current_date;");
                    upd1History.setInt(1, points);
                    upd1History.setInt(2, id);
                    upd1History.execute();

                    break;

                case 2:

                    PreparedStatement upd2History =
                            conn.prepareStatement("update user_history " +
                                    "set c2 = c2 +? where user_id = ? and date = current_date;");
                    upd2History.setInt(1, points);
                    upd2History.setInt(2, id);
                    upd2History.execute();

                    break;

                case 3:
                    PreparedStatement upd3History =
                            conn.prepareStatement("update user_history " +
                                    "set c3 = c3 + ? where user_id =? and date = current_date;");
                    upd3History.setInt(1, points);
                    upd3History.setInt(2, id);
                    upd3History.execute();
                    break;

                case 4:
                    PreparedStatement upd4History =
                            conn.prepareStatement("update user_history " +
                                    "set c4 = c4 + ? where user_id =? and date = current_date;");
                    upd4History.setInt(1, points);
                    upd4History.setInt(2, id);
                    upd4History.execute();
                    break;

                default:
                    System.out.println("Wrong category");

            }

            PreparedStatement hupdatectotal =
                    conn.prepareStatement(resource.getString("update_total_user_history"));
            hupdatectotal.execute();


        }

    }

    private static void actualizingfeatures(Connection conn, String feature) throws Exception {

        PreparedStatement getId = conn.prepareStatement("update features " +
                "set access = access + 1 where feature_name = ? ;");
        getId.setString(1, feature);
        getId.execute();


    }

    private static void addingToLog(int id, Connection conn, String feature) throws Exception {
        PreparedStatement addToLog = conn.prepareStatement("insert into features_history values( " + id + ", current_date , (select feature_id from features where feature_name ='" + feature + "') );");
        addToLog.execute();



    }

    private static void newStreak(int id, Connection conn) throws Exception {

        PreparedStatement lastDayStreak = conn.prepareStatement("select date from streak where user_id = ?;");
        lastDayStreak.setInt(1,id);
        ResultSet rs = lastDayStreak.executeQuery();
        String lastDay = null;
        while (rs.next()) {
            lastDay = rs.getString(1);
        }
        System.out.println(isToday(lastDay));
        if (!isToday(lastDay) && !isYesterday(lastDay)) {
            PreparedStatement resetStreak = conn.prepareStatement("insert into streak values (?, current_date, 1);");
            resetStreak.setInt(1,id);
            resetStreak.execute();
        } else if (isYesterday(lastDay)) {
            PreparedStatement addOneToStreak = conn.prepareStatement("update streak set number_of_days  = number_of_days + 1 where user_id = ?;");
            addOneToStreak.setInt(1,id);
            addOneToStreak.execute();
        }

    }


    /**
     * Method which given a certain day, checks if this one is the current day.
     *
     * @param day which we want to check
     * @return : true if the date send is the date of today
     */
    public static boolean isToday(String day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date2 = dateFormat.format(date);
        System.out.println("the date send as parameter is: " +
                day + " and the suppose today's date is: " + date2);
        return day.equals(date2);
    }

    /**
     * Method which given a certain day, checks if this one is the previous day.
     *
     * @param day which we want to check
     * @return : true if the date send is the date of yesterday
     */
    public static boolean isYesterday(String day) {
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