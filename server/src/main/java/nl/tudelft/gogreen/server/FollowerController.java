package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowerController {

    @PostMapping("/new")
    public MessageHolder<Boolean> follow(String username) {
        return new MessageHolder<>("Follow", false);
    }

    @PostMapping("/delete")
    public MessageHolder<Boolean> unfollow(String username) {
        return new MessageHolder<>("Unfollow", false);
    }


    @PostMapping("/activity")
    public MessageHolder<Integer> activity(String username) {
        try {
            return new MessageHolder<>("Follower activity", NewFeature.getTotal(username));
        } catch (Exception e) {
            return new MessageHolder<>("Follower activity", 0);
        }
    }

}
