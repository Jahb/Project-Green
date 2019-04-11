package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class getTotalCategoryTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Before
    public void createUser(){
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getTotalCategory() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {


            int id = NewFeature.getId("paul", conn);
            int oldTotal = NewFeature.getTotalCategory1(id,"c1",conn);
            NewFeature.adding_feature("paul", "Vegetarian Meal","20");
            int newTotal = NewFeature.getTotalCategory1(id,"c1",conn);
            assertNotEquals(oldTotal,newTotal);
            oldTotal +=20;
            assertEquals(oldTotal,newTotal);
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }
    @After
    public void deleteUser(){
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("paul",conn),conn);
        }catch (Exception exception) {
            System.out.println("Error!");
        }


    }

}
