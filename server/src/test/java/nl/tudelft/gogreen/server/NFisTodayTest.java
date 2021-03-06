package nl.tudelft.gogreen.server;


import nl.tudelft.gogreen.server.features.NewFeature;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class NFisTodayTest {


    @Before
    public void replaceDb(){
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);

    }

    @Test
    public void isTodayTestPositiveScenario() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String date2 = dateFormat.format(date);
            assertTrue(NewFeature.isToday(date2));
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }
    @Test
    public void isTodayTestNegativeScenario() {
        try (Connection conn = DriverManager.getConnection(Main.resource.getString("Postgresql.datasource.url"), Main.resource.getString("Postgresql.datasource.username"), Main.resource.getString("Postgresql.datasource.password"))){

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Create a calendar object with today date. Calendar is in java.util pakage.
            Calendar calendar = Calendar.getInstance();

            /* Move calendar to yesterday */
            calendar.add(Calendar.DATE, -1);

            // Get current date of calendar which point to the yesterday now
            Date yesterdayDate = calendar.getTime();
            String yesterday = dateFormat.format(yesterdayDate);
            assertFalse(NewFeature.isToday(yesterday));
        } catch (Exception exception) {
            System.out.println("Error!");
        }
    }

}

