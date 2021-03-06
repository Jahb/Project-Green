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


public class leave_eventTest {


    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @After
    public void delete() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {


            EventsMain.delete_event("test", "karnaval", conn);

            int id_creator = NewFeature.getId("creator", conn);
            int id_participant = NewFeature.getId("participant", conn);

            CreateUser.delete_user(id_creator, conn);
            CreateUser.delete_user(id_participant, conn);

        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

    @Test
    public void leave_eventTest() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.create_user("creator", "test");
            CreateUser.create_user("participant", "test");

            int id_creator = NewFeature.getId("creator", conn);
            int id_participant = NewFeature.getId("participant", conn);

            EventsMain.create_event("creator", "karnaval", "", "", "", conn);

            int event_id = EventsMain.getEventId("karnaval", conn);

            EventsMain.join_event("participant", "karnaval", conn);
            EventsMain.leave_event("participant", "karnaval", conn);


            PreparedStatement getEvent = conn.prepareStatement("select participant from event_participants where event_id =? and participant =?;");
            getEvent.setInt(1, event_id);
            getEvent.setInt(2, id_participant);
            ResultSet rs = getEvent.executeQuery();
            int id = -1;

            while (rs.next()) {
                id = rs.getInt(1);
            }
            assertEquals(-1, id);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}