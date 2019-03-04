package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.gogreen.shared.auth.AuthAgreement;
import nl.tudelft.gogreen.shared.auth.UserAuth;

import java.io.IOException;

/**
 * Controller For MainControllerFXML.
 */
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


    /**
     * Switches to Main Menu After a Successful Login.
     *
     * @param event
     * @throws UnirestException
     * @throws IOException
     */
    public void Login(ActionEvent event) throws UnirestException, IOException {
        String un = userField.getText();
        String pw = passwordField.getText();

        UserAuth logon = new UserAuth(un, pw);

        HttpResponse<AuthAgreement> auth = Unirest.post("http://localhost:8080/auth")
                .header("accept", "application/json")
                .header("Content-Type", "application/json").body(logon).asObject(AuthAgreement.class);

        AuthAgreement agreement = auth.getBody();

        if (agreement.isSuccess()) {
            login();
        }
    }

    /**
     * Changes Scene to menu after login
     * @throws IOException
     */
    private void login() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root1, 1200, 700));
    }

}


