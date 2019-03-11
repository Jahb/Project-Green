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

public class AchievementsController implements Initializable {

    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/AchievementsGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane topPane = (BorderPane) root.getChildren().get(1);
        IconButton.addBackButton(topPane);
        Scene achievementsScene = new Scene(root, Main.getWidth(), Main.getHeight());
        return achievementsScene;
    }

    @FXML
    private ListView<ListItem> achievementsList = new ListView<ListItem>();
    public final ObservableList<ListItem> items = FXCollections.observableArrayList();


    //method for adding pictures and text to ListView
    public void initialize(URL location, ResourceBundle resources) {
        items.clear();
        items.add(new ListItem("achievement1", "images/achievementImage.png", "2/10"));
        items.add(new ListItem("achievement2", "images/achievementImage.png", "50/100"));
        items.add(new ListItem("achievement3", "images/achievementImage.png", "Completed"));
        achievementsList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

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
                            setText(item.getName() + "\nProgress: " + item.getStatus());
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                cell.setEditable(true);
                return cell;
            }
        });
        achievementsList.setItems(items);
    }

}
