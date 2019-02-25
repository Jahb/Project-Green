package nl.tudelft.gogreen.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public  class ProfileController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Circle profileCircle;
    @FXML
    private ListView<ListItem> friendsList= new ListView<ListItem>();





    //scene switching via button
    public void GotoMenu(ActionEvent event2) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root2 = FXMLLoader.load(getClass().getResource("/PlaceholderMenu.fxml"));
        stage.setScene(new Scene(root2, 1200, 700));
    }

    //Added profile picture within circle
    @FXML
    StackPane imageContainer = new StackPane();
    @FXML
    Circle achievementCircle1;
    @FXML
    Circle achievementCircle2;
    @FXML
    Circle achievementCircle3;

    private ObservableList<ListItem> items = FXCollections.observableArrayList();

    //setting placeholder pictures
    @Override
    public void initialize (URL url , ResourceBundle rb) {
        Image profileImg = new Image("main/resources/logo.png");
        Image achievementImg = new Image("main/resources/achievementImage.png");
        profileCircle.setFill(new ImagePattern(profileImg));
        achievementCircle1.setFill(new ImagePattern(achievementImg));
        achievementCircle2.setFill(new ImagePattern(achievementImg));
        achievementCircle3.setFill(new ImagePattern(achievementImg));
        friendsList.setItems(items);
        for(ListItem a : items){
            a.setImage(achievementImg);
            a.setText("friend");
        }
    }
}
