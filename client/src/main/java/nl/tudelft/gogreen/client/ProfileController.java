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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Circle profileCircle;
    @FXML
    private ListView<ListItem> friendsList = new ListView<ListItem>();


    /**
     * Scene switching via button.
     *
     * @param event2
     * @throws Exception
     */
    public void GotoMenu(ActionEvent event2) throws Exception {
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

    ListItem a = new ListItem("friend1", "/achievementImage.png");
    ListItem b = new ListItem("friend2", "/achievementImage.png");
    ListItem c = new ListItem("friend3", "/achievementImage.png");


    private final ObservableList<ListItem> items = FXCollections.observableArrayList();


    //setting placeholder pictures
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image profileImg = new Image("/logo.png");
        Image achievementImg = new Image("/achievementImage.png");
        profileCircle.setFill(new ImagePattern(profileImg));
        achievementCircle1.setFill(new ImagePattern(achievementImg));
        achievementCircle2.setFill(new ImagePattern(achievementImg));
        achievementCircle3.setFill(new ImagePattern(achievementImg));

        items.clear();
        items.add(new ListItem("profile1", "achievementImage.png"));
        items.add(new ListItem("profile2", "achievementImage.png"));
        items.add(new ListItem("profile3", "achievementImage.png"));
        friendsList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

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
                        }
                    }
                };
                return cell;
            }
        });
    }
}
