package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class events_main {
    private static ResourceBundle resource =  ResourceBundle.getBundle("db");
    public static void main_event(String username, String eventName){
        try {
            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            System.out.println("What do you want to do?");
            System.out.println("\t 1 - Create event ");
            System.out.println("\t 2 - Delete event");
            System.out.println("\t 3 - Join event");
            System.out.println("\t 4 - Delete event ");
            boolean loop = false;
            int option;
            String name;

            while (!loop) {
                System.out.print("Enter an option: ");
                Scanner scanner = new Scanner(System.in);
                option = scanner.nextInt();
            }
        }
        catch (Exception exception){
            System.out.println("An error has occurred!");
        }
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

    public static void join_event(String username, String eventName, Connection conn){
        try{
            int id = new_feature.getId(username,conn);
            PreparedStatement join = conn.prepareStatement("insert into event_participants values( (select event_id from event where event_name ='" + eventName + "'), " + id + ");");
            join.execute();
        }
        catch (Exception exception){

            System.out.println("An error has occurred!");
        }
    }
    public static void leave_event(String username, String eventName, Connection conn){
        try{
            int id = new_feature.getId(username,conn);
            PreparedStatement leave = conn.prepareStatement("delete from event_participants where participant = " + id + " and event_id = (select event_id from event where event_name = '" + eventName + "');");
            leave.execute();
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
