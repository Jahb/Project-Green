package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainScreenController {

    public ImageView imageView;

    Image MPress = new Image("main/java/resources/ButtonMenuClicked.png");
    Image MRel = new Image("main/java/resources/ButtonMenu.png");

    Image APress = new Image("main/java/resources/ButtonAddClicked.png");
    Image ARel = new Image("main/java/resources/ButtonAdd.png");

    Image HPress = new Image("main/java/resources/ButtonHelpClicked.png");
    Image HRel = new Image("main/java/resources/ButtonHelp.png");


    public void menuPress() {
        System.out.println("Left Button Clicked");
        imageView.setImage(MPress);
    }

    public void menuRelease() {
        System.out.println("Left Button Clicked");
        imageView.setImage(MRel);
    }

    public void addPress() {
        System.out.println("Middle Button Clicked");
        imageView.setImage(APress);
    }

    public void addRelease() {
        System.out.println("Left Button Clicked");
        imageView.setImage(ARel);
    }

    public void helpPress() {
        System.out.println("Right Button Clicked");
        imageView.setImage(HPress);
    }

    public void helpRelease() {
        System.out.println("Left Button Clicked");
        imageView.setImage(HRel);
    }
}
