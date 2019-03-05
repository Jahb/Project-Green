package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertTrue;

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


    @After
    public void deleteUser() {
        try {

            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            int id = new_feature.getId("paul",conn);
            create_user.delete_user(id, conn);
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
    @Test
<<<<<<< Updated upstream
    public void log_inTest() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            assertTrue(log_in.log_in("paul","paul",conn));
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
=======
public void log_inTest() {
    try {
        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        assertTrue(log_in.log_in("paul","paul",conn));
    }
    catch (Exception exception){
        System.out.println("Error!");
>>>>>>> Stashed changes
    }

}