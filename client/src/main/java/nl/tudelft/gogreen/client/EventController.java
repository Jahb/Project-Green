package nl.tudelft.gogreen.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Priority.ALWAYS;

public class EventController implements Initializable {

    @FXML
    public ListView eventList;
    @FXML
    public ListView fullEventList;
    @FXML
    public AnchorPane createEvent;

    ObservableList<String> allEvents = FXCollections.observableArrayList();
    ObservableList<String> userEvents = FXCollections.observableArrayList();


    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/event.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        loader.getController();
        AnchorPane root = loader.load();
        System.out.println(url);
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

        allEvents.addAll("Hippy Bullshit At the Park", "Going to the beach to pick up trash and shit", "Lonely dudes who just wanna go green");
        fullEventList.setItems(allEvents);
        fullEventList.setCellFactory(param -> new Cell(userEvents));

        eventList.setItems(userEvents);
        eventList.setCellFactory(param -> new Cell());

    }

    static class Cell extends ListCell<String> {

        HBox cell = new HBox();
        Label label = new Label("");
        Button button;
        Pane pane = new Pane();

        public Cell() {
            super();
            cell.getChildren().addAll(label, pane, button = new Button("Leave Event"));
            button.setOnAction(event -> leaveEvent());
            cell.setHgrow(pane, ALWAYS);
        }

        public Cell(ObservableList<String> events) {
            super();
            cell.getChildren().addAll(label, pane, button = new Button("Join Event"));
            button.setOnAction(event -> joinEvent(events));
            cell.setHgrow(pane, ALWAYS);

        }

        public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);
            setText(null);
            setGraphic(null);

            if (name != null && !empty) {
                label.setText(name);
                setGraphic(cell);
            }
        }

        private void joinEvent(ObservableList<String> events) {
            //TODO for event join
            events.add(getItem());
        }

        private void leaveEvent() {
            //TODO for event leave
            this.getListView().getItems().remove(getItem());
        }

    }


}
