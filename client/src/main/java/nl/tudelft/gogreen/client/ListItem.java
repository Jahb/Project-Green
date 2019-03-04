package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ListItem {
    private String name;
    private String imageLocation;
    public String getName() {
        return name;
    }
    public String getImageLocation() {
        return imageLocation;
    }
    public ListItem(String name, String location) {
        super();
        this.name = name;
        this.imageLocation = location;
    }
}
