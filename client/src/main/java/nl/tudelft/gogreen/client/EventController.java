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

        //Adding Accept and BackButton in New Event Pane
        AnchorPane addEventPane = (AnchorPane) root.getChildren().get(8);
        BorderPane acceptButton = (BorderPane) addEventPane.getChildren().get(0);
        BorderPane cancelButton = (BorderPane) addEventPane.getChildren().get(1);

        IconButton acceptIcon = new IconButton("Confirm", 110, 110);
        IconButton cancelIcon = new IconButton("Deny", 110, 110);
        acceptButton.setCenter(acceptIcon.getStackPane());
        cancelButton.setCenter(cancelIcon.getStackPane());


        return new Scene(root, Main.getWidth(), Main.getHeight());
    }


}
