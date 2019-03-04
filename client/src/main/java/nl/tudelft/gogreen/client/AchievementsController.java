package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AchievementsController {
    @FXML
    private Button backButton;

    //scene switching via button
    public void GotoMenu(ActionEvent event1) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }
}