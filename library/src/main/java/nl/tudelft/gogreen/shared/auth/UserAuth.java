package nl.tudelft.gogreen.shared.auth;

/**
 * A class that can be sent to the server to request authentication from a client.
 */
public class UserAuth {
    private String username;
    private String password;

    /**
     * The main constructor, has to contain a username and password.
     *
     * @param username the username of the user that wants to be authed
     * @param password the password of the user, sent over https so plaintext is fine
     */
    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Needed for Spring to work, never use yourself.
     */
    private UserAuth() {
    }

    /**
     * The username of the user.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * The password of the user.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof UserAuth)) return false;

        UserAuth userAuth = (UserAuth) other;

        if (!getUsername().equals(userAuth.getUsername())) return false;
        return getPassword().equals(userAuth.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
