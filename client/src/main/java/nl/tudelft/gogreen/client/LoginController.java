package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import nl.tudelft.gogreen.client.communication.Api;


/**
 * Controller For MainControllerFXML.
 */
public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    @FXML
    public Label loginLabel;
    @FXML
    public Button frgtPassButton;
    @FXML
    public JFXTextField enterEmail;
    @FXML
    public Button sendEmailButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Button cancelButton1;
    @FXML
    public JFXTextField emailField;
    @FXML
    public Button backToLogin;
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
    @FXML
    private Text invalidEmail;

    /**
     * Switches to Main Menu After a Successful Login.
     */
    public void login() {
        String un = userField.getText();
        String pw = passwordField.getText();


        if (Api.current.login(un, pw)) {
            Main.openMainScreen();
        } else if (passwordWrong != null) {
            passwordWrong.setVisible(true);
        }

    }

    /**
     * Method call on register.
     */
    public void register() {
        String un = userField.getText();
        String pw = passwordField.getText();
        String pw2 = passwordField2.getText();
        if (emailField.getText().indexOf('@') == -1 || emailField.getText().indexOf('.') == -1) {
            invalidEmail.setVisible(true);
            return;
        } else {
            invalidEmail.setVisible(false);
        }
        if (!pw.equals(pw2)) {
            passwordNotMatch.setVisible(true);
            return;
        } else {
            passwordNotMatch.setVisible(false);
        }
        if (Api.current.register(un, pw)) {
            if (Api.current.login(un, pw))
                Main.openQuizScreen();

        }

    }

    public void openRegister() {
        Main.openRegisterScreen();
    }

    public void openLogin() {
        Main.openLoginScreen();
    }

    /**
     * Toggles Visibility of Forgot Password Pane.
     */
    public void frgtPassToggle() {
        frgtPassword.setVisible(!frgtPassword.isVisible());
        if (emailSent.isVisible()) {
            emailSent.setVisible(false);
        }
        if (passwordWrong.isVisible()) {
            passwordWrong.setVisible(false);
        }
    }

    /**
     * Method That is run once user clicks Send Email Button.
     */
    public void sendEmail() {
        //TODO Forget Email method.
        System.out.println("Email Sent!");
        emailSent.setVisible(true);
    }

}


