package nl.tudelft.gogreen.client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
            Scene scene = new Scene(root, 650, 440);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
