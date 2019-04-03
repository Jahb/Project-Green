package nl.tudelft.gogreen.server;


import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;


public class ResourceBundleTest {



    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }


    @Test
    public void create_userTest() {
        try {
            String getAllUsersSql = Main.resource.getString("getAllUsers");

            System.out.println("Testing to read property file. ");
            System.out.println("getAllUsers is: [" + getAllUsersSql + "]");
            assertEquals("select * from user_table;", getAllUsersSql);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

