package nl.tudelft.gogreen.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class EventsMain {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * Method which creates an event with the given parameters.
     *
     * @param username  name of the user
     * @param idCreator id of the creator of the event
     * @param eventName name of the new event
     * @param conn      connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void create_event(String username, int idCreator,
                                    String eventName, Connection conn) throws Exception {

        int idEvent = getMaxId(conn);
        idCreator = NewFeature.getId(username, conn);
        System.out.println("The idEvent is: " + idEvent + " and the eventName is: " + eventName + " and the idCreator is: " + idCreator);
        PreparedStatement createEvent = conn.prepareStatement(resource.getString("qInsertIntoEvent"));
        createEvent.setInt(1,idEvent);
        createEvent.setString(2,eventName);
        createEvent.setInt(3,idCreator);
        System.out.println(createEvent.toString());
        createEvent.execute();
    }

    /**
     * Method which deletes an existing event with the given parameters.
     *
     * @param username  name of the user
     * @param eventName name of the delete event
     * @param conn      connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void delete_event(String username, String eventName,
                                    Connection conn) throws Exception {
        //delete event

        PreparedStatement delete = conn.prepareStatement(resource.getString("qDeleteFromEvent"));
        delete.setString(1,eventName);
        delete.execute();

    }

    /**
     * Method which allows a user to join an existing event with the given parameters.
     *
     * @param username  name of the user
     * @param eventName name of the event the user wants to join
     * @param conn      connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void join_event(String username, String eventName,
                                  Connection conn) throws Exception {

        int id = NewFeature.getId(username, conn);
        PreparedStatement join = conn.prepareStatement(resource.getString("qJoinEvent"));
        join.setString(1,eventName);
        join.setInt(2,id);
        join.execute();

    }

    /**
     * Method which allows a user to leave an existing event with the given parameters.
     *
     * @param username  name of the user
     * @param eventName name of the event the user wants to leave
     * @param conn      connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void leave_event(String username, String eventName,
                                   Connection conn) throws Exception {

        int id = NewFeature.getId(username, conn);
        PreparedStatement leave = conn.prepareStatement(resource.getString("qLeaveEvent"));
        leave.setInt(1,id);
        leave.setString(2,eventName);
        leave.execute();
    }

    /**
     * Method which returns the event id of the given event name.
     * @param eventName name of the event
     * @param conn connection to the database
     * @return returns the id
     * @throws Exception raises when an error occurs accessing
     */
    public static int getEventId(String eventName, Connection conn) throws Exception {
        int id = -1;


        PreparedStatement getId = conn.prepareStatement(resource.getString("qGetEventId"));
        getId.setString(1,eventName);
        ResultSet rs = getId.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }
    /**
     * Method which returns the next id available for an event.
     *
     * @param conn connection to the database
     * @return returns the id
     * @throws Exception raises when an error occurs accessing
     */

    public static int getMaxId(Connection conn) throws Exception {

        PreparedStatement getMaxId = conn.prepareStatement(resource.getString("qGetMaxId"));
        ResultSet rs0 = getMaxId.executeQuery();
        int id = 0;
        while (rs0.next()) {
            id = rs0.getInt(1) + 1;
        }
        return id;
    }

    /**
     * Method which leaves from all the events to which the given user had joined.
     *
     * @param id   of the user
     * @param conn connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void deleteAllAtendance(int id, Connection conn) throws Exception {
        PreparedStatement delAttendance = conn.prepareStatement(resource.getString("qDeleteAllAtendance"));
        delAttendance.setInt(1,id);
        delAttendance.execute();
    }

    /**
     * Method which deletes all events created by a given user.
     *
     * @param id   of the user
     * @param conn connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void deleteAllEvents(int id, Connection conn) throws Exception {
        PreparedStatement delEvent = conn.prepareStatement(resource.getString("qDeleteAllEvents"));
        delEvent.setInt(1,id);
        delEvent.execute();
    }

}
