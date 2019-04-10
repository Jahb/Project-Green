package nl.tudelft.gogreen.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import nl.tudelft.gogreen.client.ScoreGraph.UndecoratedGraph;
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.shared.DateHolder;
import nl.tudelft.gogreen.shared.DatePeriod;

public class LeaderboardController implements Initializable {

    @FXML
    private AnchorPane notificationPane;
    @FXML
    private JFXButton timeframeButton;
    @FXML
    private LineChart<Integer, Double> scoreChart;
    @FXML
    private ListView<ListItem> leaderboardList = new ListView<>();
    private final ObservableList<ListItem> items = FXCollections.observableArrayList();

    private DatePeriod currentDatePeriod = DatePeriod.YEAR;


    /**
     * Return the LeaderBoard GUI Scene.
     *
     * @return LeaderBoard Scene.
     * @throws IOException An Exception
     */
    public Scene getScene() throws IOException {
        URL url = Main.class.getResource("/LeaderboardGUI.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        BorderPane topPane = (BorderPane) root.getChildren().get(1);

        root.getChildren().remove(2);
        createGraph(root);


        IconButton.addBackButton(topPane);
        return new Scene(root, Main.getWidth(), Main.getHeight());
    }

    private void createGraph(AnchorPane root) {
        ScoreGraph graph1 = new ScoreGraph(500, 200, 400, 400);
        root.getChildren().add(graph1.getPane());
        UndecoratedGraph graph = graph1.getGraph();
        graph.setData(new double[]{0, 20, 20, 60, 80, 80, 120});
        graph.standardizeY(250);
        graph.drawGraph();
    }

    /**
     * Initializes images for class.
     *
     * @param location  Image URL
     * @param resources ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {

        //testing notifications
        Main.showMessage(notificationPane, "You have opened the leaderboard");


        XYChart.Series<Integer, Integer> weekly = new XYChart.Series<>();
        scoreChart.setLegendVisible(false);

        weekly.getData().add(new XYChart.Data<>(1, 24));
        weekly.getData().add(new XYChart.Data<>(2, 15));
        weekly.getData().add(new XYChart.Data<>(3, 28));
        weekly.getData().add(new XYChart.Data<>(4, 21));

        XYChart.Series<Integer, Integer> monthly = new XYChart.Series<>();
        monthly.getData().add(new XYChart.Data<>(1, 23));
        monthly.getData().add(new XYChart.Data<>(5, 13));
        monthly.getData().add(new XYChart.Data<>(10, 19));
        monthly.getData().add(new XYChart.Data<>(15, 25));

        UpdateableListViewSkin<ListItem> skin = new UpdateableListViewSkin<>(this.leaderboardList);
        this.leaderboardList.setSkin(skin);

        timeframeButton.setOnMouseClicked(event -> {
            currentDatePeriod = currentDatePeriod.getNext();
            DateHolder dates = Api.current.getDatesFor(currentDatePeriod);

            XYChart.Series<Integer, Double> data = new XYChart.Series<>();
            scoreChart.getData().clear();
            for (int i = 0; i < dates.getDays(); i++) {
                data.getData().add(new XYChart.Data<>(dates.getDays() - i, dates.getData()[i]));
            }
            scoreChart.getData().add(data);
            scoreChart.getXAxis().setLabel(currentDatePeriod.name());
            switch (currentDatePeriod) {
                case WEEK:
                case MONTH:
                case YEAR:
                default:
            }
        });

        timeframeButton.getOnMouseClicked().handle(null);


        leaderboardList.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {
            @Override
            public ListCell<ListItem> call(ListView<ListItem> arg0) {
                JFXListCell<ListItem> cell = new JFXListCell<ListItem>() {
                    @Override
                    public void updateItem(ListItem item, boolean bool) {
                        super.updateItem(item, bool);
                        if (item != null && !bool) {
                            setGraphic(ListItem.imageView(item));
                            setText(item.getName() + "\nScore: " + item.getScore());

                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                cell.setEditable(true);
                cell.setOnMouseClicked((MouseEvent event) -> {
                    if (cell.isEmpty()) {
                        event.consume();
                    } else Main.openProfileScreen();
                });
                return cell;
            }
        });
        leaderboardList.setEditable(true);

        leaderboardList.setItems(items);

        scoreChart.getXAxis().setTickLabelsVisible(false);


    }
}


