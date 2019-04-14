package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.events.EventsMain;
import nl.tudelft.gogreen.server.statistics.StatsController;
import nl.tudelft.gogreen.shared.DatePeriod;
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
public class StatsControllerTest {

    @Spy
    private StatsController cs = new StatsController();

    @Before
    public void before() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);
        CreateUser.create_user("kees", "asdf");
        CreateUser.create_user("kees1", "asdf");

        Mockito.doReturn("kees").when(cs).getCurrentUser();
    }

    @Test
    public void dateTest() {
        Assert.assertEquals(7, cs.getFor(DatePeriod.WEEK).getData().getDays());
    }

    @Test
    public void historyTest() {
        Assert.assertNotEquals(-1, cs.getHistory("kees").getData().size());
    }

    @Test
    public void quizDataTest() {
        Assert.assertTrue(cs.insertQuizData(1, 1, true, 1, 1).getData());
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
