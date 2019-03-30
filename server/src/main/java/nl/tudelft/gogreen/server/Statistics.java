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
    public static float[] getLastWeekData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        float[] data = getLastData(id, 7, conn);
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
    public static float[] getLastMonthData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));


        float[] data = getLastData(id, 30, conn);
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
    public static float[] getLastYearData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        float[] data = getLastData(id, 365, conn);
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
    public static float[] getLastData(int id, int days, Connection conn) throws Exception {

        float[] result = new float[days + 2];
        float total = 0.0f;
        PreparedStatement gettingWeekData = conn.prepareStatement(resource.getString("qGetData"));
        gettingWeekData.setInt(2, id);
        for (int i = 0; i < days; i++) {
            gettingWeekData.setInt(1, i);

            ResultSet dayData = gettingWeekData.executeQuery();
            float totalPointsDay = 0.0f;
            while (dayData.next()) {
                totalPointsDay = (float) (dayData.getInt(1));
            }
            result[i] = totalPointsDay;
            total += totalPointsDay;
        }
        result[days] = total;
        result[days + 1] = total / days;
        return result;
    }


}
