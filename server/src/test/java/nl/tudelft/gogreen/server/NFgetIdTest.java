package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class NFgetIdTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    Connection conn;

    @Before
    public void createOnlyUser() {
        try {
            conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @Test
    public void getId() {
        try {
            assertEquals(0,NewFeature.getId("paul",conn));

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @After
    public void deleteUser() {
        try {


            int id = NewFeature.getId("paul", conn);
            CreateUser.delete_user(id, conn);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

