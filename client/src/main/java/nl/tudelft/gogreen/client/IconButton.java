package nl.tudelft.gogreen.client;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class IconButton {
	static Color color = new Color(.3, .9, .5, 1);
	static Color colorMouseOver = new Color(.35, .95, .55, 1);
	static Color colorMouseDown = new Color(.25, .85, .45, 1);

	public IconButton(String name, Rectangle layoutBox) {
		this.name = name;
		this.layoutBox = layoutBox;
		clickBox = new Rectangle();
		double min = Math.min(layoutBox.getWidth(), layoutBox.getHeight());
		double padding = min / 32;
		clickBox.setWidth(layoutBox.getWidth() - padding * 2);
		clickBox.setHeight(layoutBox.getHeight() - padding * 2);
		clickBox.setX(layoutBox.getX() + padding);
		clickBox.setY(layoutBox.getY() + padding);
		clickBox.setArcHeight(min - padding * 2);
		clickBox.setArcWidth(min - padding * 2);
		clickBox.setFill(color);

		icon = new ImageView("Icon" + name + ".png");
		icon.setFitWidth(min);
		icon.setFitHeight(min);
		icon.setX(layoutBox.getX() + (layoutBox.getWidth() - min) / 2);
		icon.setY(layoutBox.getY() + (layoutBox.getHeight() - min) / 2);
		icon.setMouseTransparent(true);

		addTransitions();
	}

	void addNodes(Pane root) {
		root.getChildren().add(clickBox);
		root.getChildren().add(icon);
	}

	void setX(int x) {
		double min = Math.min(layoutBox.getWidth(), layoutBox.getHeight());
		double padding = min / 32;

		layoutBox.setX(x);
		clickBox.setX(layoutBox.getX() + padding);
		icon.setX(layoutBox.getX() + (layoutBox.getWidth() - min) / 2);
	}

	void setY(int y) {
		double min = Math.min(layoutBox.getWidth(), layoutBox.getHeight());
		double padding = min / 32;

		layoutBox.setY(y);
		clickBox.setY(layoutBox.getY() + padding);
		icon.setY(layoutBox.getY() + (layoutBox.getHeight() - min) / 2);
	}

	StackPane getStackPane() {
		return new StackPane(layoutBox, clickBox, icon);
	}
	
	protected String name;
	private Rectangle layoutBox;
	private Rectangle clickBox;
	private ImageView icon;
	
	
	private void addTransitions() {
		clickBox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> clickBox.setFill(colorMouseDown));
		clickBox.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> clickBox.setFill(colorMouseOver));
		clickBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> clickBox.setFill(colorMouseOver));
		clickBox.addEventHandler(MouseEvent.MOUSE_EXITED, event -> clickBox.setFill(color));
	}
}
