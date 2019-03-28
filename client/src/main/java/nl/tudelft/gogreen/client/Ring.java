package nl.tudelft.gogreen.client;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Ring {

    private Circle innerCircle = new Circle();
    private Circle outerCircle = new Circle();
    private ArrayList<RingSegment> segments = new ArrayList<>();
    private int centerX;
    private int centerY;
    private long timerStart;

    /**
     * Constructor for Ring Class.
     * 
     * @param innerRadius Radius of inner Circle
     * @param outerRadius Radius of outer Circle
     * @param centerX     X coordinate of center of Circle
     * @param centerY     Y coordinate of center of Circle
     */
    public Ring(int innerRadius, int outerRadius, int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

        innerCircle.setCenterX(centerX);
        innerCircle.setCenterY(centerY);
        innerCircle.setRadius(innerRadius);
        innerCircle.setFill(Color.LIGHTGRAY);
        innerCircle.setStroke(Color.BLACK);

        outerCircle.setCenterX(centerX);
        outerCircle.setCenterY(centerY);
        outerCircle.setRadius(outerRadius);
        outerCircle.setFill(Color.WHITE);
        outerCircle.setStroke(Color.BLACK);
    }

    void addSegment(int percentage, Color color) {
        segments.add(new RingSegment(this, percentage, color));
    }

    Pane getPane() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(outerCircle);
        for (RingSegment rs : segments)
            rs.addNodes(anchorPane);
        anchorPane.getChildren().add(innerCircle);
        return anchorPane;
    }

    void setX(int cord) {
        centerX = cord;
        innerCircle.setCenterX(cord);
        outerCircle.setCenterX(cord);
        for (RingSegment rs : segments)
            rs.arc.setCenterX(cord);
    }

    void setSegmentValue(int segment, double newValue) throws ArrayIndexOutOfBoundsException {
        segments.get(segment).delta = newValue - segments.get(segment).percentage;
    }

    void startAnimation() {
//        if (System.nanoTime() - timerStart > 3000_000_000d) {
        timerStart = System.nanoTime();
        timer.start();
//        }
    }

    private AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long time) {
            double progress = (time - timerStart) / 30_000_000d;
            double startAngle = 0;

            if (progress > 100)
                timer.stop();
            

            for (RingSegment rs : segments) {
                rs.arc.setStartAngle(90 + startAngle);
                double animationLength = Math.max(-1, Math.min(progress / rs.delta, 1));
                rs.arc.setLength((rs.percentage + smoothFormula(animationLength) * rs.delta) * -3.6);
                
                if(animationLength == 1 || animationLength == -1) {
                    rs.percentage += rs.delta;
                    rs.delta = 0;
                }
                
                startAngle += rs.arc.getLength();
            }
        }

        private double smoothFormula(double num) {
            return Math.sin(Math.PI * (num - .5)) / 2 + .5;
        }
    };

    private Color blend(Color c1) {
        final double iRatio = 1d - 0.25;

        final double a1 = c1.getOpacity();
        final double r1 = c1.getRed();
        final double g1 = c1.getGreen();
        final double b1 = c1.getBlue();

        final double a2 = Color.WHITE.getOpacity();
        final double r2 = Color.WHITE.getRed();
        final double g2 = Color.WHITE.getGreen();
        final double b2 = Color.WHITE.getBlue();

        final double a = (a1 * iRatio) + (a2 * 0.25);
        final double r = Math.sqrt((r1 * r1 * iRatio) + (r2 * r2 * 0.25));
        final double g = Math.sqrt((g1 * g1 * iRatio) + (g2 * g2 * 0.25));
        final double b = Math.sqrt((b1 * b1 * iRatio) + (b2 * b2 * 0.25));

        return new Color(r, g, b, a);
    }

    class RingSegment {

        protected Color color;
        double percentage;
        double delta;
        Arc arc;

        RingSegment(Ring ring, double percentage, Color color) {
            this.delta = percentage;
            this.color = color;

            arc = new Arc();

            arc.setCenterX(ring.centerX);
            arc.setCenterY(ring.centerY);
            arc.setRadiusX(ring.outerCircle.getRadius());
            arc.setRadiusY(ring.outerCircle.getRadius());
            arc.setStartAngle(90);
            arc.setLength(0);

            // Setting the type of the arc
            arc.setType(ArcType.ROUND);

            arc.setFill(color);
            arc.setStroke(Color.BLACK);

            addTransitions();
        }

        void addNodes(Pane root) {
            root.getChildren().add(arc);
        }

        void addTransitions() {
            Color mouseOver = blend(color);
            Transition mouseEnter = new FillTransition(Duration.millis(200), arc, color, mouseOver);
            Transition mouseExit = new FillTransition(Duration.millis(100), arc, mouseOver, color);

            arc.setOnMouseEntered(event -> {
                mouseExit.jumpTo(Duration.millis(100));
                mouseEnter.playFromStart();

            });

            arc.setOnMouseExited(event -> {
                mouseEnter.jumpTo(Duration.millis(200));
                mouseExit.playFromStart();
            });
        }
    }

}