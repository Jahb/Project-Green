package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.gogreen.client.communication.API;

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
    private MultipartBody username;

    /* switches to main menu on successful login */
    public void Login(ActionEvent event) throws UnirestException, IOException {
        String un = userField.getText();
        String pw = passwordField.getText();


        if (API.getTestApi().login(un, pw)) {
            login();
        }

    }

    private void login() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }

}


