package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.achievements.AchievementController;
import nl.tudelft.gogreen.server.auth.CreateUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

public class AchievementTest {

    private AchievementController cs = new AchievementController();

    @Before
    public void add() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);
        CreateUser.create_user("TestKees", "asdf");
    }

    @Test
    public void testAch() {
        Assert.assertNotNull(cs.getFor("TestKees").getData());
    }

    @Test
    public void testAchNone() {
        Assert.assertNotNull(cs.getFor("TestKeesa").getData());
    }

    @Test
    public void names() {
        Assert.assertTrue(cs.getNames().getData().size() > 0);
    }

    @After
    public void delet() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
