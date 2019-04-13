package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.achievements.Achievements;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class ShowAllAchievementsTest {

    @Before
    public void fix(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void addAchievement() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))) {


            List<String> comparator = new ArrayList<>();
            comparator.add("Get Streak of 5 Days");
            comparator.add("Filled Circle for First Time");
            comparator.add("Followed 5 People");
            comparator.add("Got Followed by Someone");
            comparator.add("Added First Food Activity");
            comparator.add("Added First Transport Activity");
            comparator.add("Added First Energy Activity");
            comparator.add("Got 10 Achievements");
            comparator.add("Played Everyday for 2 Weeks");
            comparator.add("Created an Event");
            comparator.add("Joined an Event");

            List<String> comparator2 = Achievements.showAllAchievements();

            assertEquals(comparator,comparator2);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}