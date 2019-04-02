package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView achievementsList;
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

}
