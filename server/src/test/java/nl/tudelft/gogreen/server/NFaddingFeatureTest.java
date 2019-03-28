package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class NFaddingFeatureTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void actualizingUserPoints() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("MJ",conn);
            PreparedStatement getAccess = conn.prepareStatement(resource.getString("qReturnAccess"));
            ResultSet rs = getAccess.executeQuery();

            int access1 = -1;

            while (rs.next()) {
                access1 = rs.getInt(1);
            }

            NewFeature.adding_feature("MJ","Vegetarian Meal");

            PreparedStatement streakDateNumber = conn.prepareStatement(resource.getString("qReturnDays2"));
            streakDateNumber.setInt(1,  id);
            ResultSet rs2 = streakDateNumber.executeQuery();

            int number = -1;

            while (rs2.next()) {
                number = rs2.getInt(1);
            }
            assertEquals(number,1);

            PreparedStatement getAccess2 = conn.prepareStatement(resource.getString("qReturnAccess"));
            ResultSet rs3 = getAccess2.executeQuery();

            int access2 = -1;

            while (rs3.next()) {
                access2 = rs3.getInt(1);
            }

            assertEquals(access1+1,access2);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void createUsers() throws Exception {

        CreateUser.create_user("MJ", "MJ");


    }

    @After
    public void deleteUser() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("MJ", conn), conn);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}