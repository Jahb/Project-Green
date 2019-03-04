package nl.tudelft.gogreen.client;

import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {


    private int width = 920;
    private int height = 720;

    private Ring ring;

    @Override
    public void start(Stage primaryStage) throws Exception {
        long startTime = System.nanoTime();
        URL url = Main.class.getResource("/MainScreen.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        System.out.println((System.nanoTime() - startTime) / 1000000 / 1000.0);
        addRing(root);
        Scene loginScene = new Scene(root, width, height);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        ring.startAnimation();
    }

    /**
     * Adds Ring to Pane.
     *
     * @param root
     */
    private void addRing(AnchorPane root) {
        ring = new Ring((int) (150 * .75), 150, width / 2, 200);
        ring.addSegment(20, Color.LAWNGREEN);
        ring.addSegment(30, Color.YELLOW);
        ring.addSegment(40, Color.SANDYBROWN);
        ring.addNodes(root);
    }


    /**
     * Main Method.
     *
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
