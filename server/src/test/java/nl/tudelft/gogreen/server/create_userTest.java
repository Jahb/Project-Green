package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertTrue;


public class create_userTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @After
    public void deleteUser() {
        try {

            Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            int id = new_feature.getId("paul",conn);
            create_user.delete_user(id, conn);
        }
        catch(Exception exception){
            System.out.println("Error!");
        }
    }
@Test
public void create_userTest() {
    try {
        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
        create_user.create_user("paul", "paul", conn);
        PreparedStatement getHash = conn.prepareStatement("select password from user_table where username = 'paul';");
        ResultSet rs = getHash.executeQuery();
        String hash = null;
        while(rs.next()){
            hash = rs.getString(1);
        }
        assertTrue(BCrypt.checkpw("paul", hash));
    }
    catch (Exception exception){
        System.out.println("Error!");
    }
}

}

