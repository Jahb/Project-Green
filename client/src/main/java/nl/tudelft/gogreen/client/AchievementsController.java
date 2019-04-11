package nl.tudelft.gogreen.client;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AchievementsController implements Initializable {

    @FXML
    public ListView<ListItem> userAchievements = new ListView<>();
    @FXML
    private ListView<ListItem> allAchievements = new ListView<>();

    private final ObservableList<ListItem> allAchievementsList = FXCollections
            .observableArrayList();
    private final ObservableList<ListItem> userAchievementsList = FXCollections
            .observableArrayList();

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
        BorderPane topPane = (BorderPane) root.getChildren().get(0);
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
        //TODO Add achievements to the list which contains all achievements
        allAchievementsList.clear();
        allAchievementsList.add(
                new ListItem("achievement1", "images/IconCupGold.png", "Description 1"));
        allAchievementsList.add(
                new ListItem("achievement2", "images/IconCupGold.png", "Description 2"));
        allAchievementsList.add(
                new ListItem("achievement3", "images/IconCupGold.png", "Description 3"));
        allAchievementsList.add(
                new ListItem("achievement4", "images/IconCupGold.png", "Description 4"));
        userAchievementsList.add(
                new ListItem("achievement2", "images/IconCupGold.png", "Description 2"));
        userAchievementsList.add(
                new ListItem("achievement4", "images/IconCupGold.png", "Description 4"));
        allAchievements.setCellFactory(param -> new Cell());
        userAchievements.setCellFactory(param -> new Cell());

        allAchievements.setItems(allAchievementsList);
        userAchievements.setItems(userAchievementsList);
    }

    class Cell extends ListCell<ListItem> {

        private Cell() {
            super();
        }

        @Override
        public void updateItem(ListItem item, boolean bool) {
            super.updateItem(item, bool);
            if (item != null && !bool) {
                setGraphic(ListItem.imageView(item));
                setText(item.getName() + "\n" + item.getStatus());

                if (!item.getStatus().equals("Not Yet")) {
                    this.setStyle("-fx-background-color: #38ba5c;" +
                            "-fx-text-fill: white;");
                } else {
                    this.setStyle("-fx-background-color: whitesmoke;" +
                            "-fx-text-fill: black;");
                }
            } else {
                setText(null);
                setGraphic(null);
            }
        }
    }

}
