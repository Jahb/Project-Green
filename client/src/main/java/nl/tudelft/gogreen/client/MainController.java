package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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


    /**
     * Switches to Main Menu After a Successful Login.
     *
     * @param event UserEvent like a click.
     * @throws UnirestException Possible Exception Throw.
     * @throws IOException Possible Exception Throw.
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
            Main.openMainScreen();
        }
    }

}


