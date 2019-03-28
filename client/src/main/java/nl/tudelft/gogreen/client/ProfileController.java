package nl.tudelft.gogreen.client;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label followLabel;
    @FXML
    private JFXButton showFollowersButton;
    @FXML
    private JFXTextField followField;
    @FXML
    ImageView achievementImage1;
    @FXML
    ImageView achievementImage2;
    @FXML
    ImageView achievementImage3;
    @FXML
    ListView<String> activityList = new ListView<>();
    @FXML
    private ListView<ListItem> friendsList = new ListView<>();

    private ObservableList<String> activities = FXCollections.observableArrayList();
    private ObservableList<ListItem> items = FXCollections.observableArrayList();

    //setting placeholder pictures
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UpdateableListViewSkin<ListItem> skin = new UpdateableListViewSkin<>(this.friendsList);
        this.friendsList.setSkin(skin);

        showFollowersButton.setOnMouseClicked((MouseEvent event) -> {
            if(followLabel.getText().equals("Following")) {
                items.clear();
                items.add(new ListItem("profile5", "images/buttonProfile.png"));
                items.add(new ListItem("profile712847", "images/buttonProfile.png"));
                ((UpdateableListViewSkin) friendsList.getSkin()).refresh();
                followLabel.setText("Followers");
                showFollowersButton.setText("Show Following");
            }
            else{
                items.clear();
                items.add(new ListItem("profile1", "images/achievementImage.png"));
                items.add(new ListItem("profile2", "images/achievementImage.png"));
                items.add(new ListItem("profile3", "images/achievementImage.png"));
                ((UpdateableListViewSkin) friendsList.getSkin()).refresh();
                followLabel.setText("Following");
                showFollowersButton.setText("Show Followers");
            }
        });

        activities.add("17:05 - Ate a Vegetarian Meal");
        activities.add("11:45 - Ate a Vegetarian Meal");
        activities.add("13:05 - Ate a Vegetarian Meal");
        activityList.setItems(activities);

        items.clear();
        items.add(new ListItem("profile1", "images/achievementImage.png"));
        items.add(new ListItem("profile2", "images/achievementImage.png"));
        items.add(new ListItem("profile3", "images/achievementImage.png"));
        friendsList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                return new JFXListCell<ListItem>() {
                    @Override
                    public void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null) {
                            Image img = new Image(getClass()
                                    .getResource("/" + item.getImageLocation()).toExternalForm());
                            ImageView imgview = new ImageView(img);
                            imgview.setFitHeight(90);
                            imgview.setFitWidth(100);
                            setGraphic(imgview);
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        friendsList.setItems(items);
    }


    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/ProfileGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane buttonPane = (BorderPane) root.getChildren().get(0);
        IconButton.addBackButton(buttonPane);
        BorderPane bottomPane = (BorderPane) root.getChildren().get(1);
        BorderPane searchPane = (BorderPane) root.getChildren().get(2);
        addIconButtons(bottomPane, searchPane);
        return new Scene(root, Main.getWidth(), Main.getHeight());
    }


    private void addIconButtons(BorderPane root, BorderPane root1) {
        IconButton achievementsButton = new IconButton("Achievements", 100, 100);
        root.setLeft(achievementsButton.getStackPane());
        achievementsButton.setOnClick(event -> Main.openAchievementsScreen());
        IconButton leaderboardButton = new IconButton("Leaderboard", 100, 100);
        root.setRight(leaderboardButton.getStackPane());
        leaderboardButton.setOnClick(event -> Main.openLeaderboardScreen());
        IconButton followerAddButton = new IconButton("Add", 50, 50);
        root1.setRight(followerAddButton.getStackPane());
    }
}
