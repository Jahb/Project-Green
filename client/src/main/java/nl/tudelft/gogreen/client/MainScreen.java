package nl.tudelft.gogreen.client;

import java.io.IOException;
import java.net.URL;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * MainScreen object.
 *
 * @author Kamron Geijsen
 * @version 4.0
 */
public class MainScreen {

    private Ring ring;
    private IconButton leaderboardButton;
    private IconButton addButton;
    private IconButton helpButton;
    private TextArea helpText;

    /**
     * Creates a scene for MainScreen.
     * 
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/MainScreen.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);

        helpText = (TextArea) root.getChildren().get(0);
        BorderPane buttonPane = (BorderPane) root.getChildren().get(1);

        addMainRing(root);
        addIconButtons(buttonPane);

        ring.startAnimation();
        helpText.toFront();
        Scene mainScreenScene = new Scene(root, Main.width, Main.height);
        return mainScreenScene;
    }

    private void addMainRing(AnchorPane root) {
        ring = new Ring((int) (150 * .75), 150, Main.width / 2, 200);
        ring.addSegment(20, Color.LAWNGREEN);
        ring.addSegment(30, Color.YELLOW);
        ring.addSegment(40, Color.SANDYBROWN);
        root.getChildren().add(ring.getPane());

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            ring.setX(newVal.intValue() / 2);
        });
    }

    private void addIconButtons(BorderPane root) {
        leaderboardButton = new IconButton("Leaderboard", 150, 150);
        addButton = new IconButton("Add", 600, 150);
        helpButton = new IconButton("Help", 150, 150);

        root.setLeft(leaderboardButton.getStackPane());
        root.setCenter(addButton.getStackPane());
        root.setRight(helpButton.getStackPane());

        helpButton.setOnClick(event -> {
            helpText.setVisible(!helpText.isVisible());

        });
        addButton.setOnClick(event -> {
            // TODO - this event is called when addButton is clicked

        });
        leaderboardButton.setOnClick(event -> {
            // TODO - this event is called when leaderbaordButton is clicked
            Main.openLeaderboardScreen();
        });
    }

}
