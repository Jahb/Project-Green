package nl.tudelft.gogreen.server.events;

import nl.tudelft.gogreen.server.Main;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.shared.EventItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventsMain {


    /**
     * Method which creates an event with the given parameters.
     *
     * @param username    name of the user
     * @param eventName   name of the new event
     * @param description description of the event
     * @param date        the date of the event
     * @param time        the time of the event
     * @param conn        connection to the database
     * @throws Exception raises when an error occurs accessing
     */
    public static void create_event(String username,
                                    String eventName, String description, String date, String time, Connection conn) throws Exception {

        int idEvent = getMaxId(conn);
        int idCreator = NewFeature.getId(username, conn);
        System.out.println("The idEvent is: " + idEvent + " and the eventName is: " + eventName + " and the idCreator is: " + idCreator);
        PreparedStatement createEvent = conn.prepareStatement(Main.resource.getString("qInsertIntoEvent"));
        createEvent.setInt(1, idEvent);
        createEvent.setString(2, eventName);
        createEvent.setInt(3, idCreator);
        createEvent.setString(4, description);
        createEvent.setString(5, date);
        createEvent.setString(6, time);
        System.out.println(createEvent.toString());
        createEvent.execute();
    }


    /**
     * This method gets a list of all events
     *
     * @param conn a valid sql connection
     * @return A list of events
     * @throws SQLException if the database has an error
     */
    public static List<EventItem> get_events(Connection conn) throws SQLException {
        PreparedStatement events = conn.prepareStatement(Main.resource.getString("qListAllEvents"));
        return getEventItems(events);
    }

    public static List<EventItem> get_user_events(String username, Connection conn) throws SQLException {
        PreparedStatement events = conn.prepareStatement(Main.resource.getString("qGetUserEvents"));
        int idCreator = NewFeature.getId(username, conn);
        events.setInt(1,idCreator);
        return getEventItems(events);
    }

    private static List<EventItem> getEventItems(PreparedStatement events) throws SQLException {
        ResultSet results = events.executeQuery();
        List<EventItem> items = new ArrayList<>();
        while (results.next()) {
            items.add(new EventItem(results.getString("event_name"),
                    results.getString("event_description"),
                    results.getString("event_time"), results.getString("event_date")));
        }
        return items;
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

        PreparedStatement delete = conn.prepareStatement(Main.resource.getString("qDeleteFromEvent"));
        delete.setString(1, eventName);
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
        PreparedStatement join = conn.prepareStatement(Main.resource.getString("qJoinEvent"));
        join.setString(1, eventName);
        join.setInt(2, id);
        join.setInt(3, NewFeature.getId(username, conn));
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
        PreparedStatement leave = conn.prepareStatement(Main.resource.getString("qLeaveEvent"));
        leave.setInt(1, id);
        leave.setString(2, eventName);
        leave.execute();
    }

    /**
     * Method which returns the event id of the given event name.
     *
     * @param eventName name of the event
     * @param conn      connection to the database
     * @return returns the id
     * @throws Exception raises when an error occurs accessing
     */
    public static int getEventId(String eventName, Connection conn) throws Exception {
        int id = -1;


        PreparedStatement getId = conn.prepareStatement(Main.resource.getString("qGetEventId"));
        getId.setString(1, eventName);
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

        PreparedStatement getMaxId = conn.prepareStatement(Main.resource.getString("qGetMaxId"));
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
        PreparedStatement delAttendance = conn.prepareStatement(Main.resource.getString("qDeleteAllAtendance"));
        delAttendance.setInt(1, id);
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


        PreparedStatement delEventAttendance = conn.prepareStatement(Main.resource.getString("qDeleteAllEventsAttendance"));
        delEventAttendance.setInt(1, id);
        delEventAttendance.execute();
        PreparedStatement delEvent = conn.prepareStatement(Main.resource.getString("qDeleteAllEvents"));
        delEvent.setInt(1, id);
        delEvent.execute();
    }

}
