package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.features.NewFeature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    /**
     * verify a list of usernames.
     * @param usernames the usernames to verify
     * @return a list of uids
     */
    public static List<Integer> verifyUsersValid(String... usernames) {
        try {
            List<Integer> uids = new ArrayList<>();
            Connection conn = DriverManager.getConnection(
                    Main.resource.getString("Postgresql.datasource.url"),
                    Main.resource.getString("Postgresql.datasource.username"),
                    Main.resource.getString("Postgresql.datasource.password"));
            for (String username : usernames) {
                int otherID = NewFeature.getId(username, conn);
                uids.add(otherID);
            }
            return uids;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Collections.emptyList();


    }
}
