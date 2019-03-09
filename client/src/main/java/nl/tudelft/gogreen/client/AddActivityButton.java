package nl.tudelft.gogreen.client;

import java.awt.Event;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AddActivityButton {
	public AddActivityButton() {
		activityButtonPane = new AnchorPane();
		backgroundPane = new Pane();
		backgroundPane.setBackground(new Background(
				new BackgroundFill(new Color(1, 1, 1, .8), new CornerRadii(40, 40, 0, 0, false), Insets.EMPTY)));
		backgroundPane.setPrefWidth(600.0 * 31 / 32);
		backgroundPane.setPrefHeight(200);
//		backgroundPane.setLayoutX(0);

		activityButtonPane.getChildren().add(backgroundPane);
		activityButtonPane.setPrefWidth(600 * 15 / 16);
		activityButtonPane.setPrefWidth(200);
		activityButtonPane.setLayoutX(500 - 600.0 * 31 / 64);
		activityButtonPane.setLayoutY(720 - 200 - 75);

		CategoryButton foodButton = new CategoryButton("Food", CategoryButtonType.LEFT, 0);
		CategoryButton transportButton = new CategoryButton("Transport", CategoryButtonType.CENTER, 1);
		CategoryButton energyButton = new CategoryButton("Energy", CategoryButtonType.CENTER, 2);
		CategoryButton habitButton = new CategoryButton("Habit", CategoryButtonType.RIGHT, 3);

		foodButton.addNodes(activityButtonPane);
		transportButton.addNodes(activityButtonPane);
		energyButton.addNodes(activityButtonPane);
		habitButton.addNodes(activityButtonPane);
	}

	private AnchorPane activityButtonPane;
	private Pane backgroundPane;

	public Pane getPane() {
		return activityButtonPane;
	}

	private class CategoryButton {
		protected CategoryButton(String name, CategoryButtonType type, int index) {

			final int width = 125;
			final int height = 70;
			final int x = 40 + index * width;
			final int y = 40;

			background = new Pane();
			CornerRadii cornerRadii = new CornerRadii(0);
			if (type == CategoryButtonType.LEFT)
				cornerRadii = new CornerRadii(20, 0, 0, 0, false);
			if (type == CategoryButtonType.RIGHT)
				cornerRadii = new CornerRadii(0, 20, 0, 0, false);
			background.setBackground(new Background(new BackgroundFill(IconButton.color, cornerRadii, Insets.EMPTY)));
			background.setLayoutX(x);
			background.setLayoutY(y);
			background.setPrefWidth(width - 2);
			background.setPrefHeight(height);

//			icon = new ImageView("Icon" + name + ".png");
			icon = new ImageView("IconHelp.png");
			icon.setFitWidth(40);
			icon.setFitHeight(40);
			icon.setX(x + width - 40);
			icon.setY(y + height - 40);

			this.name = new Text(name);
			this.name.setX(x + 13);
			this.name.setY(y + 30);
			this.name.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
			this.name.setFill(Color.WHITE);
			
			background.setOnMousePressed(event -> mousePress(true));
			background.setOnMouseReleased(event -> mousePress(false));
			background.setOnMouseEntered(event -> mouseOver(true));
			background.setOnMouseExited(event -> mouseOver(false));
	    }
		
		
	    private void mouseOver(boolean mouseOver) {
	        this.mouseOver = mouseOver;
	        if (mouseOver)
	        	setFill(IconButton.colorMouseOver);
	        else
	        	setFill(IconButton.color);
	    }

	    private void mousePress(boolean mousePress) {
	        if (mousePress)
	        	setFill(IconButton.colorMouseDown);
	        else if (mouseOver)
	        	setFill(IconButton.colorMouseOver);
	        else
	        	setFill(IconButton.color);
	    }
	    
	    private void setFill(Color color) {
	    	CornerRadii cornerRadii = background.getBackground().getFills().get(0).getRadii();
			background.setBackground(new Background(new BackgroundFill(color, cornerRadii, Insets.EMPTY)));
	    }
	    
	    private boolean mouseOver = false;
	    
		private Pane background;
		private Text name;
		private ImageView icon;

		public void addNodes(AnchorPane anchorPane) {
			anchorPane.getChildren().addAll(background, icon, name);
		}
	}

	enum CategoryButtonType {
		LEFT, CENTER, RIGHT;
	}
}
