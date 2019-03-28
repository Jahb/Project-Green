package nl.tudelft.gogreen.server;


import org.junit.Test;

import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;


public class ResourceBundleTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");





    @Test
    public void create_userTest() {
        try {
            String getAllUsersSql = resource.getString("getAllUsers");

            System.out.println("Testing to read property file. ");
            System.out.println("getAllUsers is: [" + getAllUsersSql + "]");
            assertEquals("select * from user_table;", getAllUsersSql);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

