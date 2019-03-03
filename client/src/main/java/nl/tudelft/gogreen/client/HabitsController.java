package nl.tudelft.gogreen.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class HabitsController {

    @FXML
    Button setHabitsButton;

    public void setHabits(ActionEvent event) throws Exception {
        Stage stage = (Stage) setHabitsButton.getScene().getWindow();
        Parent root2 = null;
        root2 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root2, 1200, 700));
    }
}
