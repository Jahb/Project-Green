package nl.tudelft.gogreen.server;

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
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventsController {


    private String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    @PostMapping("/new")
    public MessageHolder<Boolean> addNew(String name, String description, String date, String time) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            EventsMain.create_event(getCurrentUser(), name, description, date, time, conn);
        } catch (Exception e) {
            return new MessageHolder<>("Create Event", false);
        }

        return new MessageHolder<>("Create Event", true);
    }

    @PostMapping("/list")
    public MessageHolder<List<EventItem>> list() throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        List<EventItem> items = EventsMain.get_events(conn);
        conn.close();
        return new MessageHolder<>("Events", items);
    }

    @PostMapping("/user")
    public MessageHolder<List<EventItem>> userEvents() throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        List<EventItem> items = EventsMain.get_user_events(getCurrentUser(), conn);
        conn.close();
        return new MessageHolder<>("Events", items);
    }

    @PostMapping("/join")
    public MessageHolder<Boolean> join(String eventName) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            EventsMain.join_event(getCurrentUser(), eventName, conn);
        } catch (Exception e) {
            return new MessageHolder<>("Join", false);
        }

        return new MessageHolder<>("Join", true);
    }

    @PostMapping("/leave")
    public MessageHolder<Boolean> leave(String eventName) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            EventsMain.leave_event(getCurrentUser(), eventName, conn);
        } catch (Exception e) {
            return new MessageHolder<>("Leave", false);
        }

        return new MessageHolder<>("Leave", true);
    }

}
