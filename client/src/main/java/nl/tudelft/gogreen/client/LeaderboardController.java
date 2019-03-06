package nl.tudelft.gogreen.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    private IconButton backButton;

    //scene switching via button
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/LeaderboardGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane buttonPane = (BorderPane) root.getChildren().get(2);
        addBackButton(buttonPane);
        Scene leaderboardScene = new Scene(root, Main.getWidth(), Main.getHeight());
        return leaderboardScene;
    }

    @FXML
    private ListView<ListItem> leaderboardList = new ListView<ListItem>();
    private final ObservableList<ListItem> items = FXCollections.observableArrayList();

    //method for adding pictures and text to ListView
    public void initialize(URL location, ResourceBundle resources) {

        Image profileImg = new Image("/logo.png");
        Image achievementImg = new Image("/achievementImage.png");


        items.clear();
        items.add(new ListItem("profile1", "achievementImage.png"));
        items.add(new ListItem("profile2", "achievementImage.png"));
        items.add(new ListItem("profile3", "achievementImage.png"));
        leaderboardList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {

            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                ListCell<ListItem> cell = new ListCell<ListItem>() {
                    @Override
                    protected void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null) {
                            Image img = new Image(getClass().getResource("/" + item.getImageLocation()).toExternalForm());
                            ImageView imgview = new ImageView(img);
                            imgview.setFitHeight(90);
                            imgview.setFitWidth(90);
                            setGraphic(imgview);
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
        leaderboardList.setItems(items);
    }

    private void addBackButton(BorderPane root){
        backButton= new IconButton("Back",100 ,100 );
        root.setLeft(backButton.getStackPane());
        backButton.setOnClick(event ->{
            Main.openMainScreen();
        });
    }
}


