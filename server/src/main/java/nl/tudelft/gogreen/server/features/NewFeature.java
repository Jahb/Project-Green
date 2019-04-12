package nl.tudelft.gogreen.server.features;

import nl.tudelft.gogreen.server.api.CoolClimateApi;
import nl.tudelft.gogreen.server.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.sql.DriverManager.getConnection;


public class NewFeature {



    public static String adding_feature(String username, String feature, String user_input) throws Exception {
        float points = C02toPoints(CoolClimateApi.fetchApiData(feature, user_input, getUID(username)));
        return aadding_feature(username, feature, points);
    }

    /**
     * Initial Structure for the adding a feature functionalities.
     *
     * @param username name of the user
     * @param feature  name of the feature to be added
     * @return returns the name of the feature
     * @throws Exception raised if an error occurs while accessing the database
     */
    public static String aadding_feature(String username, String feature, float points) throws Exception {
        Connection conn = getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        int id = getId(username, conn);
        newStreak(id, conn);
        actualizingFeatures(conn, feature);
        addingToLog(id, conn, feature);
        actualizingUserPoints(id, feature, points, conn);
        actualizingUserLog(id, feature, points, conn);
        int total = getTotal(id, conn);
        conn.close();
        return String.valueOf(total);
    }

    public static float C02toPoints(float points) {

        return points;

    }

    /**
     * Get the total points of a user by username.
     *
     * @param username the username of the user
     * @return the total points of the user
     * @throws Exception when something goes wrong
     */
    public static int getTotal(String username) throws Exception {
        Connection conn = getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        int id = getId(username, conn);
        int total = getTotal(id, conn);
        conn.close();
        return total;
    }

    public static int getTotal(float id, Connection conn) throws Exception {
        PreparedStatement OldUserPoints = conn.prepareStatement(Main.resource.getString("qgetTotalUP"));
        OldUserPoints.setFloat(1, id);
        ResultSet OUP = OldUserPoints.executeQuery();
        int total = -1;
        while (OUP.next()) {
            total = OUP.getInt(1);
        }
            return total;
        }
    /**
     * Get the id of a user by username
     *
     * @param username the username
     * @return the userid
     */
    public static int getUID(String username) {
        try {
            int id;
            try (Connection conn = getConnection(
                    Main.resource.getString("Postgresql.datasource.url"),
                    Main.resource.getString("Postgresql.datasource.username"),
                    Main.resource.getString("Postgresql.datasource.password"))) {
                id = getId(username, conn);
            }
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Get the points spread out per category
     *
     * @param username the username of the user you want the points for
     * @return an array of points
     */
    public static int[] getPontsPerCategory(String username) {
        int[] res = new int[4];

        try (Connection conn = getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            int id = getId(username, conn);
            PreparedStatement getId = conn.prepareStatement(Main.resource.getString("qPontsPerCategory"));
            getId.setInt(1, id);
            ResultSet rs = getId.executeQuery();
            rs.next();
            for (int i : Arrays.asList(0, 1, 2, 3)) {
                res[i] = rs.getInt(i + 1);
            }
            System.out.println(Arrays.toString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Method which given a username returns its id.
     *
     * @param username given username
     * @param conn     Connection to the database
     * @return returns the id
     * @throws Exception raised if an error occurs while accessing the database
     */
    public static int getId(String username, Connection conn) throws SQLException {
        int id = -1;

        //System.out.println("the username is: " + username);
        PreparedStatement getId = conn.prepareStatement(Main.resource.getString("qgetId"));
        getId.setString(1, username);
        ResultSet rs = getId.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    /**
     * Method which returns the category of the given feature.
     *
     * @param feature for which we want to know its category
     * @param conn    Connection to the database
     * @return Returns the mentioned category
     * @throws Exception Raised when an error occurs while accessing the database
     */
    public static int getCategory(String feature, Connection conn) throws Exception {

        PreparedStatement getcategoryId = conn.prepareStatement(Main.resource.getString("qgetCategory"));
        getcategoryId.setString(1, feature);
        ResultSet rs = getcategoryId.executeQuery();

        int category = -1;

        while (rs.next()) {
            category = rs.getInt(1);
        }

        System.out.println(category);

        return category;


    }

    /**
     * Method which actualizes the user points when a new activity is registered.
     *
     * @param id      Id of the user which registered the activity
     * @param feature Activity which the user recorded
     * @param points  Amount of points awarded for the activity registered
     * @param conn    Connection to the database
     * @throws Exception Raised when an error occurs while accessing the database
     */
    public static void actualizingUserPoints(int id, String feature,
                                             float points, Connection conn) throws Exception {
        //actualize user points, join with features table to know which category
        // the feature is and add to total

        int category = getCategory(feature, conn);

        switch (category) {

            case 1:
                PreparedStatement updatec1 =
                        conn.prepareStatement(Main.resource.getString("qactualizec1"));

                updatec1.setFloat(1, points);

                updatec1.setInt(2, id);
                updatec1.execute();
                break;

            case 2:
                PreparedStatement updatec2 =
                        conn.prepareStatement(Main.resource.getString("qactualizec2"));
                updatec2.setFloat(1, points);
                updatec2.setInt(2, id);
                updatec2.execute();
                break;

            case 3:
                PreparedStatement updatec3 =
                        conn.prepareStatement(Main.resource.getString("qactualizec3"));

                updatec3.setFloat(1, points);
                updatec3.setInt(2, id);
                updatec3.execute();
                break;

            case 4:
                PreparedStatement updatec4 =
                        conn.prepareStatement(Main.resource.getString("qactualizec4"));

                updatec4.setFloat(1, points);


                updatec4.setInt(2, id);
                updatec4.execute();
                break;
            default:

        }
        PreparedStatement updatectotal =
                conn.prepareStatement(Main.resource.getString("updatetotalpoints"));

        updatectotal.setFloat(1, points);
        updatectotal.setInt(2, id);
        updatectotal.execute();


    }

    /**
     * Method which actualizes the log which log all daily user activities.
     *
     * @param id      Id of the user which registered the activity
     * @param feature Activity which the user recorded
     * @param points  Amount of points awarded for the activity registered
     * @param conn    Connection to the database
     * @throws Exception Raised when an error occurs while accessing the database
     */

    public static void actualizingUserLog(int id, String feature,
                                          float points, Connection conn) throws Exception {

        //actualize user points, join with features table to
        // know which category the feature is and add to total + current_date
        int category = getCategory(feature, conn);

        PreparedStatement getLastDay = conn.prepareStatement(Main.resource.getString("qGetLastDay"));
        ResultSet rs = getLastDay.executeQuery();

        String lastDate = null;
        while (rs.next()) {
            lastDate = rs.getString(1);
        }
        System.out.println("last Date is: " + lastDate);
        if (lastDate == null || !isToday(lastDate)) {
            switch (category) {

                case 1:

                    PreparedStatement createc1 = conn.prepareStatement(Main.resource.getString("qInsertHistory1"));
                    createc1.setInt(1, id);
                    createc1.setFloat(2, points);
                    createc1.setFloat(3, points);
                    createc1.execute();
                    break;

                case 2:

                    PreparedStatement createc2 = conn.prepareStatement(Main.resource.getString("qInsertHistory2"));
                    createc2.setInt(1, id);
                    createc2.setFloat(2, points);
                    createc2.setFloat(3, points);
                    createc2.execute();
                    break;

                case 3:

                    PreparedStatement createc3 = conn.prepareStatement(Main.resource.getString("qInsertHistory3"));
                    createc3.setInt(1, id);
                    createc3.setFloat(2, points);
                    createc3.setFloat(3, points);
                    createc3.execute();
                    break;

                case 4:

                    PreparedStatement createc4 = conn.prepareStatement(Main.resource.getString("qInsertHistory4"));
                    createc4.setInt(1, id);
                    createc4.setFloat(2, points);
                    createc4.setFloat(3, points);
                    createc4.execute();
                    break;
                default:


            }

        } else {
            switch (category) {

                case 1:

                    PreparedStatement upd1History =
                            conn.prepareStatement(Main.resource.getString("qUpdateHistory1"));

                    upd1History.setFloat(1, points);
                    upd1History.setInt(2, id);
                    upd1History.execute();

                    break;

                case 2:

                    PreparedStatement upd2History =
                            conn.prepareStatement(Main.resource.getString("qUpdateHistory2"));

                    upd2History.setFloat(1, points);
                    upd2History.setInt(2, id);
                    upd2History.execute();

                    break;

                case 3:
                    PreparedStatement upd3History =
                            conn.prepareStatement(Main.resource.getString("qUpdateHistory3"));

                    upd3History.setFloat(1, points);
                    upd3History.setInt(2, id);
                    upd3History.execute();
                    break;

                case 4:
                    PreparedStatement upd4History =
                            conn.prepareStatement(Main.resource.getString("qUpdateHistory4"));

                    upd4History.setFloat(1, points);
                    upd4History.setInt(2, id);
                    upd4History.execute();
                    break;

                default:


            }

            PreparedStatement hupdatectotal =
                    conn.prepareStatement(Main.resource.getString("updatetotalhistory"));

            hupdatectotal.setFloat(1, points);
            hupdatectotal.setInt(2, id);
            hupdatectotal.execute();


        }

    }

    /**
     * Method which actualizes the total record of the activity to be registered.
     *
     * @param conn Connection to the database
     * @throws Exception Raised when an error occurs while accessing the database
     */
    public static void actualizingFeatures(Connection conn, String feature) throws Exception {

        PreparedStatement getId = conn.prepareStatement(Main.resource.getString("qActualtizingFeatures"));
        getId.setString(1, feature);
        getId.execute();


    }

    /**
     * Method to add to the log of activities for each user the new registered activity.
     *
     * @param id      Id of the user which registered the activity
     * @param conn    Connection to the database
     * @param feature Activity which the user recorded
     * @throws Exception Raised when an error occurs while accessing the database
     */

    public static void addingToLog(int id, Connection conn, String feature) throws Exception {
        PreparedStatement addToLog = conn.prepareStatement(Main.resource.getString("qAddingtoLog"));
        addToLog.setInt(1, id);
        PreparedStatement featureId = conn.prepareStatement(Main.resource.getString("qFeatureId"));
        featureId.setString(1, feature);
        ResultSet results = featureId.executeQuery();
        results.next();
        addToLog.setInt(2, results.getInt(1));
        addToLog.execute();


    }

    /**
     * Method to actualize the streak of the given user.
     *
     * @param id   Id of the user which registered the activity
     * @param conn Connection to the database
     * @throws Exception Raised when an error occurs while accessing the database
     */
    public static void newStreak(int id, Connection conn) throws Exception {

        PreparedStatement lastDayStreak = conn.prepareStatement(Main.resource.getString("qSelectDate"));
        lastDayStreak.setInt(1, id);

        ResultSet rs = lastDayStreak.executeQuery();
        String lastDay = null;

        while (rs.next()) {
            lastDay = rs.getString(1);
        }
        System.out.println("the day is: " + lastDay);
        if (lastDay == null || (!isToday(lastDay) && !isYesterday(lastDay))) {

            PreparedStatement resetStreak = conn.prepareStatement(Main.resource.getString("qInsertStreak"));
            resetStreak.setInt(1, id);
            resetStreak.execute();
        } else if (isYesterday(lastDay)) {

            PreparedStatement addOneToStreak = conn.prepareStatement(Main.resource.getString("qUpdateStreak"));
            addOneToStreak.setInt(1, id);
            addOneToStreak.execute();
        }

    }

    /**
     * Method which returns the number of Streak of the given user.
     *
     * @param id of the user
     * @return Returns the number of days as an int
     */
    public static int getStreak(int id) throws Exception {


        Connection conn = getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        PreparedStatement getStreak =
                conn.prepareStatement(Main.resource.getString("qRetrievingStreakDays"));
        getStreak.setInt(1, id);
        int numDays = 0;
        ResultSet numDaysRS = getStreak.executeQuery();
        while (numDaysRS.next()) {
            numDays = numDaysRS.getInt(1);
        }

        return numDays;

    }

    /**
     * Method which returns the total per category.
     * Send c1 for category 1,..., c'n' for category 'n'.
     *
     * @param id the id
     * @param category the category
     * @param conn connection to database
     * @return the total per category
     * @throws Exception raises exception if unable to access database
     */
    public static int getTotalCategory1(int id, String category, Connection conn) throws Exception {

        PreparedStatement getTotalCategory1 =
                conn.prepareStatement("select ? from user_points where user_id = ?");
        getTotalCategory1.setString(1, category);
        getTotalCategory1.setInt(2, id);
        ResultSet points = getTotalCategory1.executeQuery();
        int total = -1;

        while (points.next()) {
            total = points.getInt(1);
        }
        return total;
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