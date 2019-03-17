package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class NFgetIdTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Before
    public void createOnlyUser() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){


            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul","paul");
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @Test
    public void getId() {
        try(            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            assertEquals(0,NewFeature.getId("paul",conn));

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }
    @After
    public void deleteUser() {
        try(            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"))) {


            int id = NewFeature.getId("paul", conn);
            CreateUser.delete_user(id, conn);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

