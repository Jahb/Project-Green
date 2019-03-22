package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Statistics {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static int[] getLastWeekData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        return getLastData(id,7,conn);


    }
    public static int[] getLastMonthData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

       return getLastData(id,30,conn);
    }

    public static int[] getLastData(int id,int j, Connection conn) throws Exception {

        int[] result = new int[j];

        PreparedStatement gettinWeekData = conn.prepareStatement(resource.getString("qGetData"));
        gettinWeekData.setInt(2, id);
        for (int i = 0; i < j; i++) {
            gettinWeekData.setInt(1, i);

            ResultSet dayData = gettinWeekData.executeQuery();
            int totalPointsDay = 0;
            while (dayData.next()) {
                totalPointsDay = dayData.getInt(1);
            }
            result[i] = totalPointsDay;
        }

        return result;
    }

}
