package nl.tudelft.gogreen.server.statistics;

import javafx.util.Pair;
import nl.tudelft.gogreen.server.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statistics {


    /**
     * Method which returns an array of Floats with values in order chronological
     * with today in position 0 and seventh day on position 6, the total number of
     * points in position 7 and the average in position 8.
     *
     * @param id id of the user
     * @return returns the data in the form of array of Floats
     * @throws SQLException raised if an error occurs accessing the database
     */
    public static double[] getLastWeekData(int id) throws SQLException {

        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        double[] data = getLastData(id, 7, conn);
        conn.close();
        return data;
    }

    /**
     * Method which returns an array of Floats with values in order chronological
     * with today in position 0 and 30th day on position 29, the total number of
     * points in position 30 and the average in position 31.
     *
     * @param id id of the user
     * @return returns the data in the form of array of Floats
     * @throws SQLException raised if an error occurs accessing the database
     */
    public static double[] getLastMonthData(int id) throws SQLException {

        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));


        double[] data = getLastData(id, 30, conn);
        conn.close();
        return data;

    }

    /**
     * Method which returns an array of Floats with values in order chronological
     * with today in position 0 and  365th day on position 364, the total number of
     * points in position 365 and the average in position 366.
     *
     * @param id id of the user
     * @return returns the data in the form of array of Floats
     * @throws SQLException raised if an error occurs accessing the database
     */
    public static double[] getLastYearData(int id) throws SQLException {

        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        double[] data = getLastData(id, 365, conn);
        conn.close();
        return data;
    }

    /**
     * Helper function which gets the retrospective data of the given days.
     *
     * @param id   id of the user
     * @param days Number of days you want data to be retrieved
     * @param conn Connection to the database
     * @return returns the data in the form of array of Floats
     * @throws SQLException raised if an error occurs accessing the database
     */
    public static double[] getLastData(int id, int days, Connection conn) throws SQLException {

        double[] result = new double[days + 2];
        double total = 0.0;
        PreparedStatement gettingWeekData =
                conn.prepareStatement(Main.resource.getString("qGetData"));
        gettingWeekData.setInt(2, id);
        for (int i = 0; i < days; i++) {
            gettingWeekData.setInt(1, i);

            ResultSet dayData = gettingWeekData.executeQuery();
            double totalPointsDay = 0.0;
            while (dayData.next()) {
                totalPointsDay = (double) (dayData.getInt(1));
            }
            result[i] = totalPointsDay;
            total += totalPointsDay;
        }
        result[days] = total;
        result[days + 1] = total / days;
        return result;
    }

    /**
     * Inserts the data from the Entry Quiz into the database table.
     *
     * @param id            user's id
     * @param monthlyIncome the monthly income of the user
     * @param householdSize the household size of the user
     * @param ownVehicle    if the user owns a vehicle or not
     * @param energyBill    the annual energy bill of the user
     * @param houseSurface  user's house surface
     * @throws SQLException raises error if unable to access database
     */
    public static void insertQuizData(int id, int monthlyIncome,
                                      int householdSize, boolean ownVehicle,
                                      int energyBill, int houseSurface) throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData =
                conn.prepareStatement(Main.resource.getString("qQuizData"));
        insertData.setInt(1, id);
        insertData.setInt(2, monthlyIncome);
        insertData.setInt(3, householdSize);
        insertData.setBoolean(4, ownVehicle);
        insertData.setInt(5, energyBill);
        insertData.setInt(6, houseSurface);
        insertData.execute();
        conn.close();
    }

    /**
     * Get the features for a user.
     * @param id the user id
     * @return a list of pairs
     * @throws SQLException if something goes wrong at the database
     */
    public static List<Pair<String, Date>> retrieveFeaturesHistory(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData =
                conn.prepareStatement(Main.resource.getString("qRetrieveFH"));
        insertData.setInt(1, id);

        ResultSet rs = insertData.executeQuery();

        List<Pair<String, Date>> list = new ArrayList<>();
        while (rs.next()) {
            Pair<String, Date> pair = new Pair<>(rs.getString(1), rs.getDate(2));
            list.add(pair);
        }
        conn.close();
        return list;
    }


}
