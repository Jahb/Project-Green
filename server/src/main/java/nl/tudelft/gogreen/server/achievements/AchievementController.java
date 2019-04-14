package nl.tudelft.gogreen.server.achievements;

import nl.tudelft.gogreen.server.Main;
import nl.tudelft.gogreen.server.Utils;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    /**
     * Get the achievements for a user.
     *
     * @param username the username
     * @return a messageholder that has all the achievements for the user
     */
    @PostMapping("/for")
    public MessageHolder<List<Integer>> getFor(@RequestParam String username) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            int uid = Utils.verifyUsersValid(username).get(0);
            if (uid < 0) throw new SQLException("Yeet!");
            List<Integer> ach = new ArrayList<>(
                    new HashSet<>(Achievements.showAchievementsForUser(uid)));
            conn.close();
            return new MessageHolder<>("Dates", ach);
        } catch (SQLException e) {
            return new MessageHolder<>("Dates", Collections.emptyList());
        }
    }

    /**
     * Get the names of the achievements.
     *
     * @return the names
     */
    @PostMapping("/names")
    public MessageHolder<List<String>> getNames() {
        try {
            return new MessageHolder<>("Achievements", Achievements.showAllAchievements());
        } catch (SQLException e) {
            return new MessageHolder<>("Achievements", Collections.emptyList());
        }
    }

}
