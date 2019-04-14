package nl.tudelft.gogreen.client;

import com.sun.javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.ListView;

class UpdateableListViewSkin<I> extends ListViewSkin<I> {

    UpdateableListViewSkin(ListView<I> arg0) {
        super(arg0);
    }

    void refresh() {
        super.flow.rebuildCells();
    }
}
