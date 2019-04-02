package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController implements Initializable {

    @FXML
    private ListView<ListItem> achievementsList = new ListView<>();

    private final ObservableList<ListItem> items = FXCollections.observableArrayList();

    /**
     * Returns the Achievements Scene.
     *
     * @return Achievements Scene.
     * @throws IOException An Exception.
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/AchievementsGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane topPane = (BorderPane) root.getChildren().get(1);
        IconButton.addBackButton(topPane);
        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    /**
     * Initialises List and adds images to it.
     *
     * @param location  URL of images
     * @param resources A ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
        items.clear();
        items.add(new ListItem("achievement1", "images/achievementImage.png", "2/10"));
        items.add(new ListItem("achievement2", "images/achievementImage.png", "50/100"));
        items.add(new ListItem("achievement3", "images/achievementImage.png", "Completed"));
        achievementsList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                JFXListCell<ListItem> cell = new JFXListCell<ListItem>() {
                    @Override
                    public void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null && !bool) {
                            setGraphic(ListItem.imageView(item));
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
