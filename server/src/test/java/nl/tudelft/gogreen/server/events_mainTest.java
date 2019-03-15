package nl.tudelft.gogreen.server;


import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class events_mainTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Before
    public void createOnlyUser() {
        try {

            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul","paul");

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @Test
    public void getMaxId() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            assertEquals(0,EventsMain.getMaxId(conn));
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

