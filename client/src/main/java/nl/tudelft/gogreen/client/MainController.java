package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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

    public void Login(ActionEvent event) {
        if (userField.getText().equals("username") && passwordField.getText().equals("password")) {
            loginLabel.setText("Login Successful!");
        } else {
            loginLabel.setText("Login Failed! Wrong username or password.");
        }
    }
}
