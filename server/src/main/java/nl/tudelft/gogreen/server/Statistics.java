package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Statistics {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * Method which returns an array of Floats with values in order chronological
     * with today in position 0 and seventh day on position 6, the total number of
     * points in position 7 and the average in position 8.
     *
     * @param id id of the user
     * @return returns the data in the form of array of Floats
     * @throws Exception raised if an error occurs accessing the database
     */
    public static double[] getLastWeekData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

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
     * @throws Exception raised if an error occurs accessing the database
     */
    public static double[] getLastMonthData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));


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
     * @throws Exception raised if an error occurs accessing the database
     */
    public static double[] getLastYearData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

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
     * @throws Exception raised if an error occurs accessing the database
     */
    public static double[] getLastData(int id, int days, Connection conn) throws Exception {

        double[] result = new double[days + 2];
        double total = 0.0;
        PreparedStatement gettingWeekData = conn.prepareStatement(resource.getString("qGetData"));
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
     * @param id user's id
     * @param monthlyIncome the monthly income of the user
     * @param householdSize the household size of the user
     * @param ownVehicle if the user owns a vehicle or not
     * @param energyBill the annual energy bill of the user
     * @param houseSurface user's house surface
     * @throws Exception raises error if unable to access database
     */
    public static void insertQuizData(int id, int monthlyIncome,
                                      int householdSize, boolean ownVehicle,
                                      int energyBill, int houseSurface) throws Exception {
        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData = conn.prepareStatement(resource.getString("qQuizData"));
        insertData.setInt(1, id);
        insertData.setInt(2,monthlyIncome);
        insertData.setInt(3,householdSize);
        insertData.setBoolean(4,ownVehicle);
        insertData.setInt(5,energyBill);
        insertData.setInt(6,houseSurface);

        insertData.execute();
    }


}
