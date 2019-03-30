package nl.tudelft.gogreen.client;


import com.sun.javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.ListView;

public class UpdateableListViewSkin<ListItem> extends ListViewSkin<ListItem> {

    public UpdateableListViewSkin(ListView<ListItem> arg0) {
        super(arg0);
    }

    public void refresh() {
        super.flow.rebuildCells();
    }

    @SuppressWarnings("unchecked")
    static <ListItem> UpdateableListViewSkin<ListItem> cast(Object obj) {
        return (UpdateableListViewSkin<ListItem>)obj;
    }

}
