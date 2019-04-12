package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertTrue;


public class create_userTest {


    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @After
    public void deleteUser() {
        try( Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {


            int id = NewFeature.getId("paul", conn);
            CreateUser.delete_user(id, conn);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    @Test
    public void create_userTest() {
        try(Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {

            CreateUser.create_user("paul", "paul");
            System.out.println("hash");
            PreparedStatement getHash = conn.prepareStatement("select password from user_table where username = 'paul';");
            ResultSet rs = getHash.executeQuery();
            String hash = null;
            while (rs.next()) {
                hash = rs.getString(1);
            }
            assertTrue(BCrypt.checkpw("paul", hash));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}

