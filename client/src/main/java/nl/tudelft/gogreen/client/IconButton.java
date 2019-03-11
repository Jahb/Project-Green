package nl.tudelft.gogreen.client;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class IconButton extends ImageView {
    static Color color = new Color(.3, .9, .5, 1);
    static Color colorMouseOver = new Color(.32, .92, .52, 1);
    static Color colorMouseDown = new Color(.25, .90, .45, 1);

    private String name;
    private StackPane layoutBox;
    private Rectangle clickBox;
    private boolean mouseOver;

    IconButton(String name, int width, int height) {
        this.name = name;


        clickBox = new Rectangle();
        final double min = Math.min(width, height);
        final double padding = min / 16;
        clickBox.setWidth(width - padding * 2);
        clickBox.setHeight(height - padding * 2);
        clickBox.setArcHeight(min - padding * 2);
        clickBox.setArcWidth(min - padding * 2);
        clickBox.setFill(color);
        clickBox.setId(name);

        ImageView icon = new ImageView("Icon" + name + ".png");
        icon.setFitWidth(min);
        icon.setFitHeight(min);
        icon.setMouseTransparent(true);

        clickBox.setOnMousePressed(event -> mousePress(true));
        clickBox.setOnMouseReleased(event -> mousePress(false));
        clickBox.setOnMouseEntered(event -> mouseOver(true));
        clickBox.setOnMouseExited(event -> mouseOver(false));

        layoutBox = new StackPane(clickBox, icon);
        layoutBox.setPickOnBounds(false);
        layoutBox.setPrefSize(width, height);
    }

    private void mouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
        if (mouseOver)
            clickBox.setFill(colorMouseOver);
        else
            clickBox.setFill(color);
    }

    private void mousePress(boolean mousePress) {
        if (mousePress)
            clickBox.setFill(colorMouseDown);
        else if (mouseOver)
            clickBox.setFill(colorMouseOver);
        else
            clickBox.setFill(color);
    }

    public String getName() {
        return name;
    }

    /**
     * setX is used when the button is added to an AnchorPane.
     *
     * @param x x being x-coordinate of the top-left corner of the layoutBox.
     */
    public void setX(int x) {
        layoutBox.setLayoutX(x);
    }

    /**
     * setY is used when the button is added to an AnchorPane.
     *
     * @param y y being y-coordinate of the top-left corner of the layoutBox.
     */
    public void setY(int y) {
        layoutBox.setLayoutY(y);
    }

    /**
     * getStackPane is used when the button is added to any non-coordinate pane
     * It will stack the button elements (layoutBox, buttonBox and icon) correctly
     * in such a way that you only need to add the all containing StackPane.
     */
    public StackPane getStackPane() {
        return layoutBox;
    }

    /**
     * Sets the handler as for what the button needs to execute when it is pressed.
     *
     * @param handler This is passed on to the event handler. This is usually a Lambda expression.
     */
    void setOnClick(EventHandler<MouseEvent> handler) {
        clickBox.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
    }

    /**
     * Creates back button on the left side of a BorderPane
     *
     * @param root
     */
    protected static void addBackButton(BorderPane root) {
        IconButton backButton = new IconButton("Back", 100, 100);
        root.setLeft(backButton.getStackPane());
        backButton.setOnClick(event -> {
            Main.openMainScreen();
        });
    }
}
