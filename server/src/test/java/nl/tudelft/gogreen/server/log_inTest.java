package nl.tudelft.gogreen.server;


import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class log_inTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Before
    public void createUser(){
        try
        {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            create_user.create_user("'paul'","paul", conn);

        }
        catch(Exception exception)
        {
            System.out.println("Error");
        }
    }


}