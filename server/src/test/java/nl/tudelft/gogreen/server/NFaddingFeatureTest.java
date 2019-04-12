package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class NFaddingFeatureTest {



    @Test
    public void actualizingUserPoints() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("MJ",conn);
            PreparedStatement getAccess = conn.prepareStatement(Main.resource.getString("qReturnAccess"));
            ResultSet rs = getAccess.executeQuery();

            int access1 = -1;

            while (rs.next()) {
                access1 = rs.getInt(1);
            }

            NewFeature.aadding_feature("MJ","Vegetarian Meal",20);

            PreparedStatement streakDateNumber = conn.prepareStatement(Main.resource.getString("qReturnDays2"));
            streakDateNumber.setInt(1,  id);
            ResultSet rs2 = streakDateNumber.executeQuery();

            int number = -1;

            while (rs2.next()) {
                number = rs2.getInt(1);
            }
            assertEquals(number,1);

            PreparedStatement getAccess2 = conn.prepareStatement(Main.resource.getString("qReturnAccess"));
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
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        CreateUser.create_user("MJ", "MJ");


    }

    @After
    public void deleteUser() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("MJ", conn), conn);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}