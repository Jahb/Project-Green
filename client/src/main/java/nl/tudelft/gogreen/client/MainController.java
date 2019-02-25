package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    @FXML
    private TextField passwordField;
    @FXML
    private TextField userField;
    @FXML
    private Button frgtPass;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginLabel;

    /* switches to main menu on successful login */
    public void Login(ActionEvent event) throws IOException {
        if (userField.getText().equals("username") && passwordField.getText().equals("password")) {
            loginLabel.setText("Login Successful!");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Parent root1 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
            stage.setScene(new Scene(root1, 1200, 700));
        } else {
            loginLabel.setText("Login Failed! Wrong username or password.");
        }
    }
    @FXML
    private Button pButton;

    public void GotoProfile(ActionEvent event1) throws Exception{
        Stage stage = (Stage) pButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/ProfileGUI.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }
}


