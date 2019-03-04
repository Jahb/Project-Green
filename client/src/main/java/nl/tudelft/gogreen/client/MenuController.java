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
        Main.openProfileScreen();
    }
    //scene switching via button
    public void GotoSettings(ActionEvent event2) throws Exception{
        Main.openProfileScreen();
    }
    //scene switching via button
    public void GotoAchievements(ActionEvent event3) throws Exception{
        Main.openLeaderboardScreen();
    }
}
