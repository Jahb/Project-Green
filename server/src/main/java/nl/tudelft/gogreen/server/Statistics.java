package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Statistics {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static Float[] getLastWeekData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        return getLastData(id, 7, conn);


    }

    public static Float[] getLastMonthData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        return getLastData(id, 30, conn);
    }


    public static Float[] getLastYearData(int id) throws Exception {

        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        return getLastData(id, 365, conn);
    }

    public static Float[] getLastData(int id, int j, Connection conn) throws Exception {

        Float[] result = new Float[j + 2];
        Float total = Float.valueOf("0");
        PreparedStatement gettingWeekData = conn.prepareStatement(resource.getString("qGetData"));
        gettingWeekData.setInt(2, id);
        for (int i = 0; i < j; i++) {
            gettingWeekData.setInt(1, i);

            ResultSet dayData = gettingWeekData.executeQuery();
            Float totalPointsDay = Float.valueOf("0");
            while (dayData.next()) {
                totalPointsDay = (float) (dayData.getInt(1));
            }
            result[i] = totalPointsDay;
            total += totalPointsDay;
        }
        result[j] = total;
        result[j + 1] = (total / j);
        return result;
    }



}
