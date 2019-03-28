package nl.tudelft.gogreen.client;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import nl.tudelft.gogreen.client.communication.API;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * MainScreen object.
 *
 * @author Kamron Geijsen
 * @version 4.20.19
 */
public class MainScreen {

    private Ring ring;
    private TextArea helpText;
    private AddActivityButton activityButton;
    // TODO handler for each subcategory
    private Consumer<String> handler = name -> {
        try {
            int res = API.current.addFeature(name);

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    };
    //TODO handler for each ring category
    private Consumer<String> ringHandler = name -> System.out.println("EXE [" + name + "]");

    /**
     * Creates a scene for MainScreen.
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/MainScreen.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);

        BorderPane baseLayer = (BorderPane) root.getChildren().get(0);
        AnchorPane mainRingPane = (AnchorPane) baseLayer.getCenter();
        BorderPane topPane = (BorderPane) baseLayer.getTop();
        HBox topButtons = (HBox) topPane.getRight();

        AnchorPane overlayLayer = (AnchorPane) root.getChildren().get(1);
        helpText = (TextArea) overlayLayer.getChildren().get(0);
        BorderPane buttonsPanel = (BorderPane) overlayLayer.getChildren().get(1);

        addMainRing(mainRingPane);
        addTopMenuButtons(topButtons);
        addIconButtons(buttonsPanel);
        addActivityButton(overlayLayer);

        ring.startAnimation();
        helpText.setVisible(false);
        overlayLayer.setPrefSize(1000, 720);

        overlayLayer.setPickOnBounds(false);
        buttonsPanel.setPickOnBounds(false);
        Scene scene = new Scene(root, Main.getWidth(), Main.getHeight());
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node != null && !activityButton.contains(node))
                if (!(node.getId() + "").equals("Add"))
                    activityButton.close();
        });
        return scene;
    }

    private void addMainRing(AnchorPane anchorPane) {
        ring = new Ring((int) (150 * .75), 150, Main.getHeight() / 2, 200);
        ring.addSegment(0, Color.LIME, "Food");
        ring.addSegment(0, Color.YELLOW, "Energy");
        ring.addSegment(0, Color.GREEN, "Transport");
        ring.setHandler(ringHandler);
        float calc = ((float) API.current.getTotal() / 1000);
        int i = (int) (calc * 100);
        System.out.println(i);
        ring.setSegmentValue(i, 0);
        ring.setSegmentValue(i, 1);
        ring.setSegmentValue(i, 2);
        anchorPane.getChildren().add(ring.getPane());

        anchorPane.widthProperty()
                .addListener((obs, oldVal, newVal) -> ring.setX(newVal.intValue() / 2));
    }

    private void addActivityButton(AnchorPane anchorPane) {
        activityButton = new AddActivityButton();
        activityButton.setHandler(handler);
        anchorPane.getChildren().add(0, activityButton.getPane());
    }

    private void addIconButtons(BorderPane root) {
        IconButton leaderboardButton = new IconButton("Leaderboard", 150, 150);
        IconButton addButton = new IconButton("Add", 600, 150);
        IconButton eventButton = new IconButton("Event", 150, 150);


        root.setLeft(leaderboardButton.getStackPane());
        root.setCenter(addButton.getStackPane());
        root.setRight(eventButton.getStackPane());

        eventButton.setOnClick(event -> Main.openEventScreen());

        addButton.setOnClick(event -> activityButton.open());
        leaderboardButton.setOnClick(event -> Main.openLeaderboardScreen());
    }

    /**
     * Method which adds button to HBox on TopRight of MainScreen.
     *
     * @param hbox A Hbox Container.
     */
    private void addTopMenuButtons(HBox hbox) {
        IconButton helpButton = new IconButton("Help", 70, 70);
        BorderPane root = (BorderPane) hbox.getChildren().get(1);
        root.setCenter(helpButton.getStackPane());

        helpButton.setOnClick(event -> helpText.setVisible(!helpText.isVisible()));
    }

}
