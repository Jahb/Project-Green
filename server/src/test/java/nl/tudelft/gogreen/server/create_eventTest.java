package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.events.EventsMain;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class create_eventTest {

    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @After
    public void deleteEvent() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {


            PreparedStatement delete = conn.prepareStatement("delete from event where event_name = 'karnaval';");
            delete.execute();

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @Test
    public void create_eventTest() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            CreateUser.create_user("test", "test");
            int id = NewFeature.getId("test", conn);
            EventsMain.create_event("test", "karnaval", "", "", "", conn);
            PreparedStatement getCreator = conn.prepareStatement("select event_creator from event where event_name = 'karnaval';");
            ResultSet rs = getCreator.executeQuery();
            int hash = 0;
            while (rs.next()) {
                hash = rs.getInt(1);
            }
            assertEquals(hash, id);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}