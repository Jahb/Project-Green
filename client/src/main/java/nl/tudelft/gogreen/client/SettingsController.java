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
        Main.openMainScreen();
    }
}
