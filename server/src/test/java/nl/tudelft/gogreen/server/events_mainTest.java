package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class events_mainTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @After
    public void deleteEvent() {
        try {

            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            PreparedStatement delete = conn.prepareStatement("delete from event where event_name = 'karnaval';");
            delete.execute();
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
    @Test
    public void create_eventTest() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            events_main.create_event("paul", 4,"karnaval", conn);
            PreparedStatement getCreator = conn.prepareStatement("select event_creator from event where event_name = 'karnaval';");
            ResultSet rs = getCreator.executeQuery();
            String hash = null;
            while(rs.next()){
                hash = rs.getString(1);
            }
//            assertTrue(hash.equals(4));
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}