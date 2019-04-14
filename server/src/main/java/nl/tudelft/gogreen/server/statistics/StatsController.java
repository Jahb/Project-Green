package nl.tudelft.gogreen.server.statistics;

import javafx.util.Pair;
import nl.tudelft.gogreen.server.Main;
import nl.tudelft.gogreen.server.Utils;
import nl.tudelft.gogreen.server.features.NewFeature;
import nl.tudelft.gogreen.shared.DateHolder;
import nl.tudelft.gogreen.shared.DatePeriod;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {


    /**
     * Get the current user.
     * @return the current user
     */
    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    /**
     * Get the stats for a specified period.
     * @param days the amount of days
     * @return a dateholder object
     */
    @PostMapping
    public MessageHolder<DateHolder> getFor(DatePeriod days) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            int uid = Utils.verifyUsersValid(getCurrentUser()).get(0);
            if (uid < 0) throw new SQLException("Yeet!");
            double[] data = Statistics.getLastData(uid, days.getIndex(), conn);
            conn.close();
            return new MessageHolder<>("Dates", new DateHolder(data));
        } catch (SQLException e) {
            return new MessageHolder<>("Dates",
                    new DateHolder(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0}));
        }
    }

    /**
     * Get the user history.
     * @param username the username for who you want the history
     * @return a list of pairs, of the features and dates
     */
    @PostMapping("/hist")
    public MessageHolder<List<Pair<String, Date>>> getHistory(String username) {
        try {
            return new MessageHolder<>("history",
                    Statistics.retrieveFeaturesHistory(NewFeature.getUid(username)));
        } catch (SQLException e) {
            return new MessageHolder<>("history", Collections.emptyList());
        }
    }

    /**
     * Add the entry quiz.
     * @param monthlyIncome the monthly income
     * @param householdSize the size of the household
     * @param ownVehicle the vehicles they own
     * @param energyBill their energy bill
     * @param houseSurface the total surface of the house
     * @return a boolean indicating success
     */
    @PostMapping("/quiz")
    public MessageHolder<Boolean> insertQuizData(int monthlyIncome,
                                                 int householdSize, boolean ownVehicle,
                                                 int energyBill, int houseSurface) {
        try {
            Statistics.insertQuizData(NewFeature.getUid(getCurrentUser()),
                    monthlyIncome, householdSize, ownVehicle, energyBill, houseSurface);
        } catch (SQLException e) {
            return new MessageHolder<>("quiz", false);

        }
        return new MessageHolder<>("quiz", true);
    }



}
