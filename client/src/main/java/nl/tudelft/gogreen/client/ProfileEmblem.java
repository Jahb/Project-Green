package nl.tudelft.gogreen.client;

import javafx.scene.image.Image;

public enum ProfileEmblem {
    SAPLING, LEAF, APPLE, TREE;


    /**
     * Get the image for the level.
     * @param level the level
     * @return the image
     */
    public static Image getImage(int level) {
        if (level <= 0 || level > 4)
            return new Image("Images/IconEmpty.png");
        return new Image("Images/ppLvl" + level + ".png");
    }

    /**
     * Convert the level to an emblem.
     * @param level the level
     * @return an emblem
     */
    public static ProfileEmblem levelToEmblem(int level) {
        switch (level) {
            case 1:
                return SAPLING;
            case 2:
                return LEAF;
            case 3:
                return APPLE;
            case 4:
                return TREE;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        String str = super.toString();
        return str.substring(0, 1) + str.substring(1).toLowerCase();
    }
}
