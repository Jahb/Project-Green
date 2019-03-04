package nl.tudelft.gogreen.server.auth;

import nl.tudelft.gogreen.shared.auth.AuthAgreement;
import nl.tudelft.gogreen.shared.auth.UserAuth;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerifyUser {
    /**
     * This method finds the user by username in the database.
     *
     * @param username the user to find
     * @return The requested user
     */
    public static User findUser(String username) {
        //STUB
        String pw = users.get(username);
        if (pw == null) return null;
        return new User(username, pw);
    }

    private static Map<String, String> users = new HashMap<>();

    public static boolean addNewUser(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, BCrypt.hashpw(password, BCrypt.gensalt()));
        return true;
    }

    /**
     * This method verifies the password sent against the one stored.
     *
     * @param user     The user object obtained by calling findUser
     * @param password The password to check
     * @return whether the passwords match
     */
    public static boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    /**
     * This method creates a key for the user to auth with.
     *
     * @param username the username in question
     * @return The generated key
     */
    public static String storeKey(String username) {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder key = new StringBuilder();
        random.ints().limit(16).boxed().forEach(i -> {

            String hex = Integer.toHexString(i);
            while (hex.length() < 8) {
                hex = '0' + hex;
            }
            key.append(hex);
        });
        return key.toString();
    }

    /**
     * Checks whether the user can be authenticated.
     * <<<<<<< HEAD
     * =======
     * <p>
     * >>>>>>> master
     *
     * @param user the user to check
     * @return an AuthAgreement whether the user is valid or not
     */
    public static AuthAgreement authUser(UserAuth user) {
        User dbUser = findUser(user.getUsername());
        boolean check = checkPassword(dbUser, user.getPassword());
        if (check) {
            String key = storeKey(user.getUsername());
            return new AuthAgreement(true, user.getUsername(), key);
        }
        return new AuthAgreement(false, user.getUsername(), null);
    }
}
