package nl.tudelft.gogreen.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

public class ProfileController implements Initializable {

	@FXML
	private ImageView profileImage;
	@FXML
	private ListView<ListItem> friendsList = new ListView<ListItem>();

	protected IconButton backButton;

	protected IconButton achievementsButton;

	//Added profile picture within circle
	@FXML
	StackPane imageContainer = new StackPane();
	@FXML
	Circle achievementCircle1;
	@FXML
	Circle achievementCircle2;
	@FXML
	Circle achievementCircle3;


	private final ObservableList<ListItem> items = FXCollections.observableArrayList();
	@FXML



	//setting placeholder pictures
	@Override
	public void initialize (URL location , ResourceBundle resources){

		Image profileImg = new Image("images/logo.png");
		Image achievementImg = new Image("images/achievementImage.png");
		achievementCircle1.setFill(new ImagePattern(achievementImg));
		achievementCircle2.setFill(new ImagePattern(achievementImg));
		achievementCircle3.setFill(new ImagePattern(achievementImg));

		items.clear();
		items.add(new ListItem("profile1", "images/achievementImage.png"));
		items.add(new ListItem("profile2", "images/achievementImage.png"));
		items.add(new ListItem("profile3", "images/achievementImage.png"));
		friendsList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>(){

			@Override
			public ListCell<ListItem> call (ListView<ListItem> arg0){
				ListCell<ListItem> cell = new ListCell<ListItem>(){
					@Override
					protected void updateItem(ListItem item, boolean bool){
						super.updateItem(item, bool);
						if(item!=null){
							Image img = new Image(getClass().getResource("/" + item.getImageLocation()).toExternalForm());
							ImageView imgview = new ImageView(img);
							imgview.setFitHeight(90);
							imgview.setFitWidth(100);
							setGraphic(imgview);
							setText(item.getName());
						}
					}
				};
				return cell;
			}
		});
		friendsList.setItems(items);
	}


	public Scene getScene() throws IOException {
		URL url = Main.class.getResource("/ProfileGUI.fxml");
		System.out.println(url);
		AnchorPane root = FXMLLoader.load(url);
		BorderPane buttonPane = (BorderPane) root.getChildren().get(0);
		IconButton.addBackButton(buttonPane);
		BorderPane bottomPane = (BorderPane) root.getChildren().get(1);
		addLowerButtons(bottomPane);
		Scene leaderboardScene = new Scene(root, Main.getWidth(), Main.getHeight());
		return leaderboardScene;
	}


	private void addLowerButtons(BorderPane root){
		achievementsButton= new IconButton("Achievements",100 ,100 );
		root.setLeft(achievementsButton.getStackPane());
		achievementsButton.setOnClick(event ->{
			Main.openAchievementsScreen();
		});
	}
}
