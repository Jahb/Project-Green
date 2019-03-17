package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.exceptions.UnirestException;
<<<<<<< HEAD
import com.mashape.unirest.request.body.MultipartBody;
import javafx.event.ActionEvent;
=======
>>>>>>> master
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
<<<<<<< HEAD
import javafx.stage.Stage;
import nl.tudelft.gogreen.client.communication.API;
=======
import nl.tudelft.gogreen.shared.auth.AuthAgreement;
import nl.tudelft.gogreen.shared.auth.UserAuth;
>>>>>>> master

/**
 * Controller For MainControllerFXML.
 */
public class MainController {
    @FXML
    private TextField passwordField;
    @FXML
    private TextField userField;
<<<<<<< HEAD
    @FXML
    private Button frgtPass;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginLabel;
    private MultipartBody username;
=======
>>>>>>> master


    /**
     * Switches to Main Menu After a Successful Login.
     *
     * @throws UnirestException Possible Exception Throw.
     */
    public void login() throws UnirestException {
        String un = userField.getText();
        String pw = passwordField.getText();


<<<<<<< HEAD
        if (API.getTestApi().login(un, pw)) {
            login();
=======
        HttpResponse<AuthAgreement> auth = Unirest.post("http://localhost:8080/auth")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(logon).asObject(AuthAgreement.class);

        AuthAgreement agreement = auth.getBody();

        if (agreement.isSuccess()) {
            Main.openMainScreen();
>>>>>>> master
        }

    }

}


