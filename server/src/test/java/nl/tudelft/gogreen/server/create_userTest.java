package nl.tudelft.gogreen.server;


import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.testng.Assert.assertTrue;

public class create_userTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


@Test
public void create_userTest() {
    try {
        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        create_user.create_user("paul", "paul", conn);
        assertTrue(log_in.log_in("paul", "paul", conn));
    }
    catch (Exception exception){
        System.out.println("Error!");
    }
}
}

