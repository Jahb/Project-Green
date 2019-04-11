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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class countFollowersTest {


    @Before
    public void createUsers(){
        Main.resource = ResourceBundle.getBundle("db",Locale.GERMANY);

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
            CreateUser.create_user("pablo", "pablo");
            CreateUser.create_user("coco","coco");
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
            int id3 = NewFeature.getId("coco",conn);
            Following.Follow(id2,id1);
            Following.Follow(id3,id1);

            int number = Following.countAllFollowers(id1,conn);
            System.out.print(number);
            assertEquals(2, number);
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
            int id3 = NewFeature.getId("coco",conn);

//            CreateUser.delete_user(id1,conn);
//            CreateUser.delete_user(id2,conn);
//            CreateUser.delete_user(id3,conn);

            //Following.deleteAllFollows(id1,conn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}