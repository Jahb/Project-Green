package nl.tudelft.gogreen.client;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    public ImageView addButton;
    @FXML
    public ImageView menuButton;
    @FXML
    public ImageView helpButton;
    @FXML
    public TextArea helpText;
    @FXML
    public AnchorPane rootPane;

    private Image menuPress = new Image("ButtonMenuClicked.png");
    private Image menuRel = new Image("ButtonMenu.png");

    private Image addPress = new Image("ButtonAddClicked.png");
    private Image addRel = new Image("ButtonAdd.png");

    private Image helpPress = new Image("ButtonHelpClicked.png");
    private Image helpRel = new Image("ButtonHelp.png");

    public void initialize(){
        //Insert Ring Code Here!
        
    }

    public void menuPress() {
        System.out.println("Left Button Pressed");
        menuButton.setImage(menuPress);
    }

    public void menuRelease() throws Exception {
        System.out.println("Left Button Released");
        menuButton.setImage(menuRel);
        FadeOut("/LeaderboardGUI.fxml");
    }

    public void addPress() {
        System.out.println("Add Button Pressed");
        addButton.setImage(addPress);
    }

    public void addRelease() throws Exception {
        System.out.println("Add Button Released");
        addButton.setImage(addRel);
        FadeOut("/HabitsScreen.fxml");
    }

    public void helpPress() {
        System.out.println("Help Button Pressed");
        helpButton.setImage(helpPress);
    }

    /**
     * Method called on release of Help button.
     */
    public void helpRelease() {
        System.out.println("Help Button Released");

        if (helpText.isVisible()) {
            helpText.setVisible(false);
            helpText.toFront();
        } else {
            helpText.setVisible(true);
            helpText.toFront();
        }
        helpButton.setImage(helpRel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void FadeOut(String location) {
        FadeTransition fadetransition = new FadeTransition();
        fadetransition.setDuration(Duration.millis(150));
        fadetransition.setNode(rootPane);
        fadetransition.setFromValue(1);
        fadetransition.setToValue(0);
        fadetransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadScene(location);
            }
        });
        fadetransition.play();
    }

    private void loadScene(String location) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        try {
            Parent root1 = (AnchorPane) FXMLLoader.load(getClass().getResource(location));
            stage.setScene(new Scene(root1, 1200, 700));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
