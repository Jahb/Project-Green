package nl.tudelft.gogreen.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class EventController {

    @FXML
    public ListView eventList;
    @FXML
    public ListView fullEventList;

    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/event.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane buttonPane = (BorderPane) root.getChildren().get(2);
        IconButton.addBackButton(buttonPane);

        //Adding NewEventButton
        AnchorPane newEventPane = (AnchorPane) root.getChildren().get(5);
        BorderPane newEvenBPane = (BorderPane) newEventPane.getChildren().get(0);
        IconButton newEventButton = new IconButton("Add", 154, 154);
        newEvenBPane.setCenter(newEventButton.getStackPane());

        return new Scene(root, Main.getWidth(), Main.getHeight());
    }


}
