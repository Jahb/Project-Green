package nl.tudelft.gogreen.server.statistics;

import nl.tudelft.gogreen.server.Main;
import nl.tudelft.gogreen.server.Utils;
import nl.tudelft.gogreen.shared.DateHolder;
import nl.tudelft.gogreen.shared.MessageHolder;
import nl.tudelft.gogreen.shared.DatePeriod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@RequestMapping("/stats")
public class StatsController {


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

    @PostMapping
    public MessageHolder<DateHolder> getFor(DatePeriod days) {
        try (Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"))) {
            int uid = Utils.verifyUsersValid(getCurrentUser()).get(0);
            if (uid < 0) throw new Exception("Yeet!");
            double[] data = Statistics.getLastData(uid, days.getIndex(), conn);
            conn.close();
            return new MessageHolder<>("Dates", new DateHolder(data));
        } catch (Exception e) {
            return new MessageHolder<>("Dates", new DateHolder(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0}));
        }
    }
}
