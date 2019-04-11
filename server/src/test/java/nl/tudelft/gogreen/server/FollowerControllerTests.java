package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.events.EventsMain;
import nl.tudelft.gogreen.server.followers.FollowerController;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FollowerControllerTests {

    @Spy
    private FollowerController cs = new FollowerController();

    @Before
    public void before() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);
        CreateUser.create_user("kees", "asdf");
        CreateUser.create_user("kees1", "asdf");

        Mockito.doReturn("kees").when(cs).getCurrentUser();

    }

    @Test
    public void aafollowerTest() {
        Assert.assertTrue(cs.follow("kees1").getData());
    }

    @Test
    public void abfollowerstest() {
        Assert.assertTrue(cs.followers().getData().isEmpty());
    }

    @Test
    public void bafollowingtest() {
        Assert.assertEquals(1, cs.following().getData().size());
    }

    @Test
    public void bbunfollowerTest() {
        Assert.assertTrue(cs.unfollow("kees1").getData());
    }

    @AfterClass
    public static void delet() {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
