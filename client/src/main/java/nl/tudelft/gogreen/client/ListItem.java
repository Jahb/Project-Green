package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ListItem {
     String text;
     String image;


    public void setName(String text) {
        this.text = text;
    }

    public String getName() {
        return text;
    }

    public String getImageLocation() {
        return image;
    }

    public void setImageLocation(String img) {
        this.image = img;
    }

    public ListItem(final String text, final String imageURL) {
      this.text=text;
      this.image=imageURL;
    }
}
