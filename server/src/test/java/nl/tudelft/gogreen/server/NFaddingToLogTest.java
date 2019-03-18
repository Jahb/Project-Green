package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class NFaddingToLogTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void addingToLog() {



    }
    @After
    public void restoreLog() throws Exception{


//        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
//                resource.getString("Postgresql.datasource.username"),
//                resource.getString("Postgresql.datasource.password"));


    }




}