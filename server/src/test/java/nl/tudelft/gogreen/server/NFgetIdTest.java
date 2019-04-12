package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class NFgetIdTest {


    Connection conn;

    @Before
    public void createOnlyUser() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try {
            conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"));
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
            assertEquals(0, NewFeature.getId("paul",conn));

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

