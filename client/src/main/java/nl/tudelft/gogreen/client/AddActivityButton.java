package nl.tudelft.gogreen.client;

import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
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
				new BackgroundFill(new Color(1, 1, 1, .8), new CornerRadii(60, 60, 0, 0, false), Insets.EMPTY)));
		backgroundPane.setPrefWidth(600.0 * 31 / 32);
		backgroundPane.setPrefHeight(200);
		backgroundPane.setOnMousePressed(event -> {
			foodButton.subBackground.setVisible(false);
			transportButton.subBackground.setVisible(false);
			energyButton.subBackground.setVisible(false);
			habitButton.subBackground.setVisible(false);
		});
//		backgroundPane.setLayoutX(0);

		text = new Text("Record new GREEN activity");
		text.setX(50);
		text.setY(35);
		text.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
		text.setMouseTransparent(true);

		activityButtonPane.getChildren().add(backgroundPane);
		activityButtonPane.getChildren().add(text);
		activityButtonPane.setPrefWidth(600 * 15 / 16);
		activityButtonPane.setPrefWidth(200);
		activityButtonPane.setLayoutX(500 - 600.0 * 31 / 64);
		activityButtonPane.setLayoutY(720 - 200 - 75);
		activityButtonPane.setVisible(false);

		foodButton = new CategoryButton("Food", CategoryButtonCornerType.LEFT, 0);
		transportButton = new CategoryButton("Transport", CategoryButtonCornerType.CENTER, 1);
		energyButton = new CategoryButton("Energy", CategoryButtonCornerType.CENTER, 2);
		habitButton = new CategoryButton("Habit", CategoryButtonCornerType.RIGHT, 3);

		foodButton.addNodes(activityButtonPane);
		transportButton.addNodes(activityButtonPane);
		energyButton.addNodes(activityButtonPane);
		habitButton.addNodes(activityButtonPane);

		allNodes.add(activityButtonPane);
		allNodes.add(text);
		allNodes.add(backgroundPane);
	}

	private AnchorPane activityButtonPane;
	private Pane backgroundPane;
	private Text text;

	CategoryButton foodButton;
	CategoryButton transportButton;
	CategoryButton energyButton;
	CategoryButton habitButton;

	private HashSet<Node> allNodes = new HashSet<Node>(31);

	public boolean contains(Node node) {
		return allNodes.contains(node);
	}

	public Pane getPane() {
		return activityButtonPane;
	}

	public void setVisible(boolean visible) {
		activityButtonPane.setVisible(visible);
		foodButton.subBackground.setVisible(false);
		transportButton.subBackground.setVisible(false);
		energyButton.subBackground.setVisible(false);
		habitButton.subBackground.setVisible(false);
	}

	private class CategoryButton {
		protected CategoryButton(String name, CategoryButtonCornerType type, int index) {

			final int width = 125;
			final int height = 70;
			final int x = 40 + index * width;
			final int y = 50;

			background = new Pane();
			CornerRadii cornerRadii = new CornerRadii(0);
			if (type == CategoryButtonCornerType.LEFT)
				cornerRadii = new CornerRadii(20, 0, 0, 0, false);
			if (type == CategoryButtonCornerType.RIGHT)
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
			icon.setMouseTransparent(true);

			this.name = new Text(name);
			this.name.setX(x + 13);
			this.name.setY(y + 30);
			this.name.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
			this.name.setFill(Color.WHITE);
			this.name.setMouseTransparent(true);

			background.setOnMousePressed(event -> mousePress(true));
			background.setOnMouseReleased(event -> mousePress(false));
			background.setOnMouseEntered(event -> mouseOver(true));
			background.setOnMouseExited(event -> mouseOver(false));

			background.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
				foodButton.subBackground.setVisible(false);
				transportButton.subBackground.setVisible(false);
				energyButton.subBackground.setVisible(false);
				habitButton.subBackground.setVisible(false);
				
				subBackground.setVisible(true);
			});

			final int subButtonSize = 40;
			final int subPadding = 5;
			final int subBoxWidth = 3 * (subButtonSize + subPadding) + subPadding;
			subBackground = new HBox(5);
			subBackground.setAlignment(Pos.CENTER_LEFT);
			subBackground.setPrefHeight(subButtonSize + 2 * subPadding);
			subBackground.setPrefWidth(subBoxWidth);
			subBackground.setLayoutX(x + width - (subBoxWidth) + 5);
			subBackground.setLayoutY(y - subButtonSize);
			subBackground.setPadding(new Insets(5));
			subBackground
					.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(25), Insets.EMPTY)));
			subBackground.setVisible(false);
			if (name.equals("Food")) {
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
			}
			if (name.equals("Transport")) {
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
			}
			if (name.equals("Energy")) {
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
			}
			if (name.equals("Habit")) {
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
				addSubCategoryButton("Help", subButtonSize);
			}

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

		private void addSubCategoryButton(String name, int size) {
			IconButton button = new IconButton(name, size, size);
			subBackground.getChildren().add(button.getStackPane());
		}

		private boolean mouseOver = false;

		private Pane background;
		private Text name;
		private ImageView icon;

		private HBox subBackground;

		public void addNodes(AnchorPane anchorPane) {
			anchorPane.getChildren().addAll(background, icon, name, subBackground);
			allNodes.add(background);
			allNodes.add(icon);
			allNodes.add(name);
			allNodes.add(subBackground);
			// TODO add IconButtons
		}

	}

	private enum CategoryButtonCornerType {
		LEFT, CENTER, RIGHT;
	}

}
