package nl.tudelft.gogreen.shared.auth;

/**
 * A class that is sent back to the client when it sends a valid auth request.
 */
public class AuthAgreement {
    private boolean success;
    private String username;
    private String key;

    /**
     * Main constructor.
     * @param success true if the request was valid, and the auth was successful. false otherwise.
     * @param username the username of the requested account
     * @param key the to be used key of the user, or null if success == false
     */
    public AuthAgreement(boolean success, String username, String key) {
        this.success = success;
        this.username = username;
        this.key = key;
    }

    /**
     * Blame Jackson.
     */
    private AuthAgreement(){

    }

    /**
     *  Whether the auth was a success.
     * @return Whether the user is now authenticated.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *  The username.
     * @return The username of the requested user.
     */
    public String getUsername() {
        return username;
    }

    /**
     *  The password.
     * @return if successful the key of the user, else null.
     */
    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof AuthAgreement)) return false;

        AuthAgreement that = (AuthAgreement) other;

        if (isSuccess() != that.isSuccess()) return false;
        if (!getUsername().equals(that.getUsername())) return false;
        return getKey() != null ? getKey().equals(that.getKey()) : that.getKey() == null;
    }

    @Override
    public int hashCode() {
        int result = isSuccess() ? 1 : 0;
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthAgreement{" +
                "success=" + success +
                ", username='" + username + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
