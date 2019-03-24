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
    @FXML
    public AnchorPane createEvent;

    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/event.fxml");
        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(url);
        loader.getController();
        AnchorPane root = loader.load();
        BorderPane buttonPane = (BorderPane) root.getChildren().get(2);
        IconButton.addBackButton(buttonPane);

        //Adding NewEventButton
        AnchorPane newEventPane = (AnchorPane) root.getChildren().get(5);
        BorderPane newEvenBPane = (BorderPane) newEventPane.getChildren().get(0);
        IconButton newEventButton = new IconButton("Add", 154, 154);
        newEvenBPane.setCenter(newEventButton.getStackPane());

        createEvent = (AnchorPane) root.getChildren().get(7);
        newEventButton.setOnClick(event -> createEvent.setVisible(true));

        //Adding Accept and BackButton in New Event Pane
        BorderPane acceptButton = (BorderPane) createEvent.getChildren().get(0);
        BorderPane cancelButton = (BorderPane) createEvent.getChildren().get(1);

        IconButton acceptIcon = new IconButton("Confirm", 110, 110);
        IconButton cancelIcon = new IconButton("Deny", 110, 110);
        acceptButton.setCenter(acceptIcon.getStackPane());
        cancelButton.setCenter(cancelIcon.getStackPane());

        cancelIcon.setOnClick(event -> createEvent.setVisible(false));
        //Create Event Button
        acceptIcon.setOnClick(event -> System.out.println("TODO Create event"));
        acceptIcon.setOnClick(event -> createEvent.setVisible(false));


        return new Scene(root, Main.getWidth(), Main.getHeight());
    }


}
