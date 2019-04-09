package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

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
    public JFXSlider billSlider;
    @FXML
    public Label mileLabel;
    @FXML
    public Label economyLabel;
    @FXML
    public JFXButton defaultButton;
    @FXML
    public JFXButton saveButton;


    public void initialize(URL location, ResourceBundle resources) {

        defaultButton.setOnMouseClicked(event -> useDefaults());
        saveButton.setOnMouseClicked(event -> useSaved());


        /**
         * Sets string labels for vehicleSlider
         */
        vehicleSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n <= 0.5) return "No";
                else return "Yes";

            }

            public Double fromString(String s) {
                switch (s) {
                    case "No":
                        return 0d;
                    case "Yes":
                        return 1d;
                    default:
                        return 1d;
                }
            }
        });

        /**
         * disable last 3 sliders when vehicleSlider is set to "No"
         */
        ChangeListener<Number> listener = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number oldVal, Number newVal) {
                if (newVal.intValue() < 0.5) {
                    economySlider.setDisable(true);
                    mileSlider.setDisable(true);
                    mileLabel.setStyle("-fx-text-fill: gray");
                    economyLabel.setStyle("-fx-text-fill: gray");
                } else {
                    economySlider.setDisable(false);
                    mileSlider.setDisable(false);
                    mileLabel.setStyle("-fx-text-fill: black");
                    economyLabel.setStyle("-fx-text-fill: black");
                }
            }
        };
        vehicleSlider.valueProperty().addListener(listener);
    }

    /**
     * Method for loading the QuizScreen scene
     *
     * @return
     * @throws IOException
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/QuizScreen.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        return new Scene(root, Main.getWidth(), Main.getHeight());
    }


    /**
     * Method for using the default stats when clicking the defaultsButton
     */
    public void useDefaults() {
        Main.openMainScreen();
    }

    /**
     * Method for using user-selected stats when clicking the saveButton
     */
    public void useSaved() {
        Main.openMainScreen();
    }
}