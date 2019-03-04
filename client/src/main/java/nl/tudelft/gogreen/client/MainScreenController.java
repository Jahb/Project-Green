package nl.tudelft.gogreen.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainScreenController {

    @FXML
    public ImageView addButton;
    @FXML
    public ImageView menuButton;
    @FXML
    public ImageView helpButton;
    @FXML
    public TextArea helpText;

    private Image menuPress = new Image("ButtonMenuClicked.png");
    private Image menuRel = new Image("ButtonMenu.png");

    private Image addPress = new Image("ButtonAddClicked.png");
    private Image addRel = new Image("ButtonAdd.png");

    private Image helpPress = new Image("ButtonHelpClicked.png");
    private Image helpRel = new Image("ButtonHelp.png");

    public void menuPress() {
        System.out.println("Left Button Pressed");
        menuButton.setImage(menuPress);
    }

    public void menuRelease() {
        System.out.println("Left Button Released");
        menuButton.setImage(menuRel);
    }

    public void addPress() {
        System.out.println("Add Button Pressed");
        addButton.setImage(addPress);
    }

    public void addRelease() {
        System.out.println("Add Button Released");
        addButton.setImage(addRel);

    }

    public void helpPress() {
        System.out.println("Help Button Pressed");
        helpButton.setImage(helpPress);
    }

    public void helpRelease() {
        System.out.println("Help Button Released");

        if (helpText.isVisible()) {
            helpText.setVisible(false);
        } else {
            helpText.setVisible(true);
        }
        helpButton.setImage(helpRel);
    }

}
