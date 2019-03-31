package nl.tudelft.gogreen.client.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.shared.MessageHolder;

import java.util.HashMap;
import java.util.Map;

public class API {

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private String post(String url, Map<String, Object> params) throws UnirestException {
        String holder = Unirest.post(url).fields(params).asString().getBody();
        System.out.println(holder);
        return holder;
    }

    public boolean login(String username, String password) throws UnirestException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/login", maps);

        MessageHolder<Boolean> holder = gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());

        return holder.getData();
    }

    public boolean register(String username, String password) throws UnirestException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/user/new", maps);

        MessageHolder<Boolean> holder = gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());

        return holder.getData();
    }

    public int addFeature(String featureName) throws UnirestException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("feature", remap(featureName));
        String res = this.post(baseUrl + "/feature/new", maps);
        MessageHolder<Integer> holder = gson.fromJson(res, new TypeToken<MessageHolder<Integer>>() {
        }.getType());
        System.out.println(holder.getData());
        return holder.getData();
    }

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

    private Map<String, String> remapper = new HashMap<>();

    private String remap(String feature) {
        return remapper.get(feature);
    }


    private String baseUrl;

    private API(String baseUrl) {
        this.baseUrl = baseUrl;
        remapper.put("LocalProduce", "Local Product");
        remapper.put("Vegetarian", "Vegetarian Meal");
    }

    private static API api;

    public static API getTestApi() {
        if (api == null) api = new API("http://localhost:8080");
        return api;
    }

    public static API getApi() {
        if (api == null) api = new API("https://gogreen.voidcorp.nl");
        return api;
    }

    public static API current = getTestApi();

    public static void initAPI() {
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

    public double[] getRingSegmentValues(String ringName) {
        if (ringName.equals("MAIN")) {
            //TODO returns a list of [food, energy, transport] format, points out of 1000 for MAIN ring
            return new double[]{200, 400, 100};
        }

        if (ringName.equals("NEXT")) {
            //TODO returns a list of [food, energy, transport] format, points out of 1000 for NEXT ring
            return new double[]{400, 200, 200};
        }

        if (ringName.equals("PREVIOUS")) {
            //TODO returns a list of [food, energy, transport] format, points out of 1000 for PREVIOUS ring
            return new double[]{100, 200, 300};
        }
        return null;
    }

    public String getUsernamePREVIOUS() {
        //TODO returns the username of the 'previous' friend
        return "Steve";
    }

    public String getUsernameNEXT() {
        //TODO returns the username of the 'next' friend
        return "Emily";
    }

    public String getUsername() {
        //TODO returns the username of the user
        return "Rob";
    }
}
