package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.server.statistics.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SDataLastMonthTest {



    @Before
    public void createUser() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try {
            CreateUser.create_user("Russell", "Westbrook");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void getLastMonthData() {

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("Russell", conn);
            double[] AllData = Statistics.getLastMonthData(id);
            double totalPoints = AllData[AllData.length - 2];

            PreparedStatement insertData = conn.prepareStatement(Main.resource.getString("qInsertMonthData"));
            insertData.setInt(1, id);
            insertData.execute();

            double[] AllData2 = Statistics.getLastMonthData(id);
            double totalPoints2 = AllData2[AllData2.length - 2];

            assertNotEquals(totalPoints, totalPoints2);

            totalPoints += 20;

            assertEquals(totalPoints, totalPoints2, 0.1);
            double value1 = 20f / 30f;
            assertEquals(AllData2[AllData2.length - 2], 20f, 0.1);
            assertEquals(AllData2[AllData2.length - 1], value1, 0.1);


        } catch (Exception e) {
            System.out.println("lol" + e.getCause());
        }
    }

    @After
    public void deleteUser() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("Russell", conn), conn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}