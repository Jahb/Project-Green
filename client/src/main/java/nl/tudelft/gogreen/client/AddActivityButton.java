package nl.tudelft.gogreen.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.function.Consumer;


/**
 * AddActivityButton GUI object.
 *
 * @author Kamron Geijsen
 * @version 1.2.3
 */
class AddActivityButton {

    private Consumer<String> handler;

    private AnchorPane activityButtonPane;

    private Rectangle animationOverlay;

    private CategoryButton foodButton;
    private CategoryButton transportButton;
    private CategoryButton energyButton;
    private CategoryButton habitButton;

    private TranslateTransition slideUp;
    private TranslateTransition stayPut;

    private HashSet<Node> allNodes = new HashSet<>(31);

    /**
     * Initialises an AddActivityButton.
     */
    AddActivityButton() {
        Pane backgroundPane = new Pane();
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
        backgroundPane.setLayoutY(0);

        Text text = new Text("Record new GREEN activity");
        text.setX(50);
        text.setY(35);
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        text.setMouseTransparent(true);
        text.setVisible(false);// TODO

        animationOverlay = new Rectangle(0, 0, 600.0 * 31 / 32, 200);
        animationOverlay.setMouseTransparent(true);
        animationOverlay.setFill(Color.color(238d / 256, 238d / 256, 238d / 256));

        activityButtonPane = new AnchorPane(backgroundPane, text);

        activityButtonPane.setPrefWidth((double)(600 * 15 / 16));
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

    boolean contains(Node node) {
        return allNodes.contains(node);
    }

    void setHandler(Consumer<String> handler) {
        this.handler = handler;
    }

    Pane getPane() {
        return activityButtonPane;
    }

    /**
     * Starts the open animation of the AddActivityButton.
     */
    void open() {
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
    void close() {
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

        CategoryButton(String name, CategoryButtonCornerType type, int index) {

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

            icon = new ImageView("images/Icon" + name + ".png");
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
                addSubCategoryButton("Bike");
                addSubCategoryButton("Publictransport");
                addSubCategoryButton("Help");
            }
            if (name.equals("Energy")) {
                addSubCategoryButton("Solarpanel");
                addSubCategoryButton("Help");
                addSubCategoryButton("Help");
            }
            if (name.equals("Habit")) {
                addSubCategoryButton("Recycle");
                addSubCategoryButton("NoSmoking");
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
            Tooltip tooltip = new Tooltip(name);
            hackTooltipStartTiming(tooltip);
            tooltip.setStyle("-fx-background-color:#52EA7F; -fx-font-weight:bold; -fx-text-color:white;" +
                    "-fx-font-size:20");
            Tooltip.install(button.getStackPane(),tooltip);
        }

        void addNodes(AnchorPane anchorPane) {
            anchorPane.getChildren().addAll(background, icon, name, subBackground);
            allNodes.add(background);
            allNodes.add(icon);
            allNodes.add(name);
            allNodes.add(subBackground);
        }

    }

    private enum CategoryButtonCornerType {
        LEFT, CENTER, RIGHT
    }

    /**
     *  this makes it so the tooltips show instantly
     * @param tooltip
     */
    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
