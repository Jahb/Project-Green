package nl.tudelft.gogreen.client;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.collections.SetChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
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
class AddActivityButton {

    static final int height = 270;

    private Consumer<String> handler;

    private AnchorPane activityButtonPane;

    private Rectangle animationOverlay;

    private CategoryButton foodButton;
    private CategoryButton transportButton;
    private CategoryButton energyButton;
    private CategoryButton habitButton;

    private TranslateTransition slideUp;
    private TranslateTransition stayPut;
    
    private TextField metadataBox;

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

        
        
        
        Text text = new Text("kcal");
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        text.setMouseTransparent(true);
        
        metadataBox = new TextField();
        metadataBox.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
        metadataBox.setTextFormatter(new TextFormatter<Change>(event -> {
        	Color borderColor;
        	String afterType = event.getControlNewText();
        	if(afterType.equals(""))
        		afterType = "0";
        	try {
        		Float.parseFloat(afterType);
        		if(afterType.contains("e"))
        			throw new NumberFormatException();
        		borderColor = Color.GRAY;
			} catch (NumberFormatException exception) {
				borderColor = Color.RED;
			}
        	metadataBox.setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
        	return event;
        }));
        
        HBox metadataPane = new HBox(metadataBox, text);
        metadataPane.setLayoutX(50);
        metadataPane.setLayoutY(150);
        metadataPane.setAlignment(Pos.CENTER_LEFT);

        animationOverlay = new Rectangle(0, 0, 600.0 * 31 / 32, 200);
        animationOverlay.setMouseTransparent(true);
        animationOverlay.setFill(Color.color(238d / 256, 238d / 256, 238d / 256));

        activityButtonPane = new AnchorPane(backgroundPane, metadataPane);

        activityButtonPane.setPrefWidth((double) (600 * 15 / 16));
        activityButtonPane.setPrefHeight(height);
        activityButtonPane.setLayoutX(500 - 600.0 * 31 / 64);
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
        foodButton.closeDropDown();
        transportButton.closeDropDown();
        energyButton.closeDropDown();
        habitButton.closeDropDown();

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

        CategoryButton(String name, CategoryButtonCornerType type, int index, boolean hasSubcategories) {

            int width = 125;
            int height = 78;
            int x = 10;
            int y = 0;
            if(hasSubcategories) {
                width = 125;
                height = 80;
                x = 40 + index * width;
                y = 35;
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
            this.name.setFont(Font.font("Calibri", hasSubcategories?FontWeight.BOLD:FontWeight.NORMAL, 23));
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
            background.setLayoutX(x);
            background.setLayoutY(y);
            background.setPrefWidth(width - 2);
            background.setPrefHeight(height);



            background.setOnMousePressed(event -> mousePress(true));
            background.setOnMouseReleased(event -> mousePress(false));
            background.setOnMouseEntered(event -> mouseOver(true));
            background.setOnMouseExited(event -> mouseOver(false));

            if(!hasSubcategories)
                return;
            
            background.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                foodButton.closeDropDown();
                transportButton.closeDropDown();
                energyButton.closeDropDown();
                habitButton.closeDropDown();

                openDropDown();
            });

            subCategories = new AnchorPane();
            subCategories.setLayoutX(x-10);
            subCategories.setLayoutY(y);
            subCategories.setPrefHeight(80);
            subCategories.setPrefWidth(145);
            subCategories.setBackground(new Background(
                    new BackgroundFill(new Color(1, 1, 1, .95), new CornerRadii(15, 15, 0, 0, false), Insets.EMPTY)));

            if (name.equals("Food")) {
                addSubCategoryButton("Vegetarian");
                addSubCategoryButton("Local-\nproduce");
//                addSubCategoryButton("Help"); //
            }
            if (name.equals("Transport")) {
                addSubCategoryButton("Bike");
                addSubCategoryButton("Public \ntransport");
//                addSubCategoryButton("Help"); // Carpool
            }
            if (name.equals("Energy")) {
                addSubCategoryButton("Solarpanel");
                addSubCategoryButton("Help"); // CFL
                addSubCategoryButton("Help"); // Lower temp
            }
            if (name.equals("Habit")) {
                addSubCategoryButton("Recycle");
                addSubCategoryButton("No Smoking");
//                addSubCategoryButton("Help");
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
            CategoryButton button = new CategoryButton(name, CategoryButtonCornerType.CENTER, 0, false);
            subCategories.getChildren().add(button.background);
            button.background.toBack();
        }

        void addNodes(AnchorPane anchorPane) {
            anchorPane.getChildren().addAll(subCategories, background);
            allNodes.add(background);
            allNodes.add(icon);
            allNodes.add(name);
            allNodes.add(subCategories);
            for(Node n : subCategories.getChildren())
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
        }

        private void openDropDown() {
            subCategories.setVisible(true);
            startTime = System.nanoTime();
            timer.start();
        }

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
                
                
                for(int i = 0; i < children.size(); i++) {
                    Node n = children.get(i);
                    n.setTranslateY(Math.max(height-(children.size()-i)*80-80, Math.min(height-80, 10)));
                }
                
                if(progress == 1)
                    timer.stop();
            }

            private double fromTo(double from, double to) {
                return (to - from) * progress + from;
            }
        };
    }

    private enum CategoryButtonCornerType {
        LEFT, CENTER, RIGHT
    }
}