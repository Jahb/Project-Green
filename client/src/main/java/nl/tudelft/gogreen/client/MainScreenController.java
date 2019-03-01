package nl.tudelft.gogreen.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainScreenController {

    @FXML
    public ImageView addButton;
    @FXML
    public ImageView menuButton;
    @FXML
    public ImageView helpButton;
    @FXML
    public TextArea helpText;

    private Image mPress = new Image("ButtonMenuClicked.png");
    private Image mRel = new Image("ButtonMenu.png");

    private Image aPress = new Image("ButtonAddClicked.png");
    private Image aRel = new Image("ButtonAdd.png");

    private Image hPress = new Image("ButtonHelpClicked.png");
    private Image hRel = new Image("ButtonHelp.png");


    public void menuPress() {
        System.out.println("Left Button Pressed");
        menuButton.setImage(mPress);
    }

    public void menuRelease() {
        System.out.println("Left Button Released");
        menuButton.setImage(mRel);
    }

    public void addPress() {
        System.out.println("Add Button Pressed");
        addButton.setImage(aPress);
    }

    public void addRelease() {
        System.out.println("Add Button Released");
        addButton.setImage(aRel);
    }

    public void helpPress() {
        System.out.println("Help Button Pressed");
        helpButton.setImage(hPress);
    }

    public void helpRelease() {
        System.out.println("Help Button Released");

        if (helpText.isVisible()) {
            helpText.setVisible(false);
        } else {
            helpText.setVisible(true);
        }
        helpButton.setImage(hRel);
    }
}
