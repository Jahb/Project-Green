package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NFaddingToLogTest {



    @Before
    public void createUserWithFeature(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try {
            CreateUser.create_user("Leo", "Messi");

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void addingToLog() {
        try( Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {

            int id = NewFeature.getId("Leo",conn);
            PreparedStatement getCount = conn.prepareStatement(Main.resource.getString("qGetCountfeatures"));
            getCount.setInt(1,id);
            ResultSet number = getCount.executeQuery();
            int prevTotal  = -1;
            while(number.next()){
                prevTotal = number.getInt(1);
            }

            NewFeature.addingToLog(id,conn,"Vegetarian Meal");
            PreparedStatement getCount2 = conn.prepareStatement(Main.resource.getString("qGetCountfeatures"));
            getCount2.setInt(1,id);
            ResultSet number2 = getCount2.executeQuery();
            int afterTotal = -1;
            while(number2.next()){
                afterTotal = number2.getInt(1);
            }


            assertNotEquals(prevTotal,afterTotal);
            assertEquals(++prevTotal,afterTotal);


        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    @After
    public void restoreLog() throws Exception{

        try( Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))){
            CreateUser.delete_user(NewFeature.getId("Leo",conn),conn);


        }


    }




}