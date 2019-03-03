package nl.tudelft.gogreen.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(u);
        Parent root = FXMLLoader.load(u);
        URL u = Main.class.getResource("/Login.fxml");
        Scene loginScene = new Scene(root, 920, 720);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
        Unirest.setObjectMapper(new ObjectMapper() {
            private Gson gson = new GsonBuilder().setPrettyPrinting().create();

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                return gson.fromJson(value, valueType);
            }

            @Override
            public String writeValue(Object value) {
                return gson.toJson(value);
            }
        });

        launch(args);
    }
}
