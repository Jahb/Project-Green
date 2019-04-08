package nl.tudelft.gogreen.client;

import static javafx.scene.layout.Priority.ALWAYS;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.client.communication.ProfileType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;

public class ProfileController implements Initializable {

    @FXML
    public Button followingButton;
    @FXML
    public Label followersLabel;
    @FXML
    public Text position;
    @FXML
    public HBox achievement3;
    @FXML
    public HBox achievement2;
    @FXML
    public HBox achievment1;
    @FXML
    private BorderPane buttonPane;
    @FXML
    private ImageView profilePicture;
    @FXML
    private JFXListView<ListItem> followerList;
    @FXML
    private JFXTextField searchFollowers;
    @FXML
    private Button achievementsButton;
    @FXML
    private JFXListView<String> activityList;
    @FXML
    private BorderPane ringPane;
    @FXML
    private BorderPane topBar;
    @FXML
    private Text todayPoints;
    @FXML
    private Text avgPoints;
    @FXML
    private Text titleText;
    @FXML
    private Button follow;
    @FXML
    private HBox topRightBox;
    @FXML
    private BorderPane achievementsBorder;

    private Ring userRing;
    private ObservableList<ListItem> followerArray = FXCollections.observableArrayList();
    private ObservableList<ListItem> followingArray = FXCollections.observableArrayList();
    private ObservableList<String> activities = FXCollections.observableArrayList();

    //TODO handler for each ring category
    private Consumer<String> ringHandler = name -> System.out.println("EXE [" + name + "]");

    private String username = Api.current.getUsername();
    private ProfileType type = ProfileType.CURRENTUSER;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Adding of BackButton
        IconButton.addBackButton(buttonPane);
        achievementsButton.setOnAction(event -> Main.openAchievementsScreen());
        addRing(ringPane);
        titleText.setText(this.username + "'s Profile");
        todayPoints.setText("Total Points: " + Api.current.getTotal());

        if (this.type == ProfileType.CURRENTUSER) {
            topRightBox.getChildren().remove(follow);
        }

        //TODO Removes Position Text after condition e.g if user is not following.
        if (false) {
            topRightBox.getChildren().remove(position);
        } else {
            //TODO retrieve position of user compared to following
            position.setText("Position: #10");
        }
        if (Api.current.getFollowing().keySet().stream().noneMatch(s -> s.equals(this.username))) {
            follow.setText("Follow");
        }

        //TODO Adding Achievements just pull 3 most recent and call AddAchievements Method
        ListItem achievement1 = new ListItem(
                new Image("/images/IconCupGold.png"), "Achievement 1");
        ListItem achievement2 = new ListItem(
                new Image("/images/IconCupSilver.png"), "Achievement 2");
        ListItem achievement3 = new ListItem(
                new Image("/images/IconCupBronze.png"), "Achievement 3");
        addAchievements(achievement1, achievement2, achievement3);


        new Thread(() -> {
            Set<String> following = Api.getApi().getFollowing().keySet();
            Set<String> followers = Api.getApi().getFollowers().keySet();
            Platform.runLater(() -> {
                for (String f : following) {
                    followingArray.add(new ListItem(new Image("/images/addButton.png"), f));
                }
                for (String f : followers) {
                    followerArray.add(new ListItem(new Image("/images/addButton.png"), f));

                }
                followerList.setItems(followerArray);

            });
        }).start();

        followerList.setItems(followerArray);
        followerList.setCellFactory(param -> new Cell());


        //TODO Activities List
        activities.add("13:00 - Did this");
        activities.add("14:00 - Did That");
        activities.add("10:00 - Ate a vegetarian meal");
        activityList.setItems(activities);

        profilePicture.setImage(Main.getProfilePicture());


    }

    /**
     * A Badly written method that takes 3 list items and adds them to the Achievements Panel.
     *
     * @param one   ListItem
     * @param two   ListItem
     * @param three ListItem
     */
    private void addAchievements(ListItem one, ListItem two, ListItem three) {
        HBox first = (HBox) achievementsBorder.getLeft();
        first.setAlignment(Pos.CENTER);
        HBox second = (HBox) achievementsBorder.getCenter();
        second.setAlignment(Pos.CENTER);
        HBox third = (HBox) achievementsBorder.getRight();
        third.setAlignment(Pos.CENTER);
        Pane pane = new Pane();
        HBox.setHgrow(pane, ALWAYS);

        ImageView image1 = new ImageView(one.getImage());
        image1.setFitWidth(50);
        image1.setFitHeight(50);

        ImageView image2 = new ImageView(two.getImage());
        image2.setFitHeight(50);
        image2.setFitWidth(50);

        ImageView image3 = new ImageView(three.getImage());
        image3.setFitHeight(50);
        image3.setFitWidth(50);

        first.getChildren().addAll(image1, new Label(one.getText()), pane);
        second.getChildren().addAll(image2, new Label(two.getText()), pane);
        third.getChildren().addAll(image3, new Label(three.getText()), pane);
    }

    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        System.out.println(this.username);
        URL url = Main.class.getResource("/Profile.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);

        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    private void addRing(BorderPane pane) {
        userRing = new Ring((int) (150 * .75), 150, 0, 0, "MAIN");
        userRing.setHandler(ringHandler);
        userRing.setUsername(Api.current.getUsername());
        Pane ring = userRing.getPane();
        pane.setCenter(ring);

        updateRing();
    }

    private void updateRing() {
        double[] valuesMain = Api.current.getRingSegmentValues(userRing.getName());
        userRing.setUsername(Api.current.getUsername());
        userRing.setSegmentValues(valuesMain);
        userRing.startAnimation();
    }


    class ListItem {
        private Image image;
        private String text;

        ListItem(Image image, String text) {
            this.image = image;
            this.text = text;
        }

        public Image getImage() {
            return this.image;
        }

        public String getText() {
            return this.text;
        }
    }

    class Cell extends ListCell<ListItem> {
        HBox listCell = new HBox();
        Label itemName = new Label();
        Pane pane = new Pane();
        Pane pane2 = new Pane();
        ImageView image = new ImageView();

        private Cell() {
            super();
            listCell.getChildren().addAll(image, pane, itemName, pane2);
            HBox.setHgrow(pane, ALWAYS);
            HBox.setHgrow(pane2, ALWAYS);
            listCell.setAlignment(Pos.CENTER);
            followersLabel.setAlignment(Pos.CENTER);
            image.setFitHeight(75);
            image.setFitWidth(75);
        }

        public void updateItem(ListItem item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                itemName.setText(item.getText());
                image.setImage(item.getImage());
                setGraphic(listCell);
            }
        }
    }

    @FXML
    private void followersClicked() {
        if (followersLabel.getText().equals("Followers")) {
            followersLabel.setText("Following");
            followingButton.setText("Followers");
            followerList.setItems(followingArray);
        } else {
            followersLabel.setText("Followers");
            followingButton.setText("Following");
            followerList.setItems(followerArray);
        }
    }

    @FXML
    private void followClicked() {
        //TODO Allow Follow and UnFollow Of User
        if (follow.getText().equals("Follow")) {
            follow.setText("UnFollow");
        } else {
            follow.setText("Follow");
        }
    }
}
