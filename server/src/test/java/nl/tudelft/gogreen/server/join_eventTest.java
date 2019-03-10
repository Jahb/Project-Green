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
            events_main.delete_event("test","karnaval",conn);

            int id_creator = new_feature.getId("creator",conn);
            int id_participant = new_feature.getId("participant",conn);

            create_user.delete_user(id_creator,conn);
            create_user.delete_user(id_participant,conn);
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
    @Test
    public void join_eventTest() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            create_user.create_user("creator","test",conn);
            create_user.create_user("participant","test",conn);

            int id_creator = new_feature.getId("creator",conn);
            int id_participant = new_feature.getId("participant",conn);

            events_main.create_event("creator", id_creator,"karnaval", conn);

            int event_id = events_main.getEventId("karnaval",conn);

            events_main.join_event("participant","karnaval",conn);

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