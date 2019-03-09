package nl.tudelft.gogreen.client;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class IconButton extends ImageView {
    static Color color = new Color(.3, .9, .5, 1);
    static Color colorMouseOver = new Color(.32, .92, .52, 1);
    static Color colorMouseDown = new Color(.25, .90, .45, 1);

    public IconButton(String name, int width, int height) {
        this.name = name;

        layoutBox = new Rectangle(width, height);
        layoutBox.setFill(new Color(0, 0, 0, 0));
        layoutBox.setMouseTransparent(true);
        clickBox = new Rectangle();
        double min = Math.min(layoutBox.getWidth(), layoutBox.getHeight());
        double padding = min / 16;
        clickBox.setWidth(layoutBox.getWidth() - padding * 2);
        clickBox.setHeight(layoutBox.getHeight() - padding * 2);
        clickBox.setArcHeight(min - padding * 2);
        clickBox.setArcWidth(min - padding * 2);
        clickBox.setFill(color);
        clickBox.setId(name);

        icon = new ImageView("Icon" + name + ".png");
        icon.setFitWidth(min);
        icon.setFitHeight(min);
        icon.setMouseTransparent(true);

        clickBox.setOnMousePressed(event -> mousePress(true));
        clickBox.setOnMouseReleased(event -> mousePress(false));
        clickBox.setOnMouseEntered(event -> mouseOver(true));
        clickBox.setOnMouseExited(event -> mouseOver(false));
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

    protected String name;
    private Rectangle layoutBox;
    private Rectangle clickBox;
    private ImageView icon;
    private boolean mouseOver;


    /**
     * setX is used when the button is added to an AnchorPane.
     *
     * @param x x being x-coordinate of the top-left corner of the layoutBox.
     */
    public void setX(int x) {
        double min = Math.min(layoutBox.getWidth(), layoutBox.getHeight());
        double padding = min / 32;

        layoutBox.setX(x);
        clickBox.setX(layoutBox.getX() + padding);
        icon.setX(layoutBox.getX() + (layoutBox.getWidth() - min) / 2);
    }

    /**
     * setY is used when the button is added to an AnchorPane.
     *
     * @param y y being y-coordinate of the top-left corner of the layoutBox.
     */
    public void setY(int y) {
        double min = Math.min(layoutBox.getWidth(), layoutBox.getHeight());
        double padding = min / 32;

        layoutBox.setY(y);
        clickBox.setY(layoutBox.getY() + padding);
        icon.setY(layoutBox.getY() + (layoutBox.getHeight() - min) / 2);
    }

    /**
     * getStackPane is used when the button is added to any non-coordinate pane
     * It will stack the button elements (layoutBox, buttonBox and icon) correctly
     * in such a way that you only need to add the all containing StackPane.
     */
    public StackPane getStackPane() {
        StackPane ret = new StackPane(layoutBox, clickBox, icon);
        return ret;
    }

    /**
     * Sets the handler as for what the button needs to execute when it is pressed
     *
     * @param handler This is passed on to the event handler. This is usually a Lambda expression.
     */
    public void setOnClick(EventHandler<MouseEvent> handler) {
        clickBox.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
    }

}
