package nl.tudelft.gogreen.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.gogreen.client.communication.API;
import org.json.XML;

import java.io.IOException;

public class Main extends Application {

    private static int width = 1000;
    private static int height = 720;
    private static Stage stage;
    private static MainScreen mainScreen = new MainScreen();
    private static ProfileController profileScreen = new ProfileController();
    private static LeaderboardController leaderBoardScreen = new LeaderboardController();
    private static AchievementsController achievementsScreen = new AchievementsController();
    private static EventController EventController = new EventController();


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
    private static void openLoginScreen() {
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
    static void openMainScreen() {
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
    static void openLeaderboardScreen() {
        try {
            stage.setScene(leaderBoardScreen.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    static void openEventScreen() {
        try {
            stage.setScene(EventController.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    /**
     * Method that changes scene to ProfileScreen.
     */
    static void openProfileScreen() {
        try {
            stage.setScene(profileScreen.getScene());
        } catch (IOException ex) {
            pageOpenError(ex);
        }
    }

    /**
     * Method that changes scene to the AchievementsScreen.
     */
    static void openAchievementsScreen() {
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
        API.initAPI();

        launch(args);
    }
}
