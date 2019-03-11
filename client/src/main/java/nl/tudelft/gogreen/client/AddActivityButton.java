package nl.tudelft.gogreen.client;

import java.util.HashSet;
import java.util.function.Consumer;

import javafx.animation.TranslateTransition;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * AddActivityButton GUI object.
 * 
 * @author Kamron Geijsen
 * @version 1.2.3
 */
public class AddActivityButton {

    private Consumer<String> handler;

    private AnchorPane activityButtonPane;
    private Pane backgroundPane;
    private Text text;

    private Rectangle animationOverlay;

    private CategoryButton foodButton;
    private CategoryButton transportButton;
    private CategoryButton energyButton;
    private CategoryButton habitButton;

    private TranslateTransition slideUp;
    private TranslateTransition stayPut;

    private HashSet<Node> allNodes = new HashSet<Node>(31);

    /**
     * Initialises an AddActivityButton.
     */
    public AddActivityButton() {
        backgroundPane = new Pane();
        CornerRadii cr = new CornerRadii(60, 60, 0, 0, false);
        BackgroundFill bf = new BackgroundFill(new Color(1, 1, 1, .8), cr, Insets.EMPTY);
        backgroundPane.setBackground(new Background(bf));

        backgroundPane.setPrefWidth(600.0 * 31 / 32);
        backgroundPane.setPrefHeight(200);
        backgroundPane.setOnMousePressed(event -> {
            foodButton.subBackground.setVisible(false);
            transportButton.subBackground.setVisible(false);
            energyButton.subBackground.setVisible(false);
            habitButton.subBackground.setVisible(false);
        });
        backgroundPane.setLayoutY(00);

        text = new Text("Record new GREEN activity");
        text.setX(50);
        text.setY(35);
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        text.setMouseTransparent(true);
        text.setVisible(false);// TODO

        animationOverlay = new Rectangle(0, 0, 600.0 * 31 / 32, 200);
        animationOverlay.setMouseTransparent(true);
        animationOverlay.setFill(Color.color(238d / 256, 238d / 256, 238d / 256));

        activityButtonPane = new AnchorPane(backgroundPane, text);

        activityButtonPane.setPrefWidth(600 * 15 / 16);
        activityButtonPane.setPrefWidth(200);
        activityButtonPane.setLayoutX(500 - 600.0 * 31 / 64);
        activityButtonPane.setLayoutY(720 - 75);
        activityButtonPane.setVisible(false);

        foodButton = new CategoryButton("Food", CategoryButtonCornerType.LEFT, 0);
        transportButton = new CategoryButton("Transport", CategoryButtonCornerType.CENTER, 1);
        energyButton = new CategoryButton("Energy", CategoryButtonCornerType.CENTER, 2);
        habitButton = new CategoryButton("Habit", CategoryButtonCornerType.RIGHT, 3);

        foodButton.addNodes(activityButtonPane);
        transportButton.addNodes(activityButtonPane);
        energyButton.addNodes(activityButtonPane);
        habitButton.addNodes(activityButtonPane);
        activityButtonPane.getChildren().add(animationOverlay);

        slideUp = new TranslateTransition(Duration.millis(200), activityButtonPane);
        stayPut = new TranslateTransition(Duration.millis(200), animationOverlay);
        slideUp.setByY(-200);
        stayPut.setByY(200);

        allNodes.add(activityButtonPane);
        allNodes.add(text);
        allNodes.add(backgroundPane);
        allNodes.add(animationOverlay);
    }

    public boolean contains(Node node) {
        return allNodes.contains(node);
    }

    public void setHandler(Consumer<String> handler) {
        this.handler = handler;
    }

    public Pane getPane() {
        return activityButtonPane;
    }

    /**
     * Starts the open animation of the AddActivityButton.
     */
    public void open() {
        if (activityButtonPane.isVisible()) {
            close();
            return;
        }
        activityButtonPane.setVisible(true);
        foodButton.subBackground.setVisible(false);
        transportButton.subBackground.setVisible(false);
        energyButton.subBackground.setVisible(false);
        habitButton.subBackground.setVisible(false);

        activityButtonPane.setTranslateY(0);
        animationOverlay.setTranslateY(0);
        animationOverlay.setVisible(true);
        slideUp.playFromStart();
        stayPut.playFromStart();
        stayPut.setOnFinished(event -> animationOverlay.setVisible(false));
    }

    /**
     * Hides the AddActivityButton.
     */
    public void close() {
        activityButtonPane.setVisible(false);
        activityButtonPane.setTranslateY(0);
        animationOverlay.setTranslateY(0);
    }

    private class CategoryButton {

        private boolean mouseOver = false;

        private Pane background;
        private Text name;
        private ImageView icon;

        private HBox subBackground;

        protected CategoryButton(String name, CategoryButtonCornerType type, int index) {

            final int width = 125;
            final int height = 80;
            final int x = 40 + index * width;
            final int y = 35;

            background = new Pane();
            CornerRadii cornerRadii = new CornerRadii(0);
            if (type == CategoryButtonCornerType.LEFT)
                cornerRadii = new CornerRadii(20, 0, 0, 0, false);
            if (type == CategoryButtonCornerType.RIGHT)
                cornerRadii = new CornerRadii(0, 20, 0, 0, false);
            BackgroundFill bf = new BackgroundFill(IconButton.color, cornerRadii, Insets.EMPTY);
            background.setBackground(new Background(bf));
            background.setLayoutX(x);
            background.setLayoutY(y);
            background.setPrefWidth(width - 2);
            background.setPrefHeight(height);

            icon = new ImageView("Icon" + name + ".png");
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
            subBackground.setLayoutX(x + width - subBoxWidth + 5);
            subBackground.setLayoutY(y - subButtonSize);
            subBackground.setPadding(new Insets(5));
            BackgroundFill bf2 = new BackgroundFill(Color.WHITE, new CornerRadii(25), Insets.EMPTY);
            subBackground.setBackground(new Background(bf2));
            subBackground.setVisible(false);
            if (name.equals("Food")) {
                addSubCategoryButton("Vegetarian");
                addSubCategoryButton("Localproduce");
                addSubCategoryButton("Help");
            }
            if (name.equals("Transport")) {
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
            }
            if (name.equals("Energy")) {
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
            }
            if (name.equals("Habit")) {
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
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
            BackgroundFill bf = new BackgroundFill(color, cornerRadii, Insets.EMPTY);
            background.setBackground(new Background(bf));
        }

        private void addSubCategoryButton(String name) {
            IconButton button = new IconButton(name, 40, 40);
            button.setOnClick(event -> handler.accept(name));

            allNodes.add(button.getStackPane());
            allNodes.add(button.getStackPane().getChildren().get(0));
            allNodes.add(button.getStackPane().getChildren().get(1));
            subBackground.getChildren().add(button.getStackPane());
        }

        public void addNodes(AnchorPane anchorPane) {
            anchorPane.getChildren().addAll(background, icon, name, subBackground);
            allNodes.add(background);
            allNodes.add(icon);
            allNodes.add(name);
            allNodes.add(subBackground);
        }

    }

    private enum CategoryButtonCornerType {
        LEFT, CENTER, RIGHT;
    }

}