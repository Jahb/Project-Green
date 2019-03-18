package nl.tudelft.gogreen.server;


import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertTrue;


public class NFnewStreakTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Before
    public void createOnlyUser() {
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {


            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("coco","paul");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void main() {
        try(            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("coco",conn);

            PreparedStatement deleteStreak = conn.prepareStatement(resource.getString("qDeleteStreak"));
            deleteStreak.setInt(1,id);

            deleteStreak.execute();
            NewFeature.newStreak(id,conn);

            PreparedStatement updateStreakPoints = conn.prepareStatement(resource.getString("qUpdateStreakPoints"));
            updateStreakPoints.execute();

            PreparedStatement returnNumberDays = conn.prepareStatement(resource.getString("qReturnDays"));
            ResultSet rs = returnNumberDays.executeQuery();
            int days = 0;
            while (rs.next()) {
                days = rs.getInt(1);
            }

            assertTrue(days == 2);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

