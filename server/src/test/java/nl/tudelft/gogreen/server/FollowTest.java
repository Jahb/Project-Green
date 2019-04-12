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


public class FollowTest {


    @Before
    public void createUsers(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
            CreateUser.create_user("pablo", "pablo");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void isFollowing() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            int id1 = NewFeature.getId("paul",conn);
            int id2 = NewFeature.getId("pablo",conn);
            Following.follow(id1,id2);

            boolean result = Following.isFollowing(id1,id2,conn);

            assertTrue(result);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @After
    public void deleteUsers() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            int id1 = NewFeature.getId("paul",conn);
            int id2 = NewFeature.getId("pablo",conn);

            CreateUser.delete_user(id1,conn);
            CreateUser.delete_user(id2,conn);

            Following.deleteAllFollows(id1,conn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}