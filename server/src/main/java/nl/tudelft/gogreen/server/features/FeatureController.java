package nl.tudelft.gogreen.server.features;

import nl.tudelft.gogreen.shared.MessageHolder;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/feature")
public class FeatureController {

    /**
     * Add a new feature to the current user.
     * @param feature the feature name
     * @param userInput the amount of times it was done? I have no idea tbh
     * @return an integer containing the total amount of points
     */
    @PostMapping("/new")
    public MessageHolder<Integer> addNew(@RequestParam String feature,
                                         @RequestParam(required = false) String userInput) {
        getUserObject();
        System.out.printf("%s: %s", getUserObject(), feature);
        String feat = "0";
        try {
            feat = NewFeature.adding_feature(getUserObject(), feature, userInput);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new MessageHolder<>("Nice!", Integer.parseInt(feat));
    }

    /**
     * Get the total amount of points for the current user.
     * @return the total amount of points
     */
    @PostMapping("/total")
    public MessageHolder<Integer> getTotal() {
        int total = 0;
        try {
            total = NewFeature.getTotal(getUserObject());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new MessageHolder<>("Nice!", total);
    }

    /**
     * Get a list of points in the normal divided format.
     * @param username the username that we want the points for
     * @return the list
     */
    @PostMapping("/points")
    public MessageHolder<List<Integer>> getPoints(@RequestParam String username) {
        List<Integer> list = new ArrayList<>();
        for (int i : NewFeature.getPontsPerCategory(username)) {
            list.add(i);
        }
        return new MessageHolder<>("Nice data you got there", list);
    }

    /**
     * Get the current username.
     * @return the username
     */
    public String getUserObject() {
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
     * Get the current streak.
     * @return the streak length
     */
    @PostMapping("/streak")
    public MessageHolder<Integer> getStreak() {
        try {
            return new MessageHolder<>("streak",
                    NewFeature.getStreak(NewFeature.getUid(getUserObject())));
        } catch (SQLException e) {
            return new MessageHolder<>("Streak");
        }


    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageHolder<Integer> exceptions() {
        return new MessageHolder<>("There was an error?", -1);
    }
}
