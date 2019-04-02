package nl.tudelft.gogreen.client;


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
}
