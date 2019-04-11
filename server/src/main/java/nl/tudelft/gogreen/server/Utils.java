package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.features.NewFeature;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Integer> verifyUsersValid(String... usernames) {
        List<Integer> uids = new ArrayList<>();
        for (String username : usernames) {
            int otherID = NewFeature.getUID(username);
            uids.add(otherID);
        }
        return uids;
    }

}
