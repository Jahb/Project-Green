package nl.tudelft.gogreen.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;


public class AddAchievementTest {
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
//    public void addAchievement() {
//        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
//
//
//            Achievements.addAchievement(NewFeature.getId("paul",conn),"1000 points");
//            PreparedStatement getNameAchievement = conn.prepareStatement(resource.getString("qgetAchievements"));
//            getNameAchievement.setInt(1, NewFeature.getId("paul",conn));
//            String name = null;
//            ResultSet rs = getNameAchievement.executeQuery();
//            while(rs.next()){
//                name = rs.getString(1);
//            }
//            assertEquals("1000 points", name);
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
    @After
    public void deleteUser(){
        try (Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("paul",conn),conn);
        }catch (Exception exception) {
            System.out.println("Error!");
        }


    }

}
