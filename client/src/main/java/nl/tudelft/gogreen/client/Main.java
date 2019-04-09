package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.gogreen.client.communication.Api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private static int width = 1000;
    private static int height = 720;
    private static Stage stage;
    private static MainScreen mainScreen = new MainScreen();
    private static ProfileController profileScreen = new ProfileController();
    private static LeaderboardController leaderBoardScreen = new LeaderboardController();
    private static AchievementsController achievementsScreen = new AchievementsController();
    private static EventController eventController = new EventController();
    private static QuizController quizController = new QuizController();


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
    static void openLoginScreen() {
        try {
            Parent root1 = FXMLLoader.load(Main.class.getResource("/Login.fxml"));
            stage.setScene(new Scene(root1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that changes scene to Register Screen.
     */
    static void openRegisterScreen() {
        try {
            Parent root1 = FXMLLoader.load(Main.class.getResource("/Register.fxml"));
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

    /**
     * Method that changes scene to EventScreen.
     */
    static void openEventScreen() {
        try {
            stage.setScene(eventController.getScene());
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

    static void openQuizScreen(){
        try{
            stage.setScene(quizController.getScene());
        }   catch (IOException ex) {
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
     * Returns One Of 4 ProfilePictures Of Current User.
     *
     * @return ProfilePicture Image.
     */
    static Image getProfilePicture() {
        int points = Api.current.getTotal();

        if (points < 250) {
            return new Image("/images/ppLvl1.png");
        }
        if (points < 500) {
            return new Image("/images/ppLvl2.png");
        }
        if (points < 750) {
            return new Image("/images/ppLvl3.png");
        }
        return new Image("images/ppLvl4.png");
    }

    /**
     * Main Method.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        Api.initApi();
        launch(args);
    }

    /**
     * Method used to show notifications to the user. Notification appears in the selected pane and disappears if clicked.
     * @param message text to show inside the notification box
     */
    @FXML
    public static void showMessage(Pane pane, String message){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.getStylesheets().add("NotificationCSS.css");
        snackbar.show(message, "Close", 4000, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                snackbar.close();
            }
        });
        for(int i=10; i>0; i--){
            MainScreen.strings[i]= MainScreen.strings[i-1];
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
        LocalDateTime now = LocalDateTime.now();
        MainScreen.strings[0]= " "+dtf.format(now)+" "+message;
    }
}
