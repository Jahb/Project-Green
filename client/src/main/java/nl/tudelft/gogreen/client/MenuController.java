package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
    @FXML
    private Button pButton;
    @FXML
    private Button achievementsButton;
    @FXML
    private Button settingsButton;

    //scene switching via button
    public void GotoProfile(ActionEvent event1) throws Exception{
        Stage stage = (Stage) pButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/ProfileGUI.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }
    //scene switching via button
    public void GotoSettings(ActionEvent event2) throws Exception{
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/SettingsGUI.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }
    //scene switching via button
    public void GotoAchievements(ActionEvent event3) throws Exception{
        Stage stage = (Stage) achievementsButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/AchievementsGUI.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }
}
