package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;


public class DeleteAchievementTest {
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
    public void addAchievement() {
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {


            Achievements.addAchievement(NewFeature.getId("paul",conn),"1000 points");
            PreparedStatement getNameAchievement = conn.prepareStatement("select achievement_name from achievements where user_id = ?");
            getNameAchievement.setInt(1, NewFeature.getId("paul",conn));
            String name = null;
            ResultSet rs = getNameAchievement.executeQuery();
            while(rs.next()){
                name = rs.getString(1);
            }
            assertEquals("1000 points", name);
            Achievements.deleteAchievement(NewFeature.getId("paul",conn),"1000 points");
            PreparedStatement getNameAchievement2 = conn.prepareStatement("select achievement_name from achievements where user_id = ?");
            getNameAchievement2.setInt(1, NewFeature.getId("paul",conn));
            String name2 = null;
            ResultSet rs2 = getNameAchievement2.executeQuery();
            while(rs2.next()){
                name2 = rs2.getString(1);
            }
            assertNull(name2);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
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
