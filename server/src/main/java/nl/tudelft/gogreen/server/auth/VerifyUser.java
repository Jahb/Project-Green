package nl.tudelft.gogreen.server.auth;

import nl.tudelft.gogreen.server.CreateUser;
import nl.tudelft.gogreen.server.LogIn;
import sun.rmi.runtime.Log;

public class VerifyUser {
    /**
     * This method finds the user by username in the database.
     *
     * @param username the user to find
     * @return The requested user, or null if the user does not exist
     */
    public static boolean logIn(String username, String password) throws Exception {

        return LogIn.log_in(username, password);
    }


    /**
     * Add a new user to the "database"
     *
     * @param username The username
     * @param password the password
     * @return a boolean whether the user is added (and thus didnt exist)
     */
    public static boolean addNewUser(String username, String password) throws Exception {
        return CreateUser.create_user(username, password);
    }
}
