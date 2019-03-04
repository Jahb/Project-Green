package nl.tudelft.gogreen.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegisterController {

    @FXML
    Label infoLabel;
    @FXML
    TextField userField;
    @FXML
    PasswordField passField1;
    @FXML
    PasswordField passField2;
    @FXML
    Button createAccountButton;

    public void RegisterAccount(ActionEvent event) throws Exception {
        if (passField1.getText().equals(passField2.getText()) && passField1.getText().length() > 7) {
            Stage stage = (Stage) createAccountButton.getScene().getWindow();
            Parent root1 = FXMLLoader.load(getClass().getResource("/ProfileGUI.fxml"));
            stage.setScene(new Scene(root1, 1200, 700));
        } else if (!passField1.getText().equals(passField2.getText())) {
            infoLabel.setText("Passwords do not match. Try again:");
        } else infoLabel.setText("Password needs to be at least 8 characters in length. Try again:");
    }
}
