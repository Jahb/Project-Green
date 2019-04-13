package nl.tudelft.gogreen.server.auth;

import nl.tudelft.gogreen.server.followers.Following;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Add a new user.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return a boolean indicating success
     */
    @PostMapping("/new")
    public MessageHolder<Boolean> createNew(@RequestParam String username,
                                            @RequestParam String password) {
        return new MessageHolder<>("User already exists",
                CreateUser.create_user(username, password));
    }

    /**
     * Get a list of all users.
     * @return the list of all the users
     */
    @PostMapping("/all")
    public MessageHolder<List<String>> getAll() {
        try {
            return new MessageHolder<>("users",
                    Following.gettingAllUsers());
        } catch (SQLException e) {
            return new MessageHolder<>("users",
                    Collections.emptyList());
        }
    }

}
