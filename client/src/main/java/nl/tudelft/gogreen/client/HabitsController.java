package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class HabitsController {

    @FXML
    Button setHabitsButton;

    /**
     *
     * @param event
     * @throws Exception
     */
    public void SetHabits(ActionEvent event) throws Exception {
        Stage stage = (Stage) setHabitsButton.getScene().getWindow();
        Parent root2 = null;
        root2 = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
        stage.setScene(new Scene(root2, 1200, 700));
    }
}
