package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;




public class QuizController implements Initializable {

    @FXML
    public JFXSlider incomeSlider;
    @FXML
    public JFXSlider sizeSlider;
    @FXML
    public JFXSlider vehicleSlider;
    @FXML
    public JFXSlider mileSlider;
    @FXML
    public JFXSlider economySlider;
    @FXML
    public JFXSlider fuelSlider;




    public void initialize (URL location, ResourceBundle resources){

    }

    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/QuizScreen.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane leftPane = (BorderPane) root.getChildren().get(1);
        BorderPane rightPane = (BorderPane) root.getChildren().get(2);
        addIconButtons(leftPane, rightPane);
        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    public void addIconButtons(BorderPane root1, BorderPane root2){
        IconButton defaultsButton = new IconButton("Confirm", 135, 135);
        root1.setRight(defaultsButton.getStackPane());
        defaultsButton.setOnClick(event -> Main.openMainScreen());
        IconButton saveButton = new IconButton("Confirm", 135, 135);
        root2.setRight(saveButton.getStackPane());
        saveButton.setOnMouseClicked(event->Main.openMainScreen());
    }

}
