package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static javafx.scene.layout.Priority.ALWAYS;

public class NewProfileController implements Initializable {

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
    private ListView activityList;
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

        //TODO Removes Follow Button From Scene If condition is met.
        if (false) {
            topRightBox.getChildren().remove(follow);
        }

        //TODO Adding Achievements just pull 3 most recent and call AddAchievements Method
        ListItem achievement1 = new ListItem(new Image("/images/IconCupGold.png"), "Achievement 1");
        ListItem achievement2 = new ListItem(new Image("/images/IconCupSilver.png"), "Achievement 2");
        ListItem achievement3 = new ListItem(new Image("/images/IconCupBronze.png"), "Achievement 3");
        addAchievements(achievement1, achievement2, achievement3);
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
        ImageView image = new ImageView();

        private Cell() {
            super();
            listCell.getChildren().addAll(image, pane, itemName);
            HBox.setHgrow(pane, ALWAYS);
            listCell.setAlignment(Pos.CENTER);
            image.setFitHeight(50);
            image.setFitWidth(50);
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

}
