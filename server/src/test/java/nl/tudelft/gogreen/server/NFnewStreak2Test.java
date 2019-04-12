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

import static junit.framework.TestCase.assertTrue;


public class NFnewStreak2Test {



    @Before
    public void createOnlyUser() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {


            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("coco","paul");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void main() {
        try(            Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int id = NewFeature.getId("coco",conn);


            NewFeature.newStreak(id,conn);

            PreparedStatement updateStreakPoints = conn.prepareStatement(Main.resource.getString("qUpdateStreak"));
            updateStreakPoints.setInt(1,id);
            updateStreakPoints.execute();

            PreparedStatement returnNumberDays = conn.prepareStatement(Main.resource.getString("qReturnDays"));
            ResultSet rs = returnNumberDays.executeQuery();
            int days = 0;
            while (rs.next()) {
                days = rs.getInt(1);
            }


            assertTrue(days == 2);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    @After
    public void deleteUser(){
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            CreateUser.delete_user(NewFeature.getId("coco", conn), conn);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

