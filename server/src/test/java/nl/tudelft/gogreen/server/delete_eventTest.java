package nl.tudelft.gogreen.server;


import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertNull;


public class delete_eventTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Test
    public void delete_eventTest() {
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            create_user.create_user("test2","test",conn);
            int id = new_feature.getId("test2",conn);
            events_main.create_event("test2", id,"karnaval2", conn);
            events_main.delete_event("test2","karnaval2",conn);
            PreparedStatement getCreator = conn.prepareStatement("select event_name from event where event_name = 'karnaval2';");
            ResultSet rs = getCreator.executeQuery();
            String hash = null;
            while(rs.next()){
                hash = rs.getString(1);
            }
            assertNull(hash);
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}