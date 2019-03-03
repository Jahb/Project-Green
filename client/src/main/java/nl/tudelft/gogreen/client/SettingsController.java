package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private Button backButton;

    //scene switching via button
    public void gotoMenu(ActionEvent event2) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root2 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root2, 1200, 700));
    }
}
