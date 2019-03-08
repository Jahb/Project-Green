package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class events_main {
    private static ResourceBundle resource =  ResourceBundle.getBundle("db");
    public static void main_event(String username, String eventName){

    }
    public static void create_event(String username,int idCreator, String eventName, Connection conn){
        try{
            int idEvent = getMaxId(conn);
            idCreator = new_feature.getId(username,conn);
            PreparedStatement createEvent = conn.prepareStatement("insert into event values(" + idEvent + ", '"+ eventName + "',  " + idCreator + ");");
            createEvent.execute();

        }
        catch (Exception exception){
            System.out.println("An error has occurred!");
        }
    }
    public static void delete_event(String username, String eventName, Connection conn){
        //delete event
        try{
            PreparedStatement delete = conn.prepareStatement("delete from event where event_name = '" + eventName + "';");
            delete.execute();
        }
        catch (Exception exception){
            System.out.println("An error has occurred!");
        }
    }


    public static int getMaxId(Connection conn){
        try {
            PreparedStatement getMaxId = conn.prepareStatement("select event_id from event order by event_id desc limit 1;");
            ResultSet rs0 = getMaxId.executeQuery();
            int id = 0;
            while (rs0.next()) {
                id = rs0.getInt(1) + 1;
            }
            return id;
        }
        catch(Exception exception){
            System.out.println("Error!");
            return 0;
        }
    }

    public static int getEventId(String event_name,Connection conn){
        try {
            PreparedStatement getEventId = conn.prepareStatement("select event_id from event where event_name = '"+ event_name +"';");
            ResultSet rs0 = getEventId.executeQuery();
            int id = 0;
            while (rs0.next()) {
                id = rs0.getInt(1);
            }
            return id;
        }
        catch(Exception exception){
            System.out.println("Error!");
            return 0;
        }
    }

}
