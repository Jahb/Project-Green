package nl.tudelft.gogreen.client;

import java.util.Arrays;

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

public class ScoreGraph {

    AnchorPane pane;
    UndecoratedGraph graph;
    DatePeriod datePeriod = DatePeriod.WEEK;
    Text title;
    StackPane titleBackground;

    ScoreGraph(int x, int y, int width, int height) {
        graph = new UndecoratedGraph(width, height);

        title = new Text(datePeriod.toString());
        title.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        title.setFill(Color.WHITE);
        titleBackground = new StackPane(title);
        titleBackground.setBackground(new Background(
                new BackgroundFill(new Color(.2, .2, .2, .7), new CornerRadii(0, 0, 10, 0, false), null)));
        titleBackground.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(0, 0, 10, 0, false), new BorderWidths(0, 1, 1, 0))));
        titleBackground.setPadding(new Insets(5, 0, 5, 0));
        titleBackground.setPrefWidth(100);

        pane = new AnchorPane(graph.getCanvas(), titleBackground);
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));

    }

    public Pane getPane() {
        return pane;
    }

    public UndecoratedGraph getGraph() {
        return graph;
    }

    public class UndecoratedGraph {
        private Canvas graphCanvas;
        private GraphicsContext g;

        private double[] allData = new double[1];
        private double minX;
        private double maxX;
        private double minY;
        private double maxY;
        private double width;
        private double height;

        UndecoratedGraph(int width, int height) {
            this.width = width;
            this.height = height;

            graphCanvas = new Canvas(width, height);
            g = graphCanvas.getGraphicsContext2D();

        }

        public void setData(double[] data) {
            allData = data;
        }

        public void drawGraph() {
        	final int padding = 20;
        	final double insetWidth = width-padding*2;
        	final double insetHeight = height-padding*2;
        	
            final int len = allData.length;
            double[] xPoints = new double[len];
            Arrays.setAll(xPoints, a -> padding + a / (double) (len - 1) * insetWidth);
            double[] yPoints = Arrays.stream(allData).map(a -> 
            		padding + insetHeight - (a - minY) / (maxY - minY) * insetHeight).toArray();
            System.out.println(Arrays.toString(xPoints));
            System.out.println(Arrays.toString(yPoints));

            g.setFill(Color.WHITE);
            g.fillRect(0, 0, width, height);

            g.setLineWidth(5);
            g.setStroke(Color.DARKGRAY);
            g.setFill(Color.DARKGRAY);
            g.transform(1, 0, 0, 1, 3, 3);
            g.strokePolyline(xPoints, yPoints, len);
            g.transform(1, 0, 0, 1, -3, -3);
            
            final double r = 12;
            for (int i = 0; i < len; i++)
                g.fillOval(xPoints[i] - r / 2 + 3, yPoints[i] - r / 2 + 3, r, r);

            
            g.setStroke(Color.RED);
            g.strokePolyline(xPoints, yPoints, len);

//            final double r = 12;
            for (int i = 0; i < len; i++) {
                g.setFill(Color.RED);
                g.fillOval(xPoints[i] - r / 2, yPoints[i] - r / 2, r, r);
                g.setFill(Color.WHITE);
                g.fillOval(xPoints[i] - r / 4, yPoints[i] - r / 4, r / 2, r / 2);
            }
        }

        public void standardizeY() {
            minY = Double.MAX_VALUE;
            maxY = Double.MIN_VALUE;
            for (double d : allData) {
                minY = Math.min(minY, d);
                maxY = Math.max(maxY, d);
            }
        }

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
