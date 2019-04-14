package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.FeatureController;
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
public class FeatureControllerTest {

    @Spy
    private FeatureController cs = new FeatureController();

    @Before
    public void before() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);
        CreateUser.create_user("kees", "asdf");
        CreateUser.create_user("kees1", "asdf");

        Mockito.doReturn("kees").when(cs).getUserObject();
    }

    @Test
    public void aaNewTest() {
        Assert.assertNotEquals(-1, cs.addNew("Local Product", "10").getData().intValue());
    }

    @Test
    public void abExceptionsTest() {
        Assert.assertEquals(-1, cs.exceptions().getData().intValue());
    }

    @Test
    public void acTotalTest() {
        Assert.assertNotEquals(-1, cs.getTotal().getData().intValue());
    }

    @Test
    public void adPointsTest() {
        Assert.assertEquals(4, cs.getPoints("kees").getData().size());
    }

    @Test
    public void aeStreakTest() {
        Assert.assertNotEquals(-1, cs.getStreak());
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
