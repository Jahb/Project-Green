package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.events.EventsMain;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class events_mainTest {


    @Before
    public void createOnlyUser() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))){


            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul","paul");

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @Test
    public void getMaxId() {
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            assertEquals(0, EventsMain.getMaxId(conn));
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @After
    public void deleteUser(){
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            CreateUser.delete_user(NewFeature.getId("paul", conn), conn);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

