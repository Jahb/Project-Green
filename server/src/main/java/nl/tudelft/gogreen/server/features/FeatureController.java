package nl.tudelft.gogreen.server.features;

import nl.tudelft.gogreen.shared.MessageHolder;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/feature")
public class FeatureController {

    @PostMapping("/new")
    public MessageHolder<Integer> addNew(@RequestParam String feature, @RequestParam(required = false) String userInput) {
        getUserObject();
        System.out.printf("%s: %s",  getUserObject(), feature);
        String feat = null;
        try {
            feat = NewFeature.adding_feature( getUserObject(), feature,userInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MessageHolder<>("Nice!", Integer.parseInt(feat));
    }

    @PostMapping("/total")
    public MessageHolder<Integer> getTotal() {
        int total = 0;
        try {
            total = NewFeature.getTotal(getUserObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MessageHolder<>("Nice!", total);
    }

    @PostMapping("/points")
    public MessageHolder<List<Integer>> getPoints(@RequestParam String username) {
        List<Integer> l = new ArrayList<>();
        for (int i : NewFeature.getPontsPerCategory(username)) {
            l.add(i);
        }
        return new MessageHolder<>("Nice data you got there", l);
    }

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

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageHolder<Integer> exceptions() {
        return new MessageHolder<>("There was an error?", -1);
    }
}
