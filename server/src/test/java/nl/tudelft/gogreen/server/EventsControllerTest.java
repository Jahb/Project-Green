package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.events.EventsController;
import nl.tudelft.gogreen.server.events.EventsMain;
import nl.tudelft.gogreen.shared.MessageHolder;
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
public class EventsControllerTest {
    @Spy
    private EventsController cs = new EventsController();

    private static long now = System.currentTimeMillis();

    @Before
    public void before() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);
        CreateUser.create_user("kees", "asdf");
        CreateUser.create_user("kees1", "asdf");

        Mockito.doReturn("kees").when(cs).getCurrentUser();
    }

    @Test
    public void aaddNewEvent() {
        cs.addNew("TestEvent" + now, "This is a test event", "01-04-1970", "04:20");
        Assert.assertFalse(cs.list().getData().isEmpty());
    }

    @Test
    public void abJoinEvent() {
        Assert.assertTrue(cs.join("TestEvent" + now).getData());
    }

    @Test
    public void baLeaveEvent(){
        Assert.assertTrue(cs.leave("TestEvent" + now).getData());
    }

    @Test
    public void bbListEvent(){
        Assert.assertNotNull(cs.userEvents().getData());
    }

    @AfterClass
    public static void delet() {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            EventsMain.delete_event("", "TestEvent" + now, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
