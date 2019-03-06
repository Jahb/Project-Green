package nl.tudelft.gogreen.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static int width = 1000;
    private static int height = 720;
    private static Stage stage;
    private static MainScreen mainScreen = new MainScreen();
    private static LoginScreen loginScreen = new LoginScreen();
    private static LeaderboardController leaderboardScreen = new LeaderboardController();


    static int getWidth() {
        return width;
    }

    static int getHeight() {
        return height;
    }

    @Override
    public void start(Stage primaryStage) {
        final long startTime = System.nanoTime();

        stage = primaryStage;
        openMainScreen();
        stage.show();

        System.out.println("Initialization code took " +
                ((System.nanoTime() - startTime) / 1000000 / 1000.0) + "s");
    }

    /**
     * Method that changes scene to LoginScreen.
     */
    public static void openLoginScreen() {
        try {
            stage.setScene(loginScreen.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    /**
     * Method that changes scene to MainScreen.
     */
    public static void openMainScreen() {
        try {
            stage.setScene(mainScreen.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }

    }

    /**
     * Method that changes scene to LeaderBoard.
     */
    public static void openLeaderboardScreen() {
        try {
            stage.setScene(leaderboardScreen.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    /**
     * Method that changes scene to ProfileScreen.
     */
    public static void openProfileScreen() {
        try {
            Parent root1 = FXMLLoader.load(Main.class.getResource("/ProfileGUI.fxml"));
            stage.setScene(new Scene(root1, width, height));
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    /**
     * Method that changes scene to the AchievementsScreen.
     */
    public static void openAchievementsScreen() {
        try {
            Parent root1 = FXMLLoader.load(Main.class.getResource("/SettingsGUI.fxml"));
            stage.setScene(new Scene(root1, width, height));
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    private static void pageOpenError(Exception ex) {
        ex.printStackTrace();
        Pane pane = new Pane();
        pane.getChildren().add(new Label("Something went wrong"));
        stage.setScene(new Scene(pane, width, height));
    }

    /**
     * Main Method.
     *
     * @param args Program arguments
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
