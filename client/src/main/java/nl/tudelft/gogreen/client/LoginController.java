package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import nl.tudelft.gogreen.client.communication.API;


/**
 * Controller For MainControllerFXML.
 */
public class LoginController {
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordField2;
    @FXML
    private TextField userField;
    @FXML
    private AnchorPane frgtPassword;
    @FXML
    private AnchorPane emailSent;
    @FXML
    private Text passwordWrong;
    @FXML
    private Text passwordNotMatch;

    /**
     * Switches to Main Menu After a Successful Login.
     *
     * @throws UnirestException Possible Exception Throw.
     */
    public void login() throws UnirestException {
        String un = userField.getText();
        String pw = passwordField.getText();


        if (API.current.login(un, pw)) {

            Main.openMainScreen();
        } else if (passwordWrong != null) {
            passwordWrong.setVisible(true);
        }

    }

    /**
     * Method call on register.
     *
     * @throws UnirestException an exception.
     */
    public void register() throws UnirestException {
        String un = userField.getText();
        String pw = passwordField.getText();
        String pw2 = passwordField2.getText();
        if (!pw.equals(pw2)) {
            passwordNotMatch.setVisible(true);
        } else if (API.current.register(un, pw)) {
            login();
        }

    }

    public void openRegister() {
        Main.openRegisterScreen();
    }

    public void openLogin() {
        Main.openLoginScreen();
    }

    public void frgtPassToggle() {
        frgtPassword.setVisible(!frgtPassword.isVisible());
        if (emailSent.isVisible()) {
            emailSent.setVisible(false);
        }
    }

    public void sendEmail() {
        //TODO Forget Email method.
        System.out.println("Email Sent!");
        emailSent.setVisible(true);
    }

}


