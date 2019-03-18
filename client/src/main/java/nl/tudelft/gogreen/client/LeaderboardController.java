package nl.tudelft.gogreen.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {


    @FXML
    private VBox labelVbox;
    @FXML
    private ListView<ListItem> leaderboardList = new ListView<>();
    private final ObservableList<ListItem> items = FXCollections.observableArrayList();

    /**
     * Return the LeaderBoard GUI Scene.
     * @return LeaderBoard Scene.
     * @throws IOException An Exception
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/LeaderboardGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane topPane = (BorderPane) root.getChildren().get(2);
        IconButton.addBackButton(topPane);
        VBox buttonBox = (VBox) root.getChildren().get(3);
        addTimeFrameButtons(buttonBox);
        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    /**
     * Initializes images for class.
     * @param location Image URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        labelVbox.setMouseTransparent(true);
        items.clear();
        items.add(new ListItem("profile1", "images/achievementImage.png", 3000));
        items.add(new ListItem("profile2", "images/achievementImage.png", 420));
        items.add(new ListItem("profile3", "images/achievementImage.png", 3));
        leaderboardList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                ListCell<ListItem> cell = new ListCell<ListItem>() {
                    @Override
                    protected void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null && !bool) {
                            Image img = new Image(getClass()
                                    .getResource("/" + item.getImageLocation()).toExternalForm());
                            ImageView imgview = new ImageView(img);
                            imgview.setFitHeight(90);
                            imgview.setFitWidth(90);
                            setGraphic(imgview);
                            setText(item.getName() + "\nScore: " + item.getScore());

                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                cell.setEditable(true);
                cell.setOnMouseReleased((MouseEvent event) -> {
                    if (cell.isEmpty()) {
                        event.consume();
                    } else Main.openProfileScreen();
                });
                return cell;
            }
        });
        leaderboardList.setEditable(true);
        System.out.print(leaderboardList.isEditable());
        leaderboardList.setItems(items);

    }


    private void addTimeFrameButtons(VBox root) {
        IconButton dayButton = new IconButton("Empty", 450, 100);
        IconButton weekButton = new IconButton("Empty", 450, 100);
        IconButton monthButton = new IconButton("Empty", 450, 100);
        IconButton overallButton = new IconButton("Empty", 450, 100);
        root.getChildren().addAll(dayButton.getStackPane(),
                weekButton.getStackPane(),
                monthButton.getStackPane(),
                overallButton.getStackPane());
        weekButton.setOnClick(event -> leaderboardList.setItems(null));
    }

}


