package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertTrue;


public class isFollowingTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Before
    public void createUsers(){
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
            CreateUser.create_user("pablo", "pablo");
            Following.Follow(NewFeature.getId("paul",conn), NewFeature.getId("pablo",conn));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void isFollowing() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            boolean result = Following.isFollowing(0,1,conn);

            assertTrue(result);
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