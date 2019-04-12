package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.features.NewFeature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Utils{

    public static List<Integer> verifyUsersValid(String... usernames) {
        try {
            List<Integer> uids = new ArrayList<>();
            Connection conn = DriverManager.getConnection(
                    Main.resource.getString("Postgresql.datasource.url"),
                    Main.resource.getString("Postgresql.datasource.username"),
                    Main.resource.getString("Postgresql.datasource.password"));
            for (String username : usernames) {
                int otherID = NewFeature.getId(username,conn);
                uids.add(otherID);
            }
            return uids;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;


    }
}
