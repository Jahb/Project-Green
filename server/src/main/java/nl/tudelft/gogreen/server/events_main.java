package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class events_main {
    private static ResourceBundle resource =  ResourceBundle.getBundle("db");
    public static void main_event(String username, String eventName){
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            //main menu of events, here depending on the message we either create an event,
            // delete it or add or delete an attendance of a user to an event
        }
        catch (Exception exception){
            System.out.println("An error has occurred!");
        }
    }
    public static void create_event(String username, String eventName, Connection conn){
        try{
            //insert into event and event_details tables!
        }
        catch (Exception exception){
            System.out.println("An error has occurred!");
        }
    }




    public static void join_event(String username, String eventName, Connection conn){
        try{
            PreparedStatement join = conn.prepareStatement("insert into event_attendance values( (select event_id from event where event_name =" + eventName + "), (select user_id from user_table where username = " + username + ");");
        }
        catch (Exception exception){
            System.out.println("An error has occurred!");
        }
    }


}
