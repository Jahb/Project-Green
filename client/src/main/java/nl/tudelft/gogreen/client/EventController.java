package nl.tudelft.gogreen.client;

import static javafx.scene.layout.Priority.ALWAYS;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.application.Platform;
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
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.shared.EventItem;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class EventController implements Initializable {

    private static ObservableList<EventItem> allEvents = FXCollections.observableArrayList();
    private static ObservableList<EventItem> userEvents = FXCollections.observableArrayList();

    @FXML
    public BorderPane buttonPane;
    @FXML
    public ListView<EventItem> eventList;
    @FXML
    public ListView<EventItem> fullEventList;
    @FXML
    public AnchorPane createEvent;
    @FXML
    public JFXTextField newEventName;
    @FXML
    public JFXDatePicker newEventDate;
    @FXML
    public JFXTextArea newEventDesc;
    @FXML
    public JFXTimePicker newEventTime;

    private AnchorPane root;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * Returns ProfileGUI Scene.
     *
     * @return ProfileGUI Scene
     * @throws IOException IO Exception may be thrown
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/EventScreen.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        loader.getController();

        root = loader.load();
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

        acceptIcon.setOnClick(event -> addNewEvent());

        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    private void addNewEvent() {
        AnchorPane base = (AnchorPane) root.getChildren().get(7);

        EventItem newEvent = new EventItem(((JFXTextField) base.getChildren().get(2)).getText(),
                ((JFXTextArea) base.getChildren().get(4)).getText(),
                ((JFXTimePicker) base.getChildren().get(5)).getValue().format(timeFormat),
                ((JFXDatePicker) base.getChildren().get(3)).getValue().format(dateFormat));
        createEvent.setVisible(false);
        new Thread(() -> Api.current.newEvent(newEvent)).start();
        refreshEvents();
    }

    private void refreshEvents() {
        new Thread(() -> {
            List<EventItem> all = Api.current.getAllEvents();
            List<EventItem> user = Api.current.getUserEvents();
            Platform.runLater(() -> {
                allEvents.clear();
                allEvents.addAll(all);
                userEvents.clear();
                userEvents.addAll(user);
            });
        }).start();
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
        refreshEvents();

        fullEventList.setItems(allEvents);
        fullEventList.setCellFactory(param -> new Cell(userEvents));

        eventList.setItems(userEvents);
        eventList.setCellFactory(param -> new Cell());

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
                eventName.setText(event.getName());
                setGraphic(listCell);

                String hoverText = "Date:\n" + getItem().getDate() +
                        "\n\nStart Time:\n" + getItem().getTime() +
                        "\n\nDescription: \n" + getItem().getDescription();
                Tooltip.install(this.eventName, new Tooltip(hoverText));

            }
        }

        private void joinEvent(ObservableList<EventItem> events) {
            events.add(getItem());
            new Thread(() -> Api.current.joinEvent(getItem().getName())).start();
            refreshEvents();
        }

        private void leaveEvent() {
            Api.current.leaveEvent(getItem().getName());
            refreshEvents();
            this.getListView().getItems().remove(getItem());
        }

    }

}
