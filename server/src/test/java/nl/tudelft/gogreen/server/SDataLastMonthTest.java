package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SDataLastMonthTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Before
    public void createUser() {
        try {
            CreateUser.create_user("Russell", "Westbrook");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void getLastMonthData() {

        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("Russell", conn);
            float[] AllData = Statistics.getLastMonthData(id);
            float totalPoints = AllData[AllData.length - 2];

            PreparedStatement insertData = conn.prepareStatement(resource.getString("qInsertMonthData"));
            insertData.setInt(1, id);
            insertData.execute();

            float[] AllData2 = Statistics.getLastMonthData(id);
            float totalPoints2 = AllData2[AllData2.length - 2];

            assertNotEquals(totalPoints, totalPoints2);

            totalPoints += 20;

            assertEquals(totalPoints, totalPoints2, 0.1);
            float value1 = 20f / 30f;
            assertEquals(AllData2[AllData2.length - 2], 20f, 0.1);
            assertEquals(AllData2[AllData2.length - 1], value1, 0.1);


        } catch (Exception e) {
            System.out.println("lol" + e.getCause());
        }
    }

    @After
    public void deleteUser() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("Russell", conn), conn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}