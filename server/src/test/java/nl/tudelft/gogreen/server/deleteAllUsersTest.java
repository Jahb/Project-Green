package nl.tudelft.gogreen.server;


import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class deleteAllUsersTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");



    @Test
    public void deleteAllUsers() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){


            CreateUser.deleteAllUsers(conn);
            PreparedStatement stmt = conn.prepareStatement("select count(*) from user_table");
            ResultSet rs = stmt.executeQuery();
            int count = -1;

            while (rs.next()) {

               count = rs.getInt(1);
            }
            assertEquals(0,count);
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}