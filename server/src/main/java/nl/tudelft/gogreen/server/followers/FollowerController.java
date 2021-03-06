package nl.tudelft.gogreen.server.followers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.server.Main;
import nl.tudelft.gogreen.server.Utils;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.server.websocket.LiveConnections;
import nl.tudelft.gogreen.shared.MessageHolder;
import nl.tudelft.gogreen.shared.PingPacket;
import nl.tudelft.gogreen.shared.PingPacketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/follow")
public class FollowerController {

    private ObjectMapper mapper;

    public FollowerController(@Autowired ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Get the current username.
     *
     * @return the username
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
     * Follow another user.
     *
     * @param username the username to follow
     * @return a boolean indicating success
     */
    @PostMapping("/follow")
    public MessageHolder<Boolean> follow(String username) {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser(), username);
        System.out.println(users);
        if (users.stream().anyMatch(integer -> integer == -1)) {
            return new MessageHolder<>(users.toString(), false);
        }
        try {
            Following.follow(users.get(0), users.get(1));
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageHolder<>("Follow", false);
        }
        PingPacket msg = new PingPacket(PingPacketData.FOLLOWER, getCurrentUser());
        WebSocketSession sess = LiveConnections.sessionMap.get(username);
        if (sess != null) {
            try {
                sess.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new MessageHolder<>("Follow", true);
    }

    /**
     * Unfollow a user.
     * @param username the user to unfollow
     * @return a boolean indicating success
     */
    @PostMapping("/unfollow")
    public MessageHolder<Boolean> unfollow(String username) {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser(), username);
        if (users.stream().anyMatch(integer -> integer == -1)) {
            return new MessageHolder<>("Unfollow", false);
        }
        try {
            Following.unfollow(users.get(0), users.get(1));
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageHolder<>("Unfollow", false);
        }
        return new MessageHolder<>("Unfollow", true);
    }

    /**
     * Get the users you are following, and their total amount of points.
     * @param username the username you want this info for
     * @return a map of people following you to their total points
     */
    @PostMapping("/following")
    public MessageHolder<Map<String, Integer>> following(String username) {
        List<Integer> users = Utils.verifyUsersValid(username);
        Map<String, Integer> result = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            List<String> following = Following.showAllFollowing(users.get(0),
                    conn).stream().map(userid ->
                    Following.toUsername(userid, conn)).collect(Collectors.toList());
            for (String s : following) {
                result.put(s, NewFeature.getTotal(s));
            }

        } catch (SQLException e) {
            result.putAll(Collections.emptyMap());
        }
        return new MessageHolder<>("Following", result);
    }

    /**
     * Get your followers, and their total amount of points.
     * @param username the username you want this info for
     * @return a map of followers to their total points
     */
    @PostMapping("/followers")
    public MessageHolder<Map<String, Integer>> followers(String username) {
        List<Integer> users = Utils.verifyUsersValid(username);
        Map<String, Integer> result = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            List<String> followers = Following.showAllFollowers(users.get(0),
                    conn).stream().map(userid ->
                    Following.toUsername(userid, conn)).collect(Collectors.toList());
            for (String s : followers) {
                result.put(s, NewFeature.getTotal(s));
            }
        } catch (SQLException e) {
            result.putAll(Collections.emptyMap());
        }
        return new MessageHolder<>("Followers", result);
    }

}
