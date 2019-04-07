package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/follow")
public class FollowerController {



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

    @PostMapping("/follow")
    public MessageHolder<Boolean> follow(String username) {
        List<Integer> users = Utils.verifyUsersValid(getCurrentUser(), username);
        if (users.stream().anyMatch(integer -> integer == -1)) {
            return new MessageHolder<>("Follow", false);
        }
        try {
            Following.Follow(users.get(0), users.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHolder<>("Follow", false);
        }
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
