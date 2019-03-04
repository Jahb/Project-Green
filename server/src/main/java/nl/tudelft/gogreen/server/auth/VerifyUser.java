package nl.tudelft.gogreen.server.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class VerifyUser {
    /**
     * This method finds the user by username in the database.
     *
     * @param username the user to find
     * @return The requested user, or null if the user does not exist
     */
    public static User findUser(String username) {
        String pw = users.get(username);
        if (pw == null) return null;
        return new User(username, pw);
    }

    private static Map<String, String> users = new HashMap<>();


    /**
     * Add a new user to the "database"
     *
     * @param username The username
     * @param password the password
     * @return a boolean whether the user is added (and thus didnt exist)
     */
    public static boolean addNewUser(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, BCrypt.hashpw(password, BCrypt.gensalt()));
        return true;
    }
}
