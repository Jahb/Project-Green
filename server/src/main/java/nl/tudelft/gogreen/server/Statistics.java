package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class Statistics {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static int[] getLastWeekData(int id) throws Exception {

        int[] result = new int[7];
        Connection conn = getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        PreparedStatement gettinWeekData = conn.prepareStatement("select total from user_history where date = current_date -? and user_id = ?");
        gettinWeekData.setInt(2, id);
        for (int i = 0; i < 7; i++) {
            gettinWeekData.setInt(1, i);

            ResultSet dayData = gettinWeekData.executeQuery();
            int totalPointsDay = -1;
            while (dayData.next()) {
                totalPointsDay = dayData.getInt(1);
            }
            result[i] = totalPointsDay;
        }

        return result;
    }


}
