package nl.tudelft.gogreen.client;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.tudelft.gogreen.shared.DatePeriod;

import java.util.Arrays;

public class ScoreGraph {

    AnchorPane pane;
    UndecoratedGraph graph;
    DatePeriod datePeriod = DatePeriod.WEEK;
    Text title;
    StackPane titleBackground;

    ScoreGraph(int xoffs, int yoffs, int width, int height) {
        graph = new UndecoratedGraph(width, height);

        title = new Text(datePeriod.toString());
        title.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        title.setFill(Color.WHITE);
        titleBackground = new StackPane(title);
        titleBackground.setBackground(new Background(new BackgroundFill(
            new Color(.2, .2, .2, .7), new CornerRadii(0, 0, 10, 0, false), null)));
        titleBackground.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(0, 0, 10, 0, false), new BorderWidths(0, 1, 1, 0))));
        titleBackground.setPadding(new Insets(5, 0, 5, 0));
        titleBackground.setPrefWidth(100);

        pane = new AnchorPane(graph.getCanvas(), titleBackground);
        pane.setLayoutX(xoffs);
        pane.setLayoutY(yoffs);
        pane.setBorder(new Border(new BorderStroke(
            Color.BLACK, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));

    }

    public Pane getPane() {
        return pane;
    }

    public UndecoratedGraph getGraph() {
        return graph;
    }

    public class UndecoratedGraph {
        private Canvas graphCanvas;
        private GraphicsContext gc;

        private double[] allData = new double[1];
        private double minY;
        private double maxY;
        private double width;
        private double height;

        UndecoratedGraph(int width, int height) {
            this.width = width;
            this.height = height;

            graphCanvas = new Canvas(width, height);
            gc = graphCanvas.getGraphicsContext2D();

        }

        public void setData(double[] data) {
            allData = data;
        }

        /**
         * Draws the graph to the canvas.
         * 
         */
        public void drawGraph() {
            final int padding = 20;
            final double insetWidth = width - padding * 2;
            final double insetHeight = height - padding * 2;

            final int len = allData.length;
            double[] xpoints = new double[len];
            Arrays.setAll(xpoints, a -> padding + a / (double) (len - 1) * insetWidth);
            double[] ypoints = Arrays.stream(allData).map(a ->
                    padding + insetHeight - (a - minY) / (maxY - minY) * insetHeight).toArray();
            System.out.println(Arrays.toString(xpoints));
            System.out.println(Arrays.toString(ypoints));

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, width, height);

            gc.setLineWidth(5);
            gc.setStroke(Color.DARKGRAY);
            gc.setFill(Color.DARKGRAY);
            gc.transform(1, 0, 0, 1, 3, 3);
            gc.strokePolyline(xpoints, ypoints, len);
            gc.transform(1, 0, 0, 1, -3, -3);

            final double r = 12;
            for (int i = 0; i < len; i++)
                gc.fillOval(xpoints[i] - r / 2 + 3, ypoints[i] - r / 2 + 3, r, r);


            gc.setStroke(Color.RED);
            gc.strokePolyline(xpoints, ypoints, len);

            for (int i = 0; i < len; i++) {
                gc.setFill(Color.RED);
                gc.fillOval(xpoints[i] - r / 2, ypoints[i] - r / 2, r, r);
                gc.setFill(Color.WHITE);
                gc.fillOval(xpoints[i] - r / 4, ypoints[i] - r / 4, r / 2, r / 2);
            }
        }

        /**
         * Updates the minY and maxY.
         * 
         * @param snapOntoValue the value for the graphs y-axis to snap onto.
         */
        public void standardizeY(double snapOntoValue) {
            minY = Double.MAX_VALUE;
            maxY = Double.MIN_VALUE;
            for (double d : allData) {
                minY = Math.min(minY, d);
                maxY = Math.max(maxY, d);
            }
            minY = Math.floor(minY / snapOntoValue) * snapOntoValue;
            maxY = Math.ceil(maxY / snapOntoValue) * snapOntoValue;
        }

        Canvas getCanvas() {
            return graphCanvas;
        }

    }
}
