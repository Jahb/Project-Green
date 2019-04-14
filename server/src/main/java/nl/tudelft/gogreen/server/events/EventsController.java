package nl.tudelft.gogreen.server.events;

import nl.tudelft.gogreen.server.Main;
import nl.tudelft.gogreen.shared.EventItem;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventsController {


    /**
     * Get the current user, based on a cookie.
     *
     * @return the current user
     */
    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    /**
     * Add a new event.
     *
     * @param name        the name of the event
     * @param description the description of the event
     * @param date        the date of the event, for some reason as a string
     * @param time        the time as an event, see date for why
     * @return a boolean indicating success
     */
    @PostMapping("/new")
    public MessageHolder<Boolean> addNew(String name,
                                         String description, String date, String time) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            EventsMain.create_event(getCurrentUser(), name, description, date, time, conn);
        } catch (SQLException e) {
            return new MessageHolder<>("Create Event", false);
        }

        return new MessageHolder<>("Create Event", true);
    }

    /**
     * Get a list of all events.
     *
     * @return the list
     */
    @PostMapping("/list")
    public MessageHolder<List<EventItem>> list() {
        List<EventItem> items = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            items.addAll(EventsMain.get_events(conn));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new MessageHolder<>("Events", items);
    }

    /**
     * get a list of the events the current user has joined.
     *
     * @return the list of the events
     */
    @PostMapping("/user")
    public MessageHolder<List<EventItem>> userEvents() {
        List<EventItem> items = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(
                    Main.resource.getString("Postgresql.datasource.url"),
                    Main.resource.getString("Postgresql.datasource.username"),
                    Main.resource.getString("Postgresql.datasource.password"));
            items.addAll(EventsMain.get_user_events(getCurrentUser(), conn));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new MessageHolder<>("Events", items);
    }

    /**
     * Join an event.
     *
     * @param eventName the name of the event
     * @return a boolean indicating success.
     */
    @PostMapping("/join")
    public MessageHolder<Boolean> join(String eventName) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            EventsMain.join_event(getCurrentUser(), eventName, conn);
        } catch (SQLException e) {
            return new MessageHolder<>("Join", false);
        }

        return new MessageHolder<>("Join", true);
    }

    /**
     * Leave an event.
     *
     * @param eventName the name of the event
     * @return a boolean indicating success
     */
    @PostMapping("/leave")
    public MessageHolder<Boolean> leave(String eventName) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            EventsMain.leave_event(getCurrentUser(), eventName, conn);
        } catch (SQLException e) {
            return new MessageHolder<>("Leave", false);
        }

        return new MessageHolder<>("Leave", true);
    }

}
