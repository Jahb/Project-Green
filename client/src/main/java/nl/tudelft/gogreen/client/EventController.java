package nl.tudelft.gogreen.client;

import static javafx.scene.layout.Priority.ALWAYS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    @FXML
    public ListView<EventItem> eventList;
    @FXML
    public ListView<EventItem> fullEventList;
    @FXML
    public AnchorPane createEvent;

    private ObservableList<EventItem> allEvents = FXCollections.observableArrayList();
    private ObservableList<EventItem> userEvents = FXCollections.observableArrayList();


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
        acceptIcon.setOnClick(event -> addNewEvent());
        acceptIcon.setOnClick(event -> createEvent.setVisible(false));

        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    private void addNewEvent() {
        //TODO for adding event to DataBase
        System.out.println("Adding Event");
        //Not sure if this help but a new EventItem Object should be created
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

        //Dummy data for the allEvents List.
        EventItem event1 = new EventItem("Hippy Bullshit At the Park",
                "We do it at the park",
                "12:45am",
                "10/12/2019");
        EventItem event2 = new EventItem("Going to the beach to pick up trash and shit",
                "IDK lets go green",
                "13:40pm",
                "5/2/2018");
        EventItem event3 = new EventItem("Lonely dudes who just wanna go green",
                "We sad and want some tree GFs",
                "11:14pm",
                "1/1/2020");


        allEvents.addAll(event1, event2, event3);
        fullEventList.setItems(allEvents);
        fullEventList.setCellFactory(param -> new Cell(userEvents));

        eventList.setItems(userEvents);
        eventList.setCellFactory(param -> new Cell());

    }

    /**
     * Helper Class that creates and Event object which stores a Label and a Description.
     * Can be removed if believed to be inefficent.
     */
    private class EventItem {
        private String label;
        private String description;
        private String time;
        private String date;

        private EventItem(String label, String description, String time, String date) {
            this.label = label;
            this.description = description;
            this.time = time;
            this.date = date;
        }

        private String getLabel() {
            return this.label;
        }

        private String getDescription() {
            return this.description;
        }

        private String getTime() {
            return this.time;
        }

        private String getDate() {
            return this.date;
        }
    }

    /**
     * Helper Class for Cells in both listViews.
     */
    class Cell extends ListCell<EventItem> {

        HBox listCell = new HBox();
        Label eventName = new Label();
        Button button;
        Pane pane = new Pane();

        private Cell() {
            super();
            listCell.getChildren().addAll(eventName, pane, button = new Button("Leave Event"));
            button.setId("button");
            button.setOnAction(event -> leaveEvent());
            HBox.setHgrow(pane, ALWAYS);

        }

        private Cell(ObservableList<EventItem> events) {
            super();
            listCell.getChildren().addAll(eventName, pane, button = new Button("Join Event"));
            button.setOnAction(event -> joinEvent(events));
            button.setId("button");
            HBox.setHgrow(pane, ALWAYS);

        }

        public void updateItem(EventItem event, boolean empty) {
            super.updateItem(event, empty);
            setText(null);
            setGraphic(null);

            if (event != null && !empty) {
                eventName.setText(event.getLabel());
                setGraphic(listCell);
                String hoverText = "Date:\n" + getItem().getDate() + "\n\nStart Time:\n" + getItem().getTime() + "\n\nDescription: \n" + getItem().getDescription();
                Tooltip.install(this.eventName, new Tooltip(hoverText));

            }
        }

        private void joinEvent(ObservableList<EventItem> events) {
            //TODO for event join
            events.add(getItem());
        }

        private void leaveEvent() {
            //TODO for event leave
            this.getListView().getItems().remove(getItem());
        }

    }

}
