package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ListItem {
     String text;
     ImageView image;


    public void setText(String text) {
        this.text = text;
    }

    public void setImage(Image img) {
        this.image.setImage(img);
    }

    public ListItem(final String text, final String imageURL) {
        this.text = text;
        this.image = new ImageView(imageURL);
    }
}
