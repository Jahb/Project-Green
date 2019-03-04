package nl.tudelft.gogreen.client;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView leaderboardHeader;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ListView<ListItem> leaderboardList = new ListView<ListItem>();

    public Image buttonRelease = new Image("ButtonBack.png");
    public Image buttonPress = new Image("ButtonBackClicked.png");


    private final ObservableList<ListItem> items = FXCollections.observableArrayList();

    //method for adding pictures and text to ListView
    public void initialize(URL location, ResourceBundle resources) {
        FadeIn();
        items.clear();
        items.add(new ListItem("profile1", "achievementimage.png"));
        items.add(new ListItem("profile2", "achievementimage.png"));
        items.add(new ListItem("profile3", "achievementImage.png"));
        leaderboardList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                ListCell<ListItem> cell = new ListCell<ListItem>() {
                    @Override
                    protected void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null) {
                            Image img = new Image(getClass().getResource("/" + item.getImageLocation()).toExternalForm());
                            ImageView imgview = new ImageView(img);
                            imgview.setFitHeight(90);
                            imgview.setFitWidth(90);
                            setGraphic(imgview);
                            setText(item.getName());
                            super.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
                        }
                    }
                };
                return cell;
            }
        });
        leaderboardList.setItems(items);
    }

    //back button methods
    public void backPress() {
        System.out.println("Back Button Pressed");
        backButton.setImage(buttonPress);
    }

    public void backRelease() throws Exception {
        System.out.println("Back Button Released");
        backButton.setImage(buttonRelease);
        Parent root2 = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root2, 1200, 700));
    }

    private void FadeIn() {
        FadeTransition fadetransition = new FadeTransition();
        fadetransition.setDuration(Duration.millis(150));
        fadetransition.setNode(rootPane);
        fadetransition.setFromValue(0);
        fadetransition.setToValue(1);
        fadetransition.play();
    }
}
