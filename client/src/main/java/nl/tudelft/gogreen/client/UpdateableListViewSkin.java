package nl.tudelft.gogreen.client;


import com.sun.javafx.scene.control.skin.ListViewSkin;

import javafx.scene.control.ListView;

class UpdateableListViewSkin<ListItem> extends ListViewSkin<ListItem> {

    UpdateableListViewSkin(ListView<ListItem> arg0) {
        super(arg0);
    }

    void refresh() {
        super.flow.rebuildCells();
    }
}
