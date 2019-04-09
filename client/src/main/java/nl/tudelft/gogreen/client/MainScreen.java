package nl.tudelft.gogreen.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.gogreen.client.communication.Api;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;


/**
 * MainScreen object.
 *
 * @author Kamron Geijsen
 * @version 4.20.21
 */
public class MainScreen implements Initializable{

    public static String[] strings = new String[7];
    @FXML
    public HBox topLeftButtons;
    @FXML
    public JFXDrawer notificationBox= new JFXDrawer();
    @FXML
    public AnchorPane notificationPane;
    @FXML
    public JFXButton searchButton;
    @FXML
    public JFXTextField searchField;
    @FXML
    public VBox container;
    private Scene scene;
    private Ring ringMain;
    private Ring ringPrevious;
    private Ring ringNext;
    private TextArea helpText;
    private AddActivityButton activityButton;
    private AnchorPane root;
    // TODO handler for each subcategory
    private Consumer<String> handler = name -> {
        try {
            //Line below this one used to be res = API...... removed for checkStyle
            Api.current.addFeature(name);
            System.out.println(name);
            updateRingValues();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    };
    //TODO handler for each ring category
    private Consumer<String> ringHandler = name -> System.out.println("EXE [" + name + "]");

    /**
     * Creates a scene for MainScreen.
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/MainScreen.fxml");
        System.out.println(url);
        root = FXMLLoader.load(url);
        scene = new Scene(root, Main.getWidth(), Main.getHeight());

        BorderPane baseLayer = (BorderPane) root.getChildren().get(0);
        AnchorPane mainRingPane = (AnchorPane) baseLayer.getCenter();
        BorderPane topPane = (BorderPane) baseLayer.getTop();
        HBox topRightButtons = (HBox) topPane.getRight();

        AnchorPane overlayLayer = (AnchorPane) root.getChildren().get(2);
        helpText = (TextArea) overlayLayer.getChildren().get(0);
        BorderPane buttonsPanel = (BorderPane) overlayLayer.getChildren().get(1);

        addRings(mainRingPane);
        addTopMenuButtons(topRightButtons);
        addIconButtons(buttonsPanel);
        addActivityButton(overlayLayer);


        helpText.setVisible(false);
        overlayLayer.setPrefSize(1000, 720);

        overlayLayer.setPickOnBounds(false);
        buttonsPanel.setPickOnBounds(false);

        //Streaks
        //TODO Implement Streaks. condition to check if first login today.
        if (true) {
            setUpStreak();
        }


        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node != null && !activityButton.contains(node))
                if (!(node.getId() + "").equals("Add"))
                    activityButton.close();
        });
        return scene;
    }

    /**
     * toggles between showing and hiding dropdown menu
     */
    private static void toggleNotifications(JFXDrawer notificationBox){
        if(!notificationBox.isShown()){
            notificationBox.open();
            notificationBox.setMouseTransparent(false);
        }
        else{
            notificationBox.close();
            notificationBox.setMouseTransparent(true);
        }
    }


    private void addRings(AnchorPane anchorPane) {
        ringMain = new Ring((int) (150 * .75), 150, Main.getWidth() / 2, 200, "MAIN");
        ringMain.setHandler(ringHandler);
        ringMain.setUsername(Api.current.getUsername());
        anchorPane.getChildren().add(ringMain.getPane());

        ringNext = new Ring((int) (90 * .75), 90, 120, 350, "NEXT");
        ringNext.setHandler(ringHandler);
        ringNext.setUsername(Api.current.getUsernameNext());
        anchorPane.getChildren().add(ringNext.getPane());

        ringPrevious = new Ring((int) (90 * .75), 90, Main.getWidth() - 120, 350, "PREVIOUS");
        ringPrevious.setHandler(ringHandler);
        ringPrevious.setUsername(Api.current.getUsernamePrevious());
        anchorPane.getChildren().add(ringPrevious.getPane());

        scene.widthProperty()
                .addListener((obs, oldVal, newVal) -> {
                    ringMain.setX(newVal.intValue() / 2);
                    ringNext.setX(120);
                    ringPrevious.setX(newVal.intValue() - 120);
                });

        updateRingValues();

    }

    private void updateRingValues() {
        new Thread(() -> {
            double[] valuesMain = Api.current.getRingSegmentValues(ringMain.getName());
            ringMain.setUsername(Api.current.getUsername());
            ringMain.setSegmentValues(valuesMain);
            Platform.runLater(() -> ringMain.startAnimation());
        }).start();

        new Thread(() -> {
            double[] valuesNext = Api.current.getRingSegmentValues(ringNext.getName());
            ringNext.setUsername(Api.current.getUsernameNext());
            ringNext.setSegmentValues(valuesNext);
            Platform.runLater(() -> ringNext.startAnimation());
        }).start();

        new Thread(() -> {
            double[] valuesPrevious = Api.current.getRingSegmentValues(ringPrevious.getName());
            ringPrevious.setUsername(Api.current.getUsernamePrevious());
            ringPrevious.setSegmentValues(valuesPrevious);
            Platform.runLater(() -> ringPrevious.startAnimation());
        }).start();


    }

    private void addActivityButton(AnchorPane anchorPane) {
        activityButton = new AddActivityButton();
        activityButton.setHandler(handler);
        anchorPane.getChildren().add(0, activityButton.getPane());
    }

    private void addIconButtons(BorderPane root) {
        IconButton leaderboardButton = new IconButton("Leaderboard", 150, 150);
        IconButton addButton = new IconButton("Add", 600, 150);
        IconButton eventButton = new IconButton("Event", 150, 150);


        root.setLeft(leaderboardButton.getStackPane());
        root.setCenter(addButton.getStackPane());
        root.setRight(eventButton.getStackPane());

        eventButton.setOnClick(event -> Main.openEventScreen());

        addButton.setOnClick(event -> activityButton.open());
        leaderboardButton.setOnClick(event -> Main.openLeaderboardScreen());
    }

    /**
     * Method which adds button to HBox on TopRight of MainScreen.
     *
     * @param rightHbox A Hbox Container.
     */
    private void addTopMenuButtons(HBox rightHbox) {
        IconButton helpButton = new IconButton("Help", 70, 70);
        BorderPane root = (BorderPane) rightHbox.getChildren().get(0);
        root.setCenter(helpButton.getStackPane());
        helpButton.setOnClick(event -> helpText.setVisible(!helpText.isVisible()));

        IconButton profileButton = new IconButton("Profile", 70, 70);
        BorderPane root2 = new BorderPane();
        root2.setCenter(profileButton.getStackPane());
        rightHbox.getChildren().add(root2);
        profileButton.setOnClick(event -> Main.openProfileScreen());


    }

    /**
     * add labels to the vbox
     */
    public VBox setLabels() {
        VBox labelVBox = new VBox();
        Label title= new Label(" Recent notifications:");
        title.setMinWidth(350);
        title.setMinHeight(40);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setStyle("-fx-font-weight: bold; -fx-background-color: #50e476; -fx-font-size: 20; -fx-text-fill: white");
        labelVBox.getChildren().add(title);
        for (String text : strings) {
            if (text != null) {
                Label label = new Label(text);
                label.setMinWidth(350);
                label.setMinHeight(30);
                label.setStyle("-fx-border-radius: 1; -fx-border-color: gray; -fx-background-color: white;" +
                        " -fx-font-weight: bold; -fx-font-size:16");
                labelVBox.getChildren().add(label);
            }
        }
        return labelVBox;
    }
    /**
     * shows notifications
     */
    public void initialize(URL location, ResourceBundle resources){
        IconButton notificationButton = new IconButton("Bell", 70, 70);
        topLeftButtons.getChildren().add(notificationButton.getStackPane());
        notificationButton.setOnClick(event -> {if(notificationBox.isHidden())notificationBox.setSidePane(setLabels());
        toggleNotifications(notificationBox);});
        //notificationBox.setSidePane(setLabels());
        /*
         * testing notifications
         */
        Main.showMessage(notificationPane, "You have opened the main screen");
        /*
         * String array with all usernames TODO retrieve usernames from database to string options
         */
        String[] options = {"user1", "asdf", "aaa", "wovuwe", "brrrr", "name", "sample", "sample223", "naaaaaaaaaaame", "namenamename", "username"};
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(container.getChildren().size()>1){
                container.getChildren().remove(1);
            }
            container.getChildren().add(populateDropDownMenu(newValue, options, searchField));
        });

    }

    /**
     * Searches for text in an array of strings and returns the matches in a VBox
     * @param text text currently in the search bar
     * @param options array of all potential suggestions
     * @param search the search field itself
     * @return returns suggestions box
     */
    private static VBox populateDropDownMenu(String text, String[] options, JFXTextField search){
        VBox dropDownMenu = new VBox();
        dropDownMenu.setStyle("-fx-background-color: white");
        dropDownMenu.setAlignment(Pos.CENTER);

        for(String option : options){
            // loop through every String in the array
            String substring;
            if(!option.equals(text)){
            substring = option.substring(0, Math.min(text.length(), option.length()));}
            else substring = text;
            if(!text.replace(" ", "").isEmpty() && substring.toUpperCase().equals(text.toUpperCase())){
                Label label = new Label(option);
                label.setMinWidth(330);
                label.setStyle("-fx-border-radius: 1; -fx-border-color: gray;");
                label.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event) {
                            search.setText(label.getText());
                        }
                    }
                );
                dropDownMenu.getChildren().add(label); //adds suggestion to VBox
            }
        }
        return dropDownMenu;
    }

    private void setUpStreak() {
        //TODO Get Streak Days
        int streakDays = 6;
        AnchorPane streakPane = (AnchorPane) root.getChildren().get(3);
        AnchorPane buttonPane = (AnchorPane) streakPane.getChildren().get(0);
        Text numDays = (Text) streakPane.getChildren().get(2);
        JFXButton reward = (JFXButton) buttonPane.getChildren().get(0);
        ImageView streakImg = (ImageView) streakPane.getChildren().get(4);

        streakPane.setVisible(true);
        numDays.setText(streakDays + " Days!");
        reward.setOnAction(event -> streakPane.setVisible(false));

        switch (streakDays) {
            case 0:
                reward.setText("Today is your first day, come back tomorrow!");
                break;
            case 1:
                reward.setText("Congratulations! You earned an extra 10 Points!");
                break;
            case 2:
                reward.setText("Congratulations! You earned an extra 15 Points!");
                break;
            case 3:
                reward.setText("Congratulations! You earned an extra 25 Points!");
                streakImg.setImage(new Image("/images/IconCupSilver.png"));
                break;
            case 4:
                reward.setText("Congratulations! You Earned An Extra 40 Points!");
                streakImg.setImage(new Image("/images/IconCupSilver.png"));
                break;
            case 5:
                reward.setText("Congratulations! You earned an extra 60 Points!");
                streakImg.setImage(new Image("/images/IconCupSilver.png"));
                break;
            case 6:
                reward.setText("Congratulations! You earned an extra 80 Points!");
                streakImg.setImage(new Image("/images/IconCupGold.png"));
                break;
            default:
                reward.setText("Congratulations! You earned an extra 100 Points!");
                streakImg.setImage(new Image("IconCupGold.png"));
                break;
        }

    }

}
