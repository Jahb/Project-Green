package nl.tudelft.gogreen.server;

import javafx.util.Pair;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class retrieveFeaturesHistoryTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test

    public void retrieveFH() {
        try(

                Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password")))
        {

            Connection conn3 = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            Connection conn4 = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul","paul");
            NewFeature.aadding_feature("paul","Vegetarian Meal",20);
            NewFeature.aadding_feature("paul","Local Product",20);
            List<Pair<String,Date>> list = Statistics.retrieveFeaturesHistory(NewFeature.getId("paul",conn4));

//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = new Date();
//            String Date = dateFormat.format(date);
            String newString = "Vegetarian Meal";
            assertEquals(newString,list.get(0).getKey());

            CreateUser.delete_user(NewFeature.getId("paul",conn3),conn3);
        }
        catch (Exception exception){
            System.out.print(exception.getMessage());
        }
    }


}