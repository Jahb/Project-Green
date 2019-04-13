package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.SetChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
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

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * AddActivityButton GUI object.
 *
 * @author Kamron Geijsen
 * @version 1.2.3
 */
class AddActivityButton {

    static final int height = 270;

    private Consumer<String> handler;

    private AnchorPane activityButtonPane;
    private Rectangle animationOverlay;

    private CategoryButton foodButton;
    private CategoryButton transportButton;
    private CategoryButton energyButton;
    private CategoryButton habitButton;

    private Text specifyButton;
    private HBox metadataPane;
    private JFXTextField metadataBox;
    private Text metadataUnit;
    
    private AnchorPane addButton;
    private ImageView addButtonIcon;
    private String toAdd;
    private boolean mouseOverAddButton = false;

    private TranslateTransition slideUp;
    private TranslateTransition stayPut;
    private FadeTransition toAddFade;
    
    private HashSet<Node> allNodes = new HashSet<>(31);

    /**
     * Initialises an AddActivityButton.
     */
    AddActivityButton() {
        Pane backgroundPane = new Pane();
        CornerRadii cr = new CornerRadii(60, 60, 0, 0, false);
        BackgroundFill bf = new BackgroundFill(new Color(1, 1, 1, .95), cr, Insets.EMPTY);
        backgroundPane.setBackground(new Background(bf));

        backgroundPane.setPrefWidth(600.0 * 31 / 32);
        backgroundPane.setPrefHeight(height);
        backgroundPane.setOnMousePressed(event -> {
            foodButton.closeDropDown();
            transportButton.closeDropDown();
            energyButton.closeDropDown();
            habitButton.closeDropDown();
        });
        backgroundPane.setLayoutY(0);

        metadataUnit = new Text("");
        metadataUnit.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        metadataUnit.setMouseTransparent(true);
        metadataBox = new JFXTextField();
        metadataBox.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        metadataBox.setPrefWidth(200);
        metadataBox.setTextFormatter(new TextFormatter<Change<String>>(event -> {
            String afterType = event.getControlNewText();
            if (afterType.equals(""))
                afterType = "0";
            try {
                float test = Float.parseFloat(afterType);
                if (afterType.contains("e") || afterType.contains("d") || afterType.contains("f"))
                    throw new NumberFormatException();
                if (test < 0 || test > 1000)
                    throw new ArrayIndexOutOfBoundsException();
                metadataBox.setStyle("-fx-text-inner-color: black");
            } catch (NumberFormatException exception) {
                metadataBox.setStyle("-fx-text-inner-color: red");
            } catch (ArrayIndexOutOfBoundsException exception) {
                metadataBox.setStyle("-fx-text-inner-color: orange");
            }
            return event;
        }));
        IconButton metadataBackIcon = new IconButton("Back", 50, 50);
        metadataBackIcon.setOnClick(event -> setMetadataVisible(false));
        metadataPane = new HBox(metadataBackIcon.getStackPane(), metadataBox, metadataUnit);
        metadataPane.setSpacing(10);
        metadataPane.setLayoutX(50);
        metadataPane.setLayoutY(130);
        metadataPane.setPrefHeight(60);
        metadataPane.setAlignment(Pos.CENTER_LEFT);
        metadataPane.setVisible(false);

        Text addButtonText = new Text("Add");
        addButtonText.setFill(Color.WHITE);
        addButtonText.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        addButtonText.setX(13);
        addButtonText.setY(30);
        addButtonText.setMouseTransparent(true);
        addButtonIcon = new ImageView("Images/IconEmpty.png");
        addButtonIcon.setFitHeight(40);
        addButtonIcon.setFitWidth(40);
        addButtonIcon.setX(125 - 40);
        addButtonIcon.setY(60 - 40);
        addButtonIcon.setMouseTransparent(true);
        addButton = new AnchorPane(addButtonText, addButtonIcon);
        addButton.setBackground(new Background(new BackgroundFill(
            Color.GRAY, new CornerRadii(0), Insets.EMPTY)));
        addButton.setLayoutX(600.0 * 31 / 32 - 125 - 40);
        addButton.setLayoutY(130);
        addButton.setPrefWidth(125);
        addButton.setPrefHeight(60);
        AnchorPane fadeBackground = new AnchorPane();
        fadeBackground.setBackground(new Background(new BackgroundFill(
            IconButton.color.interpolate(new Color(1, 1, 1, 1), .5), 
            new CornerRadii(0), Insets.EMPTY)));
        fadeBackground.setLayoutX(600.0 * 31 / 32 - 125 - 40);
        fadeBackground.setLayoutY(130);
        fadeBackground.setPrefWidth(125);
        fadeBackground.setPrefHeight(60);
        toAddFade = new FadeTransition(new Duration(200), addButton);
        toAddFade.setToValue(1);
        toAddFade.setFromValue(.5);
        addButton.setOnMouseClicked(event -> {
            if (toAdd == null)
                return;
            handler.accept(toAdd + ":" + metadataBox.getText());
            close();
        });
        addButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> mouseOver(true));
        addButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> mouseOver(false));
        addButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> mousePress(true));

        specifyButton = new Text("Specify");
        specifyButton.setFill(Color.BLACK);
        specifyButton.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        specifyButton.setUnderline(true);
        specifyButton.setX(55);
        specifyButton.setY(165);
        specifyButton.setOnMouseEntered(event -> specifyButton.setFill(Color.GRAY));
        specifyButton.setOnMouseExited(event -> specifyButton.setFill(Color.BLACK));
        specifyButton.setOnMousePressed(event -> setMetadataVisible(true));

        animationOverlay = new Rectangle(0, 0, 600.0 * 31 / 32, 200);
        animationOverlay.setMouseTransparent(true);
        animationOverlay.setFill(Color.color(238d / 256, 238d / 256, 238d / 256));

        activityButtonPane = new AnchorPane(
            backgroundPane, metadataPane, specifyButton, fadeBackground, addButton);

        activityButtonPane.setPrefWidth((double) (600 * 15 / 16));
        activityButtonPane.setPrefHeight(height);
        activityButtonPane.setLayoutX((1000 - 600.0 * 31 / 32) / 2);
        activityButtonPane.setLayoutY(720 - 75);
        activityButtonPane.setVisible(false);

        foodButton = new CategoryButton("Food", CategoryButtonCornerType.LEFT, 0, true);
        transportButton = new CategoryButton("Transport", CategoryButtonCornerType.CENTER, 1, true);
        energyButton = new CategoryButton("Energy", CategoryButtonCornerType.CENTER, 2, true);
        habitButton = new CategoryButton("Habit", CategoryButtonCornerType.RIGHT, 3, true);

        foodButton.addNodes(activityButtonPane);
        transportButton.addNodes(activityButtonPane);
        energyButton.addNodes(activityButtonPane);
        habitButton.addNodes(activityButtonPane);
        foodButton.background.toFront();
        transportButton.background.toFront();
        energyButton.background.toFront();
        habitButton.background.toFront();
        activityButtonPane.getChildren().add(animationOverlay);

        slideUp = new TranslateTransition(Duration.millis(200), activityButtonPane);
        stayPut = new TranslateTransition(Duration.millis(200), animationOverlay);
        slideUp.setByY(-height);
        stayPut.setByY(height);

        allNodes.add(metadataUnit);
        allNodes.add(metadataBox);
        allNodes.add(metadataBackIcon.getStackPane());
        allNodes.add(metadataBackIcon.getStackPane().getChildren().get(0));
        allNodes.add(metadataPane);
        allNodes.add(specifyButton);
        allNodes.add(addButton);
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
    
    public void setX(double value) {
        activityButtonPane.setLayoutX(value);
    }
    
    public void setY(double value) {
        activityButtonPane.setLayoutY(value);
    }

    private void setMetadataVisible(boolean visible) {
        metadataPane.setVisible(visible);
        specifyButton.setVisible(!visible);
        if (!visible)
            metadataBox.clear();
    }

    private void setToAdd(String name, String category, String unit) {
        if (name == null) {
            toAdd = null;
            addButtonIcon.setImage(new Image("Images/IconEmpty.png"));
            metadataUnit.setText("");
            mouseOver(mouseOverAddButton);
            return;
        }

        toAdd = category + ":" + name;
        addButtonIcon.setImage(new Image("Images/Icon" + name.replaceAll("\\-|\n| ", "") + ".png"));
        toAddFade.playFromStart();
        metadataUnit.setText(unit);
        mouseOver(mouseOverAddButton);
    }

    private void mouseOver(boolean mouseOver) {
        mouseOverAddButton = mouseOver;

        if (toAdd == null) {
            setAddButtonFill(Color.GRAY);
            return;
        }

        if (mouseOver)
            setAddButtonFill(IconButton.colorMouseOver);
        else
            setAddButtonFill(IconButton.color);
    }

    private void mousePress(boolean mousePress) {

        if (toAdd == null)
            return;

        if (mousePress)
            setAddButtonFill(IconButton.colorMouseDown);
        else if (mouseOverAddButton)
            setAddButtonFill(IconButton.colorMouseOver);
        else
            setAddButtonFill(IconButton.color);
    }

    private void setAddButtonFill(Color color) {
        CornerRadii cornerRadii = addButton.getBackground().getFills().get(0).getRadii();
        BackgroundFill bf = new BackgroundFill(color, cornerRadii, Insets.EMPTY);
        addButton.setBackground(new Background(bf));
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
        foodButton.closeDropDown();
        transportButton.closeDropDown();
        energyButton.closeDropDown();
        habitButton.closeDropDown();

        setMetadataVisible(false);
        setToAdd(null, null, null);

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

        private AnchorPane subCategories;
        
        private long startTime = 0;
        private AnimationTimer timer = new AnimationTimer() {
            private double progress;

            @Override
            public void handle(long time) {
                progress = (time - startTime) / 200_000_000d;
                progress = Math.min(1, progress);

                List<Node> children = subCategories.getChildren();

                subCategories.setTranslateY(fromTo(0, -80 * children.size() - 10));
                final double height = fromTo(80, 80 * children.size() + 90);
                subCategories.setPrefHeight(height);

                for (int i = 0; i < children.size(); i++) {
                    Node node = children.get(i);
                    node.setTranslateY(Math.max(height - (children.size() - i) * 80 - 80, 
                        Math.min(height - 80, 10)));
                }

                if (progress == 1)
                    timer.stop();
            }

            private double fromTo(double from, double to) {
                return (to - from) * progress + from;
            }
        };

        CategoryButton(String name, CategoryButtonCornerType type, 
            int index, boolean hasSubcategories) {

            int width = 125;
            int height = 78;
            int xoffs = 10;
            int yoffs = 0;
            if (hasSubcategories) {
                width = 125;
                height = 80;
                xoffs = 40 + index * width;
                yoffs = 35;
            }

            icon = new ImageView("images/Icon" + name.replaceAll("\\-|\n| ", "") + ".png");
            icon.setFitWidth(40);
            icon.setFitHeight(40);
            icon.setX(width - 40);
            icon.setY(height - 40);
            icon.setMouseTransparent(true);

            this.name = new Text(name);
            this.name.setX(13);
            this.name.setY(30);
            this.name.setFont(Font.font("Calibri", 
                hasSubcategories ? FontWeight.BOLD : FontWeight.NORMAL, 23));
            this.name.setFill(Color.WHITE);
            this.name.setMouseTransparent(true);

            background = new AnchorPane(icon, this.name);
            CornerRadii cornerRadii = new CornerRadii(0);
            if (type == CategoryButtonCornerType.LEFT)
                cornerRadii = new CornerRadii(20, 0, 0, 0, false);
            if (type == CategoryButtonCornerType.RIGHT)
                cornerRadii = new CornerRadii(0, 20, 0, 0, false);
            BackgroundFill bf = new BackgroundFill(IconButton.color, cornerRadii, Insets.EMPTY);
            background.setBackground(new Background(bf));
            background.setLayoutX(xoffs);
            background.setLayoutY(yoffs);
            background.setPrefWidth(width - 2);
            background.setPrefHeight(height);

            background.setOnMousePressed(event -> mousePress(true));
            background.setOnMouseReleased(event -> mousePress(false));
            background.setOnMouseEntered(event -> mouseOver(true));
            background.setOnMouseExited(event -> mouseOver(false));

            if (!hasSubcategories)
                return;

            background.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                openDropDown();
                background.setMouseTransparent(true);
            });

            subCategories = new AnchorPane();
            subCategories.setLayoutX(xoffs - 10);
            subCategories.setLayoutY(yoffs);
            subCategories.setPrefHeight(80);
            subCategories.setPrefWidth(145);
            subCategories.setBackground(new Background(
                new BackgroundFill(new Color(1, 1, 1, .95), 
                new CornerRadii(15, 15, 0, 0, false), Insets.EMPTY)));
            subCategories.setOnMouseExited(event -> closeDropDown());

            if (name.equals("Food")) {
                addSubCategoryButton("Vegetarian", "kcal");
                addSubCategoryButton("Local-\nproduce", "kcal");
            }
            if (name.equals("Transport")) {
                addSubCategoryButton("Bike", "km");
                addSubCategoryButton("Public \ntransport", "km");
            }
            if (name.equals("Energy")) {
                addSubCategoryButton("Solarpanel", "kW/day");
                addSubCategoryButton("Help", ""); // CFL
                addSubCategoryButton("Help", ""); // Lower temp
            }
            if (name.equals("Habit")) {
                addSubCategoryButton("Recycle", "kg");
                addSubCategoryButton("No Smoking", "amount");
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

        private void addSubCategoryButton(String name, String unit) {
            CategoryButton button = 
                new CategoryButton(name, CategoryButtonCornerType.CENTER, 0, false);
            button.background.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                foodButton.closeDropDown();
                transportButton.closeDropDown();
                energyButton.closeDropDown();
                habitButton.closeDropDown();

                setToAdd(button.name.getText().replaceAll("\n", ""), this.name.getText(), unit);
            });
            subCategories.getChildren().add(button.background);
            button.background.toBack();
        }

        void addNodes(AnchorPane anchorPane) {
            anchorPane.getChildren().addAll(subCategories, background);
            allNodes.add(background);
            allNodes.add(icon);
            allNodes.add(name);
            allNodes.add(subCategories);
            for (Node n : subCategories.getChildren())
                allNodes.add(n);
        }

        private void closeDropDown() {
            subCategories.setVisible(false);
            List<Node> children = subCategories.getChildren();
            for (int i = 0; i < children.size(); i++) {
                children.get(0).setTranslateY(0);
            }
            subCategories.setTranslateY(0);
            subCategories.setPrefHeight(80);
            background.setMouseTransparent(false);
        }

        private void openDropDown() {
            if (this != foodButton)
                foodButton.closeDropDown();
            if (this != transportButton)
                transportButton.closeDropDown();
            if (this != energyButton)
                energyButton.closeDropDown();
            if (this != habitButton)
                habitButton.closeDropDown();
            if (this.subCategories.isVisible())
                return;
            mouseOver(true);

            subCategories.setVisible(true);
            startTime = System.nanoTime();
            timer.start();
        }
    }

    private enum CategoryButtonCornerType {
        LEFT, CENTER, RIGHT
    }
}