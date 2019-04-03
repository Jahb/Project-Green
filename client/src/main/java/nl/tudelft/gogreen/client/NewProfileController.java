package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.shared.EventItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static javafx.scene.layout.Priority.ALWAYS;

public class NewProfileController implements Initializable {

    @FXML
    public Button followingButton;
    @FXML
    public Label followersLabel;
    @FXML
    public Text position;
    @FXML
    private BorderPane buttonPane;
    @FXML
    private ImageView profilePicture;
    @FXML
    private JFXListView followerList;
    @FXML
    private JFXTextField searchFollowers;
    @FXML
    private Button achievementsButton;
    @FXML
    private JFXListView activityList;
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
        titleText.setText(Api.current.getUsername() + "'s Profile");
        todayPoints.setText("Total Points: " + Api.current.getTotal());

        //TODO Removes Follow Button From Scene If condition is met e.g if is UserProfile.
        if (false) {
            topRightBox.getChildren().remove(follow);
        }
        //TODO Removes Position Text after condition e.g if user is not following.
        if (false) {
            topRightBox.getChildren().remove(position);
        }
        //TODO Marks Follow button from "UnFollow" to Follow after condition is met.
        if (false) {
            follow.setText("Follow");
        }

        //TODO Adding Achievements just pull 3 most recent and call AddAchievements Method
        ListItem achievement1 = new ListItem(new Image("/images/IconCupGold.png"), "Achievement 1");
        ListItem achievement2 = new ListItem(new Image("/images/IconCupSilver.png"), "Achievement 2");
        ListItem achievement3 = new ListItem(new Image("/images/IconCupBronze.png"), "Achievement 3");
        addAchievements(achievement1, achievement2, achievement3);

        //TODO Add to Follow Array buttons I think should work all you need to do is pull from DB and add to the followerArray & followingArray
        ListItem follower1 = new ListItem(new Image("/images/addButton.png"), "Alex");
        ListItem follower2 = new ListItem(new Image("/images/addButton.png"), "Justin");
        ListItem follower3 = new ListItem(new Image("/images/addButton.png"), "Emily");

        followerArray.addAll(follower1, follower2, follower3, follower1, follower2);
        followingArray.addAll(follower2, follower1);
        followerList.setItems(followerArray);
        followerList.setCellFactory(param -> new Cell());


        //TODO Activities List
        activities.add("13:00 - Did this");
        activities.add("14:00 - Did That");
        activities.add("10:00 - Ate a vegetarian meal");
        activityList.setItems(activities);


    }

    /**
     * A Badly written method that takes 3 list items and adds them to the Achievements Panel.
     *
     * @param one   ListItem
     * @param two   ListItem
     * @param three ListItem
     */
    private void addAchievements(ListItem one, ListItem two, ListItem three) {
        Pane pane = new Pane();

        HBox first = (HBox) achievementsBorder.getLeft();
        first.setAlignment(Pos.CENTER);
        HBox second = (HBox) achievementsBorder.getCenter();
        second.setAlignment(Pos.CENTER);
        HBox third = (HBox) achievementsBorder.getRight();
        third.setAlignment(Pos.CENTER);
        HBox.setHgrow(pane, ALWAYS);

        ImageView image1 = new ImageView(one.getImage());
        ImageView image2 = new ImageView(two.getImage());
        ImageView image3 = new ImageView(three.getImage());

        image1.setFitWidth(50);
        image1.setFitHeight(50);

        image2.setFitHeight(50);
        image2.setFitWidth(50);

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

            //listCell.getStylesheets().add(getClass().getResource("/ListStyle.css").toExternalForm());
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
