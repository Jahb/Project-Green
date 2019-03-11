package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class join_eventTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @After
    public void delete() {
        try {

            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            EventsMain.delete_event("test","karnaval",conn);
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
    @Test
    public void join_eventTest() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            CreateUser.create_user("creator","test");
            CreateUser.create_user("participant","test");

            int id_creator = NewFeature.getId("creator",conn);
            int id_participant = NewFeature.getId("participant",conn);

            EventsMain.create_event("creator", id_creator,"karnaval", conn);

            int event_id = EventsMain.getEventId("karnaval",conn);

            EventsMain.join_event("participant","karnaval",conn);

            PreparedStatement getEvent = conn.prepareStatement("select event_id from event_participants where participant = " + id_participant + ";");
            ResultSet rs = getEvent.executeQuery();
            int id = 0;
            while(rs.next()){
                id = rs.getInt(1);
            }
            assertEquals(id,event_id);
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}