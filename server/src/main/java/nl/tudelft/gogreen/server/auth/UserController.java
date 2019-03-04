package nl.tudelft.gogreen.server.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/new")
    public String createNew(@RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);
        boolean res = VerifyUser.addNewUser(username, password);
        if (res) {
            return "Success!";
        }
        return "Error, User already exists";
    }

}
