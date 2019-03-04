package nl.tudelft.gogreen.client.communication;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.client.Main;
import nl.tudelft.gogreen.shared.MessageHolder;

import java.util.HashMap;
import java.util.Map;

public class API {

    private String post(String url, Map<String, Object> params) throws UnirestException {
        String holder = Unirest.post(url).fields(params).asString().getBody();
        System.out.println(holder);
        return holder;
    }

    private boolean loggedIn = false;

    public boolean login(String username, String password) throws UnirestException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/login", maps);

        MessageHolder<Boolean> holder = Main.gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());
        loggedIn = holder.getData();

        return loggedIn;
    }

    public boolean register(String username, String password) throws UnirestException {
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("password", password);
        String res = this.post(baseUrl + "/user/new", maps);

        MessageHolder<Boolean> holder = Main.gson.fromJson(res, new TypeToken<MessageHolder<Boolean>>() {
        }.getType());

        return holder.getData();
    }



    private String baseUrl;

    private API(String baseUrl) {
        this.baseUrl = baseUrl;
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

}
