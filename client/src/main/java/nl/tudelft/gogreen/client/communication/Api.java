package nl.tudelft.gogreen.client.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.shared.*;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;


public class Api {

    /**
     * The currently used api, so we only have to change the value once...
     */
    public static Api current = getTestApi();


    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private int position;

    private static Api api;

    private static CookieStore cookies = new BasicCookieStore();

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

        for (PingPacketData d : PingPacketData.values()) {
            notifications.put(d, new ArrayList<>());
        }
        this.registerNotification(PingPacketData.FOLLOWER, System.out::println);
    }


    /**
     * Hidden black magic that produces nulls and stuff.
     *
     * @param url    the url to request
     * @param params a map of params
     * @return a string with the data needed
     * @throws UnirestException if unirest fucks up in a bad way?
     */
    private String post(String url, Map<String, Object> params) throws UnirestException {
        String holder = Unirest.post(url).fields(params).asString().getBody();
        System.out.println(holder);
        return holder;
    }

    /**
     * Log in as an existing user.
     *
     * @param username the username
     * @param password the password (what did you expect?)
     * @return true if success, false otherwise
     * @throws UnirestException if unirest fucks up in a bad way?
     */
    public boolean login(String username, String password) throws UnirestException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/login", maps);

        MessageHolder<Boolean> holder = gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());
        System.out.println(this.username);
        if (holder.getData() && this.username == null) {
            this.username = username;
            wsInit();
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
     * @throws UnirestException if unirest fucks up in a bad way?
     */
    public boolean register(String username, String password) throws UnirestException {
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
     * @throws UnirestException if unirest fucks up in a bad way?
     */
    public int addFeature(String featureName) throws UnirestException {
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
        String res;
        try {
            res = this.post(baseUrl + "/feature/total", new HashMap<>());
        } catch (UnirestException e) {
            e.printStackTrace();
            return 0;
        }
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
    private List<Integer> getFor(String username) {
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            res = this.post(baseUrl + "/feature/points", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return Arrays.asList(0, 0, 0, 0);
        }
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
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("days", period);
        try {
            res = this.post(baseUrl + "/stats", params);
        } catch (UnirestException e) {
            return new DateHolder(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        }
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
     * Get the position of the user on their leaderboard
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
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            res = this.post(baseUrl + "/follow/follow", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        MessageHolder<Boolean> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
                }.getType());
        followDirty = holder.getData();
        return holder.getData();
    }

    /**
     * Unfollow a user if their activities bore you.
     *
     * @param username the username to kick from your feed
     * @return whether you can be happy now
     */
    public boolean unfollow(String username) {
        String res;
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            res = this.post(baseUrl + "/follow/unfollow", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
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
        String res;
        Map<String, Object> params = new HashMap<>();
        try {
            res = this.post(baseUrl + "/follow/followers", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
        MessageHolder<Map<String, Integer>> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Map<String, Integer>>>() {
                }.getType());

        return holder.getData();
    }

    /**
     * And also the people you are following?.
     *
     * @return the peeps you're following
     */
    public Map<String, Integer> getFollowing() {
        String res;
        Map<String, Object> params = new HashMap<>();
        try {
            res = this.post(baseUrl + "/follow/following", params);
        } catch (UnirestException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
        MessageHolder<Map<String, Integer>> holder =
                gson.fromJson(res, new TypeToken<MessageHolder<Map<String, Integer>>>() {
                }.getType());

        return holder.getData();
    }


    private void wsInit() {
        String url = baseUrl.replace("http", "ws") + "/ws";
        Map<String, String> cooks = new HashMap<>();
        cookies.getCookies().forEach(cookie -> cooks.put(cookie.getName(), cookie.getValue()));

        WebSocketClient client = new WebSocketClient(URI.create(url), cooks) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                this.send(gson.toJson(new PingPacket(PingPacketData.OPEN, Api.this.username)));

            }

            @Override
            public void onMessage(String message) {
                PingPacket packet = gson.fromJson(message, PingPacket.class);
                System.out.println(packet);
                notifications.get(packet.getDataType()).forEach(it -> it.run(packet.getData()));
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };
        client.connect();

    }

    private Map<PingPacketData, List<MessageRunnable>> notifications = new HashMap<>();

    /**
     * Register a new notification, it is added to the list so don't add it twice!
     *
     * @param type     the notification type to respond to
     * @param runnable the MessageRunnable to execute
     */
    public void registerNotification(PingPacketData type, MessageRunnable runnable) {
        notifications.get(type).add(runnable);
    }

    /**
     * The basic notification class
     */
    public interface MessageRunnable {
        /**
         * The called method when a notification is received
         *
         * @param data The data to receive, is always a string but may be json encoded data.
         */
        void run(String data);
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
        Unirest.setHttpClient(HttpClients.custom().setDefaultCookieStore(cookies).build());
    }

}
