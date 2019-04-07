package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class gettingAllUsersTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Before
    public void createUser(){
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("paul", "paul");

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getAllUsers() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            int oldsize = Following.gettingAllUsers().size();
            CreateUser.create_user("pablo", "pablo");
            int newsize = Following.gettingAllUsers().size();
            assertNotEquals(oldsize,newsize);
            oldsize++;
            assertEquals(oldsize,newsize);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @After
    public void deleteUsers() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("paul",conn),conn);
            CreateUser.delete_user(NewFeature.getId("pablo",conn),conn);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}