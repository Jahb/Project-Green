package nl.tudelft.gogreen.client;



import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class AddActivityButton {
	public AddActivityButton() {
		activityButtonPane = new AnchorPane();
		backgroundPane = new Pane();
		backgroundPane.setBackground(new Background(new BackgroundFill(new Color(1,1,1,.8), new CornerRadii(40, 40, 0, 0, false), Insets.EMPTY)));
		backgroundPane.setPrefWidth(600.0*31/32);
		backgroundPane.setPrefHeight(200);
//		backgroundPane.setLayoutX(0);
		
		activityButtonPane.getChildren().add(backgroundPane);
		activityButtonPane.setPrefWidth(600*15/16);
		activityButtonPane.setPrefWidth(200);
		activityButtonPane.setLayoutX(500-600.0*31/64);
		activityButtonPane.setLayoutY(720-200-75);
	}
	
	AnchorPane activityButtonPane;
	Pane backgroundPane;
	
	
	
	public Pane getPane() {
		return activityButtonPane;
	}
}
