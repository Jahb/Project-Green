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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.gogreen.client.LeaderboardController;

import java.io.IOException;

public class Main extends Application {

    private static int width = 1000;
    private static int height = 720;
    private static Stage stage;
    private static MainScreen mainScreen = new MainScreen();
    private static ProfileController profileScreen = new ProfileController();
    private static LeaderboardController leaderboardScreen = new LeaderboardController();
    private static AchievementsController achievementsScreen = new AchievementsController();


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
        primaryStage.setTitle("Project Green: CO2 Reduction Tracker");
        primaryStage.getIcons().add(new Image("images/addButton.png"));

        openLoginScreen();
        stage.show();

        System.out.println("Initialization code took " +
                ((System.nanoTime() - startTime) / 1000000 / 1000.0) + "s");
    }

    /**
     * Method that changes scene to Login Screen.
     */
    public static void openLoginScreen() {
        try {
            Parent root1 = FXMLLoader.load(Main.class.getResource("/Login.fxml"));
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that changes scene to MainScreen.
     */
    public static void openMainScreen() {
        try {
            stage.setScene(mainScreen.getScene());
            stage.setTitle("Go Green: MainScreen");
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
            stage.setScene(profileScreen.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    /**
     * Method that changes scene to the AchievementsScreen.
     */
    public static void openAchievementsScreen() {
        try {
            stage.setScene(achievementsScreen.getScene());
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
