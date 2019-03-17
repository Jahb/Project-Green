package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import nl.tudelft.gogreen.client.communication.API;


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


        if (API.getTestApi().login(un, pw)) {

            Main.openMainScreen();
        }

    }

}


