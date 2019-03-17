package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/feature")
public class FeatureController {

    @PostMapping("/new")
    public MessageHolder<Integer> addNew(@RequestParam String feature) throws Exception {
        getUserObject();
        System.out.printf("%s: %s",  getUserObject(), feature);
        String f = NewFeature.adding_feature( getUserObject(), feature);
        return new MessageHolder<>("Nice!", Integer.parseInt(f));
    }

    @PostMapping("/total")
    public MessageHolder<Integer> getTotal() throws Exception {

        return new MessageHolder<>("Nice!", NewFeature.getTotal( getUserObject()));

    }

    private String getUserObject() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
