package nl.tudelft.gogreen.client;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.tudelft.gogreen.shared.DatePeriod;

public class ScoreGraph {
    
    AnchorPane pane;
    UndecoratedGraph graph;
    DatePeriod datePeriod = DatePeriod.WEEK;
    
    ScoreGraph(int x, int y, int width, int height){
        graph = new UndecoratedGraph(width, height);
        
        pane = new AnchorPane(graph.graphCanvas);
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
            
            final int len = allData.length;
            double[] xPoints = new double[len]; 
            Arrays.setAll(xPoints, a -> a/(double)(len-1)*width);
            double[] yPoints = Arrays.stream(allData).map(a -> height-(a-minY)/(maxY-minY)*height).toArray();
            System.out.println(Arrays.toString(xPoints));
            System.out.println(Arrays.toString(yPoints));
            
            g.clearRect(0, 0, width, height);
            g.strokePolyline(xPoints, yPoints, len);
        }
        public void standardizeY() {
            minY = Double.MAX_VALUE;
            maxY = Double.MIN_VALUE;
            for(double d : allData) {
                minY = Math.min(minY, d);
                maxY = Math.max(maxY, d);
            }
        }
        
        Canvas getCanvas() {
            return graphCanvas;
        }
        
        
    }
}
