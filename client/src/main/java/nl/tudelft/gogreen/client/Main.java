package nl.tudelft.gogreen.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL u = Main.class.getResource("/Login.fxml");
        System.out.println(u);
        Parent root = FXMLLoader.load(u);
        Scene loginScene = new Scene(root, 650, 440);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
