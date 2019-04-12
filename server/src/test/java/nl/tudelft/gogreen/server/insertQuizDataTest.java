package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.server.statistics.Statistics;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class insertQuizDataTest {
    @Before
    public void fix() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test

    public void insertQuizTest() {

        try (

                Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");

            Statistics.insertQuizData(NewFeature.getId("paul", conn), 20, 20, true, 20, 20);

            PreparedStatement result = conn.prepareStatement(Main.resource.getString("qReturnQuiz"));
            result.setInt(1, NewFeature.getId("paul", conn));

            ResultSet rs = result.executeQuery();

            int monthly = 0;
            while (rs.next()) {
                monthly = rs.getInt(1);
            }

            assertEquals(monthly, 20);

            PreparedStatement result2 = conn.prepareStatement(Main.resource.getString("qDeleteQuiz"));
            result2.setInt(1, NewFeature.getId("paul", conn));
            result2.execute();

            CreateUser.delete_user(NewFeature.getId("paul", conn), conn);
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
        }
    }

}