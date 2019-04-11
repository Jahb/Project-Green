package nl.tudelft.gogreen.client.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import nl.tudelft.gogreen.shared.DateHolder;
import nl.tudelft.gogreen.shared.DatePeriod;
import nl.tudelft.gogreen.shared.EventItem;
import nl.tudelft.gogreen.shared.MessageHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Api {

    /**
     * The currently used api, so we only have to change the value once...
     */

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Api current = getTestApi();
    private static Api api;

    private int position;

    private String username;

    private boolean followDirty = true;

    private Map<String, Integer> followers = Collections.emptyMap();


    private Map<String, String> remapper = new HashMap<>();

    private String baseUrl;

    /**
     * Create a new instance of the api using the provided baseurl.
     *
     * @param baseUrl the base url to use
     */
    private Api(String baseUrl) {
        this.baseUrl = baseUrl;
        remapper.put("Localproduce", "Local Product");
        remapper.put("Vegetarian", "Vegetarian Meal");
        remapper.put("Bike", "Usage of Bike");
        remapper.put("Publictransport", "Usage of Public Transport");
        remapper.put("Solarpanel", "Solar Panels");
        remapper.put("Recycle", "Recycling");
        remapper.put("NoSmoking", "Lower Temperature");


    }


    /**
     * Hidden black magic that produces nulls and stuff.
     *
     * @param url    the url to request
     * @param params a map of params
     * @return a string with the data needed
     */
    private String post(String url, Map<String, Object> params) {

        String holder = "";
        try {
            holder = Unirest.post(url).fields(params).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.out.println(holder);
        return holder;
    }

    /**
     * Log in as an existing user.
     *
     * @param username the username
     * @param password the password (what did you expect?)
     * @return true if success, false otherwise
     */
    public boolean login(String username, String password) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/login", maps);

        MessageHolder<Boolean> holder = gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());
        System.out.println(this.username);
        if (holder.getData() && this.username == null) {
            this.username = username;
            System.out.println(this.username);
        }
        return holder.getData();
    }

    /**
     * Register as a new user.
     *
     * @param username the new username
     * @param password the password
     * @return true if success, false otherwise
     */
    public boolean register(String username, String password) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/user/new", maps);

        MessageHolder<Boolean> holder = gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());
        if (holder.getData()) {
            this.username = username;
        }
        return holder.getData();
    }

    /**
     * Register a feature.
     *
     * @param featureName the name of the feature, should really be an Enum...
     * @return the current total points
     */
    public int addFeature(String featureName) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("feature", remap(featureName));
        String res = this.post(baseUrl + "/feature/new", maps);
        MessageHolder<Integer> holder = gson.fromJson(res, new TypeToken<MessageHolder<Integer>>() {
        }.getType());
        System.out.println(holder.getData());
        return holder.getData();
    }

    /**
     * Get the currently logged in users total points.
     *
     * @return the total amount of points
     */
    public int getTotal() {
        String res = this.post(baseUrl + "/feature/total", new HashMap<>());
        MessageHolder<Integer> holder = gson.fromJson(res, new TypeToken<MessageHolder<Integer>>() {
        }.getType());

        return holder.getData();
    }

    /**
     * Get the current usage for the user.
     *
     * @param username the username to find
     * @return the co2 saved
     */
    public List<Integer> getFor(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        String res = this.post(baseUrl + "/feature/points", params);
        MessageHolder<List<Integer>> holder = gson.fromJson(res, new TypeToken<MessageHolder<List<Integer>>>() {
        }.getType());

        return holder.getData();
    }

    /**
     * Get the progress in the past x dates.
     *
     * @param period the date period to look through
     * @return the information requested
     */
    public DateHolder getDatesFor(DatePeriod period) {
        Map<String, Object> params = new HashMap<>();
        params.put("days", period);
        String res = this.post(baseUrl + "/stats", params);
        MessageHolder<DateHolder> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<DateHolder>>() {
                }.getType());

        return holder.getData();
    }

    /**
     * Get the ring segment values,
     * for now just the first one since we cant really discern between the different ones.
     *
     * @param ringName the ring to look up
     * @return the value for the ring!
     */
    public double[] getRingSegmentValues(String ringName) {
        if (ringName.equals("MAIN")) {
            List<Integer> res = getFor(getUsername());
            return new double[]{res.get(0), res.get(1), res.get(2)};
        }

        if (ringName.equals("NEXT")) {
            if (getUsernameNext() == null) {
                return new double[]{333, 334, 333};
            }
            List<Integer> res = getFor(getUsernameNext());
            return new double[]{res.get(0), res.get(1), res.get(2)};
        }

        if (ringName.equals("PREVIOUS")) {
            if (getUsernamePrevious() == null) {
                return new double[]{333, 334, 333};
            }
            List<Integer> res = getFor(getUsernamePrevious());
            return new double[]{res.get(0), res.get(1), res.get(2)};
        }
        return null;
    }

    /**
     * Get the username of the previous user on the leaderboard.
     *
     * @return Guess what?
     */
    public String getUsernamePrevious() {
        int myTotal = getTotal();
        if (followDirty) {
            followers = getFollowing();
            followDirty = false;
        }
        List<Map.Entry<String, Integer>> yeet =
                followers.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        yeet.removeIf(it -> it.getValue() > myTotal);
        position = followers.size() - yeet.size() + 1;
        if (yeet.size() == 0) return null;
        return yeet.get(0) == null ? null : yeet.get(0).getKey();

    }

    /**
     * See above: but now with the next one...
     *
     * @return Again, see above
     */
    public String getUsernameNext() {
        int myTotal = getTotal();
        if (followDirty) {
            followers = getFollowing();
            followDirty = false;
        }
        List<Map.Entry<String, Integer>> yeet =
                followers.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(Collectors.toList());
        yeet.removeIf(it -> it.getValue() < myTotal);
        if (yeet.size() == 0) return null;
        return yeet.get(0) == null ? null : yeet.get(0).getKey();
    }

    /**
     * Whats my name?.
     *
     * @return now you know
     */
    public String getUsername() {
        System.out.println(this.username);
        return this.username;
    }

    /**
     * Get the position of the user on their leaderboard.
     *
     * @return the users position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Follow another user to compare your progress with theirs.
     *
     * @param username the user you want to follow
     * @return a boolean indicating success
     */
    public boolean follow(String username) {
        return getfollow(username, "/follow/follow");

    }

    /**
     * Unfollow a user if their activities bore you.
     *
     * @param username the username to kick from your feed
     * @return whether you can be happy now
     */
    public boolean unfollow(String username) {

        return getfollow(username, "/follow/unfollow");

    }

    private boolean getfollow(String username, String url) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        String res = this.post(baseUrl + url, params);
        MessageHolder<Boolean> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
                }.getType());
        return holder.getData();
    }

    /**
     * Who doesn't want to know their followers?.
     *
     * @return your followers
     */
    public Map<String, Integer> getFollowers() {
        return getStringIntegerMap("/follow/followers");
    }

    /**
     * And also the people you are following?.
     *
     * @return the peeps you're following
     */
    public Map<String, Integer> getFollowing() {
        return getStringIntegerMap("/follow/following");
    }

    private Map<String, Integer> getStringIntegerMap(String s) {
        String res;
        Map<String, Object> params = new HashMap<>();
        res = this.post(baseUrl + s, params);
        MessageHolder<Map<String, Integer>> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Map<String, Integer>>>() {
                }.getType());

        return holder.getData();
    }

    public List<EventItem> getAllEvents() {
        String res;
        Map<String, Object> params = new HashMap<>();
        try {
            res = this.post(baseUrl + "/event/list", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        MessageHolder<List<EventItem>> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<List<EventItem>>>() {
                }.getType());

        return holder.getData();
    }

    public List<EventItem> getUserEvents() {
        String res;
        Map<String, Object> params = new HashMap<>();
        try {
            res = this.post(baseUrl + "/event/user", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        MessageHolder<List<EventItem>> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<List<EventItem>>>() {
                }.getType());

        return holder.getData();
    }

    public boolean newEvent(EventItem event) {
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("name", event.getName());
        params.put("description", event.getDescription());
        params.put("date", event.getDate());
        params.put("time", event.getTime());
        try {
            res = this.post(baseUrl + "/event/new", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        MessageHolder<Boolean> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
                }.getType());

        return holder.getData();
    }

    public boolean joinEvent(String event) {
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("eventName", event);
        try {
            res = this.post(baseUrl + "/event/join", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        MessageHolder<Boolean> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
                }.getType());

        return holder.getData();
    }

    public boolean leaveEvent(String event) {
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("eventName", event);
        try {
            res = this.post(baseUrl + "/event/leave", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        MessageHolder<Boolean> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
                }.getType());

        return holder.getData();
    }

    private String remap(String feature) {
        return remapper.get(feature);
    }

    /**
     * Get the localhost test api.
     *
     * @return an API instance
     */
    public static Api getTestApi() {
        if (api == null) api = new Api("http://localhost:8080");
        return api;
    }

    /**
     * Get the production api.
     *
     * @return an API instance
     */
    public static Api getApi() {
        if (api == null) api = new Api("https://gogreen.voidcorp.nl");
        return api;
    }

    /**
     * Init the api, aka set up an objectmapper and stuff.
     */
    public static void initApi() {
        Unirest.setObjectMapper(new ObjectMapper() {

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                return gson.fromJson(value, valueType);
            }

            @Override
            public String writeValue(Object value) {
                return gson.toJson(value);
            }
        });
    }

}
