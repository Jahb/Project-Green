package nl.tudelft.gogreen.server.auth;

import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/new")
    public MessageHolder<Boolean> createNew(@RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);
        boolean res = VerifyUser.addNewUser(username, password);
        if (res) {
            return new MessageHolder<>("New user created!", true);
        }
        return new MessageHolder<>("User already exists", false);
    }

}
