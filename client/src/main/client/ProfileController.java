package main.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public  class ProfileController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Circle profileCircle;

    //scene switching via button
    public void GotoMenu(ActionEvent event2) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root2 = FXMLLoader.load(getClass().getResource("PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root2, 1200, 700));
    }

    //Added profile picture within circle
    @FXML
    Image img = new Image("main/resources/logo.png");
    @FXML
    StackPane imageContainer = new StackPane();

    @Override
    public void initialize (URL url , ResourceBundle rb){
        profileCircle.setFill(new ImagePattern(img));
    }
}
