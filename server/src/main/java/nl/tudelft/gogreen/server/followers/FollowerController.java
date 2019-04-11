package nl.tudelft.gogreen.server.followers;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @PostMapping("/follow")
    public MessageHolder<Boolean> follow(String username) throws IOException {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser(), username);
        System.out.println(users);
        if (users.stream().anyMatch(integer -> integer == -1)) {
            return new MessageHolder<>(users.toString(), false);
        }
        try {
            Following.Follow(users.get(0), users.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHolder<>("Follow", false);
        }
        PingPacket msg = new PingPacket(PingPacketData.FOLLOWER, getCurrentUser());
        WebSocketSession sess = LiveConnections.sessionMap.get(username);
        if (sess != null)
            sess.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
        return new MessageHolder<>("Follow", true);
    }

    @PostMapping("/unfollow")
    public MessageHolder<Boolean> unfollow(String username) {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser(), username);
        if (users.stream().anyMatch(integer -> integer == -1)) {
            return new MessageHolder<>("Unfollow", false);
        }
        try {
            Following.Unfollow(users.get(0), users.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHolder<>("Unfollow", false);
        }
        return new MessageHolder<>("Unfollow", true);
    }


    @PostMapping("/activity")
    public MessageHolder<Integer> activity(String username) {
        try {
            return new MessageHolder<>("Follower activity", NewFeature.getTotal(username));
        } catch (Exception e) {
            return new MessageHolder<>("Follower activity", 0);
        }
    }

    @PostMapping("/following")
    public MessageHolder<Map<String, Integer>> following() {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser());
        Map<String, Integer> result = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            List<String> following = Following.showAllFollowing(users.get(0), conn).stream().map(userid -> Following.toUsername(userid, conn)).collect(Collectors.toList());
            for (String s : following) {
                result.put(s, NewFeature.getTotal(s));
            }

        } catch (Exception e) {
            result.putAll(Collections.emptyMap());
        }
        return new MessageHolder<>("Following", result);
    }

    @PostMapping("/followers")
    public MessageHolder<Map<String, Integer>> followers() {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser());
        Map<String, Integer> result = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            List<String> followers = Following.showAllFollowers(users.get(0), conn).stream().map(userid -> Following.toUsername(userid, conn)).collect(Collectors.toList());
            for (String s : followers) {
                result.put(s, NewFeature.getTotal(s));
            }
        } catch (Exception e) {
            result.putAll(Collections.emptyMap());
        }
        return new MessageHolder<>("Followers", result);
    }

}
