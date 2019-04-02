package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Cursed ListItem?
 * What does it do, how does it work?
 * Nobody knows
 */
class ListItem {

    private String text;
    private String image;
    private String status;
    private int score;

    ListItem(final String text, String image) {
        this.text = text;
        this.image = image;
    }

    ListItem(final String text, String image, int score) {
        this.text = text;
        this.image = image;
        this.score = score;
    }

    ListItem(final String text, String image, String status) {
        this.text = text;
        this.image = image;
        this.status = status;
    }

    String getName() {
        return text;
    }


    String getImageLocation() {
        return image;
    }

    int getScore() {
        return score;
    }

    String getStatus() {
        return status;
    }

    static ImageView imageView(ListItem item) {
        Image img = new Image(ListItem.class
                .getResource("/" + item.getImageLocation()).toExternalForm());
        javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(img);
        imgView.setFitHeight(90);
        imgView.setFitWidth(90);
        return imgView;
    }
}
