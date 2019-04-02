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
import javafx.scene.text.Text;
import javafx.util.Callback;
import nl.tudelft.gogreen.client.communication.Api;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private JFXButton followUserButton;
    @FXML
    private Text dailyPoints;
    @FXML
    private Text overallPoints;
    @FXML
    private Label followLabel;
    @FXML
    private JFXButton showFollowersButton;
    @FXML
    public JFXTextField followField;
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
            if (followLabel.getText().equals("Following")) {
                Map<String, Integer> followers = Api.current.getFollowers();
                items.clear();
                for (String st : followers.keySet()) {
                    items.add(new ListItem(st, "images/buttonProfile.png"));
                }
                ((UpdateableListViewSkin) friendsList.getSkin()).refresh();
                followLabel.setText("Followers");
                showFollowersButton.setText("Show Following");
            } else {
                items.clear();
                Map<String, Integer> followers = Api.current.getFollowing();
                items.clear();
                for (String st : followers.keySet()) {
                    items.add(new ListItem(st, "images/buttonProfile.png"));
                }
                ((UpdateableListViewSkin) friendsList.getSkin()).refresh();
                followLabel.setText("Following");
                showFollowersButton.setText("Show Followers");
            }
        });

        overallPoints.setText(Integer.toString(Api.current.getTotal()));
        dailyPoints.setText(Integer.toString(Api.current.getTotal()));

        LocalTime yeet = LocalTime.now().minus(Duration.ofMinutes(1));

        activities.add(yeet.format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) + " - Sometime this");
        activities.add(yeet.minus(
                Duration.ofMinutes(1)).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) + " - is going to be");
        activities.add(yeet.minus(
                Duration.ofMinutes(2)).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) + " - kept history");
        activityList.setItems(activities);

        Map<String, Integer> followers = Api.current.getFollowing();
        items.clear();
        for (String st : followers.keySet()) {
            items.add(new ListItem(st, "images/buttonProfile.png"));
        }

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

        //Adding Follow
        followUserButton.setOnMouseClicked((MouseEvent event) -> {
            String username = followField.getText();
            boolean res = Api.current.follow(username);
        });
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
    }

}
