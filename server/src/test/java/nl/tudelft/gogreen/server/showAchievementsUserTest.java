package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.server.statistics.Achievements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class showAchievementsUserTest {

    @Before
    public void createUser(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
            CreateUser.deleteAllUsers(conn);
            CreateUser.create_user("paul", "paul");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addAchievement() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {


            Achievements.addAchievement(NewFeature.getId("paul",conn),1);
            Achievements.addAchievement(NewFeature.getId("paul",conn),2);
            Achievements.addAchievement(NewFeature.getId("paul",conn),3);

            PreparedStatement getAchievements = conn.prepareStatement(Main.resource.getString("qgetAchievements"));
            getAchievements.setInt(1, NewFeature.getId("paul",conn));
            List<Integer> list = new ArrayList<>();
            ResultSet rs = getAchievements.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(1));
            }

            List<Integer> list2 = Achievements.showAchievementsForUser(NewFeature.getId("paul",conn));

            assertEquals(list,list2);

            Achievements.deleteAchievement(NewFeature.getId("paul",conn),1);
            Achievements.deleteAchievement(NewFeature.getId("paul",conn),2);
            Achievements.deleteAchievement(NewFeature.getId("paul",conn),3);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    @After
    public void deleteUser(){
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {
           CreateUser.delete_user(NewFeature.getId("paul",conn),conn);
        }catch (Exception exception) {
            System.out.println("Error!");
        }
    }
}