package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ListItem {
     String text;
     String image;
     String status;
     int score;


    public void setName(String text) {
        this.text = text;
    }

    public String getName() {
        return text;
    }

    public String getImageLocation() {
        return image;
    }

    public int getScore(){ return score;}

    public String getStatus(){ return status;}

    public void setImageLocation(String img) {
        this.image = img;
    }

    public ListItem(final String text, String imageURL, int score) {
      this.text=text;
      this.image=imageURL;
      this.score=score;
    }
    public ListItem(final String text, String imageURL) {
        this.text=text;
        this.image=imageURL;
    }
    public ListItem(final String text, String imageURL, String status) {
        this.text=text;
        this.image=imageURL;
        this.status=status;
    }
}
