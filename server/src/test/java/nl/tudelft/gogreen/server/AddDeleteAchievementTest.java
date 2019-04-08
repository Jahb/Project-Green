package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class AddDeleteAchievementTest {
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


            Achievements.addAchievement(NewFeature.getId("paul",conn),1);
            PreparedStatement getIdAchievement = conn.prepareStatement(resource.getString("qgetAchievements"));
            getIdAchievement.setInt(1, NewFeature.getId("paul",conn));
            int id = 0;
            ResultSet rs = getIdAchievement.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
            assertEquals(1, id);

            Achievements.deleteAchievement(NewFeature.getId("paul",conn),1);
            PreparedStatement getIdAchievement2 = conn.prepareStatement(resource.getString("qgetAchievements"));
            getIdAchievement2.setInt(1, NewFeature.getId("paul",conn));
            int id2 = 0;
            ResultSet rs2 = getIdAchievement2.executeQuery();
            while(rs2.next()){
                id2 = rs2.getInt(1);
            }
            assertEquals(0,id2);
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
