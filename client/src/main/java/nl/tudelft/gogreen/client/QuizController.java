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
    public JFXSlider billSlider;
    @FXML
    public JFXSlider surfaceSlider;
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
        //TODO connect values to database (these should use the average)
        int surface= -1;
        int size= -1;
        int vehicle=-1;
        int income=-1;
        int bill=-1;
    }

    /**
     * Method for using user-selected stats when clicking the saveButton
     */
    public void useSaved() {
        Main.openMainScreen();
        //TODO connect values to database(actual values on the ints)
        int surface= (int) surfaceSlider.getValue();
        int size = (int) sizeSlider.getValue();
        int vehicle = (int) vehicleSlider.getValue();
        int income = (int) incomeSlider.getValue();
        int bill = (int) billSlider.getValue();
        System.out.print(income+"\n");
        System.out.print(size+"\n");
        System.out.print(bill+"\n");
        System.out.print(surface+"\n");
        System.out.print(vehicle+"\n");
    }
}
