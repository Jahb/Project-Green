package nl.tudelft.gogreen.shared;

import javafx.scene.image.Image;

public enum ProfileEmblem {
	SAPLING, LEAF, APPLE, TREE;

	public Image getImage(ProfileEmblem emblem) {
		return new Image("Images/Emblem" + emblem + ".png");
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.substring(0, 1) + str.substring(1).toLowerCase();
	}
}
