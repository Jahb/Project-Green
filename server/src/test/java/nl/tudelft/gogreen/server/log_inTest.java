package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertTrue;

public class log_inTest {


    @Before
    public void createUser(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password")))
        {

            CreateUser.create_user("paul","paul");

        }
        catch(Exception exception)
        {
            System.out.println("Error");
        }
    }


    @After
    public void deleteUser() {
        try (            Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))){

            int id = NewFeature.getId("paul",conn);
            CreateUser.delete_user(id, conn);
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
    @Test

    public void log_inTest() {
        try (            Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))){
            assertTrue(LogIn.log_in("paul","paul"));
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}