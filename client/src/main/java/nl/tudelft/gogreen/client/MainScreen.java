package nl.tudelft.gogreen.client;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * MainScreen object
 *
 * @author Kamron Geijsen
 * @version 3.1415.9235.85
 */
public class MainScreen {

	private Ring ring;
	private IconButton leaderboardButton;
	private IconButton addButton;
	private IconButton helpButton;

	public Scene getScene() throws IOException {
		URL url = Main.class.getResource("/MainScreen.fxml");
		System.out.println(url);
		AnchorPane root = FXMLLoader.load(url);

		addMainRing(root);
		addIconButtons(root);
//		MainScreenController.helpText.setVisible(true);
		
		ring.startAnimation();
		Scene mainScreenScene = new Scene(root, Main.width, Main.height);
		return mainScreenScene;
	}

	void addMainRing(AnchorPane root) {
		ring = new Ring((int) (150 * .75), 150, Main.width / 2, 200);
		ring.addSegment(20, Color.LAWNGREEN);
		ring.addSegment(30, Color.YELLOW);
		ring.addSegment(40, Color.SANDYBROWN);
		ring.addNodes(root);

		root.widthProperty().addListener((obs, oldVal, newVal) -> {
			ring.setX(newVal.intValue() / 2);
		});
	}

	void addIconButtons(AnchorPane root) {
		leaderboardButton = new IconButton("Leaderboard", new Rectangle(50, 100, 150, 150));
		leaderboardButton.addNodes(root);
		addButton = new IconButton("Add", new Rectangle(200, 100, 600, 150));
		addButton.addNodes(root);
		helpButton = new IconButton("Help", new Rectangle(500, 100, 150, 150));
		helpButton.addNodes(root);

		root.widthProperty().addListener((obs, oldVal, newVal) -> {
			leaderboardButton.setX(0);
			addButton.setX(newVal.intValue() / 2 - 300);
			helpButton.setX(newVal.intValue() - 150);
		});

		root.heightProperty().addListener((obs, oldVal, newVal) -> {
			leaderboardButton.setY(newVal.intValue() - 175);
			addButton.setY(newVal.intValue() - 175);
			helpButton.setY(newVal.intValue() - 175);
		});
	}

}
