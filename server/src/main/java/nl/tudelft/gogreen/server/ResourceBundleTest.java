package nl.tudelft.gogreen.server;
import java.util.ResourceBundle;

public class ResourceBundleTest {

    // Telling which is the properties file
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * This class checks if we can read the properties file.
     *
     * @param args user input
     */
    public static void main(String[] args) {

        // Read a property from file of properties and assign it to an String
        String getAllUsersSql = resource.getString("getAllUsers");

        System.out.println("Testing to read property file. ");
        System.out.println("getAllUsers is: [" + getAllUsersSql + "]");
    }

}