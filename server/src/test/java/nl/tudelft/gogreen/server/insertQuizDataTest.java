package nl.tudelft.gogreen.server;

import org.junit.Test;

import java.sql.*;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class insertQuizDataTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test

    public void insertQuizTest() {
        try(

                Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))){

            Statistics.insertQuizData(1,20,20,true,20,20,20,20);

            PreparedStatement result = conn.prepareStatement(resource.getString("qReturnQuiz"));

            ResultSet rs = result.executeQuery();

            int monthly = 0;
            while (rs.next()) {
                monthly = rs.getInt(1);
            }

            assertEquals(monthly,20);

            PreparedStatement result2 = conn.prepareStatement(resource.getString("qDeleteQuiz"));
            result2.execute();
        }
        catch (Exception exception){
            System.out.println("Error!");
        }
    }

}