package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.server.followers.Following;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertTrue;


public class isFollowingTest {


    @Before
    public void createUsers(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
            CreateUser.create_user("pablo", "pablo");
            Following.follow(NewFeature.getId("paul",conn), NewFeature.getId("pablo",conn));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void isFollowing() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            boolean result = Following.isFollowing(0,1,conn);

            assertTrue(result);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @After
    public void deleteUsers() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("paul",conn),conn);
            CreateUser.delete_user(NewFeature.getId("pablo",conn),conn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}