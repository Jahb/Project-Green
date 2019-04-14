package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXListView;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.client.communication.ProfileType;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    private ProfileType type = ProfileType.CURRENTUSER;

    private static String username;

    public static void setUsername(String username) {
        ProfileController.username = username;
    }

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
        titleText.setText(username + "'s Profile");
        todayPoints.setText("Total Points: " + Api.current.getTotal());

        if (this.type == ProfileType.CURRENTUSER) {
            topRightBox.getChildren().remove(follow);
        }

        if (Api.current.getFollowing(username).keySet().stream().noneMatch(un -> un.equals(username))) {
            topRightBox.getChildren().remove(position);
        } else {
            position.setText("Position: #" + Api.current.getPosition());
        }
        if (Api.current.getFollowing(username).keySet().stream().noneMatch(s -> s.equals(username))) {
            follow.setText("Follow");
        }

        List<Integer> ach = Api.current.getAchievements(username);
        List<String> names = Api.current.getAchievementNames();

        int switchSize;
        if (ach == null) {
            switchSize = 0;
        } else {
            switchSize = ach.size();
        }

        switch (switchSize) {

            case 1:
                addAchievements(new ListItem(new Image("/images/IconCupGold.png"),
                                names.get(ach.get(0) - 1)),
                        null, null);
                break;
            case 2:
                addAchievements(new ListItem(new Image("/images/IconCupGold.png"),
                                names.get(ach.get(0) - 1)),
                        new ListItem(new Image("/images/IconCupGold.png"),
                                names.get(ach.get(1) - 1)),
                        null);
                break;
            case 3:
                addAchievements(new ListItem(new Image("/images/IconCupGold.png"),
                                names.get(ach.get(0) - 1)),
                        new ListItem(new Image("/images/IconCupGold.png"),
                                names.get(ach.get(1) - 1)),
                        new ListItem(new Image("/images/IconCupGold.png"),
                                names.get(ach.get(2) - 1)));
                break;
            default:
                break;
        }


        new Thread(() -> {
            Set<String> following = Api.getApi().getFollowing(username).keySet();
            Set<String> followers = Api.getApi().getFollowers(username).keySet();
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


        new Thread(() -> {
            List<Pair<String, Date>> data = Api.current.getHistoryFor(username);
            List<String> dates = data.stream().map(it ->
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(it.getValue().toInstant())
                            + " - " + it.getKey()).collect(Collectors.toList());
            Platform.runLater(() -> {
                activities.addAll(dates);
                activityList.setItems(activities);

            });
        }).start();

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
        HBox.setHgrow(pane, Priority.ALWAYS);
        if (one != null) {
            ImageView image1 = new ImageView(one.getImage());
            image1.setFitWidth(50);
            image1.setFitHeight(50);
            first.getChildren().addAll(image1, new Label(one.getText()), pane);
        }
        if (two != null) {
            ImageView image2 = new ImageView(two.getImage());
            image2.setFitHeight(50);
            image2.setFitWidth(50);
            second.getChildren().addAll(image2, new Label(two.getText()), pane);
        }
        if (three != null) {
            ImageView image3 = new ImageView(three.getImage());
            image3.setFitHeight(50);
            image3.setFitWidth(50);
            third.getChildren().addAll(image3, new Label(three.getText()), pane);
        }
    }

    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        System.out.println(username);
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
            HBox.setHgrow(pane, Priority.ALWAYS);
            HBox.setHgrow(pane2, Priority.ALWAYS);
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
