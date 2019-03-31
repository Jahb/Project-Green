package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import nl.tudelft.gogreen.client.communication.Api;


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
     * @throws UnirestException Possible Exception Throw.
     */
    public void login() throws UnirestException {
        String un = userField.getText();
        String pw = passwordField.getText();


        if (Api.current.login(un, pw)) {

            Main.openMainScreen();
        }

    }

    public void register() throws UnirestException {
        String un = userField.getText();
        String pw = passwordField.getText();
        if (Api.current.register(un, pw)) {
            login();
        }
    }

}


