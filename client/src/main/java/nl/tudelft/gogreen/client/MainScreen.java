package nl.tudelft.gogreen.client;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * MainScreen object.
 *
 * @author Kamron Geijsen
 * @version 4.20
 */
public class MainScreen {

	private Ring ring;
	private TextArea helpText;

	/**
	 * Creates a scene for MainScreen.
	 */
	public Scene getScene() throws IOException {
		URL url = Main.class.getResource("/MainScreen.fxml");
		System.out.println(url);
		StackPane root = FXMLLoader.load(url);
		System.out.println(root);
		
		BorderPane baseLayer = (BorderPane) root.getChildren().get(0);
		AnchorPane mainRingPane = (AnchorPane) baseLayer.getCenter();
		
		AnchorPane overlayLayer = (AnchorPane) root.getChildren().get(1);
		BorderPane buttonsPanel = (BorderPane) overlayLayer.getChildren().get(1);
		helpText = (TextArea) overlayLayer.getChildren().get(0);
		
		addMainRing(mainRingPane);
		addIconButtons(buttonsPanel);

		ring.startAnimation();
		helpText.setVisible(false);
		return new Scene(root, Main.getWidth(), Main.getHeight());
	}

	private void addMainRing(AnchorPane anchorPane) {
		ring = new Ring((int) (150 * .75), 150, Main.getHeight() / 2, 200);
		ring.addSegment(38, Color.LIME);
		ring.addSegment(20, Color.YELLOW);
		ring.addSegment(15, Color.GREEN);
		anchorPane.getChildren().add(ring.getPane());

		anchorPane	.widthProperty().addListener((obs, oldVal, newVal) -> ring.setX(newVal.intValue() >> 1));
	}

	private void addIconButtons(BorderPane root) {
		IconButton leaderboardButton = new IconButton("Leaderboard", 150, 150);
		IconButton addButton = new IconButton("Add", 600, 150);
		IconButton helpButton = new IconButton("Help", 150, 150);

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
			Main.openLeaderboardScreen();
		});
	}

}
