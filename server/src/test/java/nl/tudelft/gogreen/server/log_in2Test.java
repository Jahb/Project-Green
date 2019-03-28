package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertFalse;

public class log_in2Test {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Before
    public void createUser(){
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));)
        {

            CreateUser.create_user("'paul'","paul");

        }
        catch(Exception exception)
        {
            System.out.println("Error");
        }
    }


    @After
    public void deleteUser() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){


            int id = NewFeature.getId("paul",conn);
            CreateUser.delete_user(id, conn);
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
    @Test
    public void log_inTestNegativeScenario() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            assertFalse(LogIn.log_in("paul","pa"));
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}