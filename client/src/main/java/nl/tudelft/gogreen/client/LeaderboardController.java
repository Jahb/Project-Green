package nl.tudelft.gogreen.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {


    private IconButton dayButton;
    private IconButton weekButton;
    private IconButton monthButton;
    private IconButton overallButton;
    @FXML
    private VBox labelVbox;

    //scene switching via button
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/LeaderboardGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane topPane = (BorderPane) root.getChildren().get(2);
        IconButton.addBackButton(topPane);
        VBox buttonBox = (VBox) root.getChildren().get(3);
        addTimeframeButtons(buttonBox);
        Scene leaderboardScene = new Scene(root, Main.getWidth(), Main.getHeight());
        return leaderboardScene;
    }

    @FXML
    private ListView<ListItem> leaderboardList = new ListView<ListItem>();
    public final ObservableList<ListItem> items = FXCollections.observableArrayList();


    //method for adding pictures and text to ListView
    public void initialize(URL location, ResourceBundle resources) {
        labelVbox.setMouseTransparent(true);
        items.clear();
        items.add(new ListItem("profile1", "achievementImage.png", 3000));
        items.add(new ListItem("profile2", "achievementImage.png", 420));
        items.add(new ListItem("profile3", "achievementImage.png", 3));
        leaderboardList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                ListCell<ListItem> cell = new ListCell<ListItem>() {
                    @Override
                    protected void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null && !bool) {
                            Image img = new Image(getClass().getResource("/" + item.getImageLocation()).toExternalForm());
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


    private void addTimeframeButtons(VBox root) {
        dayButton = new IconButton("Empty", 450, 100);
        weekButton = new IconButton("Empty", 450, 100);
        monthButton = new IconButton("Empty", 450, 100);
        overallButton = new IconButton("Empty", 450, 100);
        root.getChildren().addAll(dayButton.getStackPane(),
                weekButton.getStackPane(),
                monthButton.getStackPane(),
                overallButton.getStackPane());
        weekButton.setOnClick(event -> {
            leaderboardList.setItems(null);
        });
    }

}


