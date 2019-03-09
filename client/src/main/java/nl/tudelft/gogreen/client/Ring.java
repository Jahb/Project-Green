package nl.tudelft.gogreen.client;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ring {

    public Ring(int innerRadius, int outerRadius, int centerx, int centery) {
        this.centerx = centerx;
        this.centery = centery;

        innerCircle.setCenterX(centerx);
        innerCircle.setCenterY(centery);
        innerCircle.setRadius(innerRadius);
        innerCircle.setFill(Color.LIGHTGRAY);
        innerCircle.setStroke(Color.BLACK);

        outerCircle.setCenterX(centerx);
        outerCircle.setCenterY(centery);
        outerCircle.setRadius(outerRadius);
        outerCircle.setFill(Color.GRAY);
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

    private Circle innerCircle = new Circle();
    private Circle outerCircle = new Circle();
    private ArrayList<RingSegment> segments = new ArrayList<RingSegment>();
    private int centerx;
    private int centery;

    public void setX(int x) {
        centerx = x;
        innerCircle.setCenterX(x);
        outerCircle.setCenterX(x);
        for (RingSegment rs : segments)
            rs.arc.setCenterX(x);
    }

    public void setY(int y) {
        centery = y;
        innerCircle.setCenterY(y);
        outerCircle.setCenterY(y);
        for (RingSegment rs : segments)
            rs.arc.setCenterY(y);
    }

    public void startAnimation() {
        timerStart = System.nanoTime();
        timer.start();
    }

    private long timerStart;
    private AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long time) {
            double progress = (time - timerStart) / 38_000_000d;
            double startAngle = 0;

            if (progress > 100)
                timer.stop();

            for (RingSegment rs : segments) {
                rs.arc.setStartAngle(90 + startAngle);
                double animationLength = Math.min(progress / rs.percentage, 1);
                rs.arc.setLength(smoothFormula(animationLength) * rs.percentage * -3.6);

                startAngle += rs.arc.getLength();
            }
        }

        private double smoothFormula(double num) {
            return Math.sin(Math.PI * (num - .5)) / 2 + .5;
        }
    };

    Color blend(Color c1, Color c2, double ratio) {
        if (ratio > 1)
            ratio = 1;
        else if (ratio < 0)
            ratio = 0;
        final double iRatio = 1d - ratio;

        final double a1 = c1.getOpacity();
        final double r1 = c1.getRed();
        final double g1 = c1.getGreen();
        final double b1 = c1.getBlue();

        final double a2 = c2.getOpacity();
        final double r2 = c2.getRed();
        final double g2 = c2.getGreen();
        final double b2 = c2.getBlue();

        final double a = (a1 * iRatio) + (a2 * ratio);
        final double r = Math.sqrt((r1 * r1 * iRatio) + (r2 * r2 * ratio));
        final double g = Math.sqrt((g1 * g1 * iRatio) + (g2 * g2 * ratio));
        final double b = Math.sqrt((b1 * b1 * iRatio) + (b2 * b2 * ratio));

        return new Color(r, g, b, a);
    }

    class RingSegment {
        public RingSegment(Ring ring, double percentage, Color color) {
            this.percentage = percentage;
            this.color = color;

            arc = new Arc();

            arc.setCenterX(ring.centerx);
            arc.setCenterY(ring.centery);
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

        protected Arc arc;
        protected Color color;
        protected Ring ring;
        protected double percentage;

        void addTransitions() {
            Color mouseOver = blend(color, Color.WHITE, 1d / 4);
            Transition mouseEnter = new FillTransition(Duration.millis(200), arc, color, mouseOver);
            Transition mouseExit = new FillTransition(Duration.millis(100), arc, mouseOver, color);

            arc.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseExit.jumpTo(Duration.millis(100));
                    mouseEnter.playFromStart();

                }
            });

            arc.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseEnter.jumpTo(Duration.millis(200));
                    mouseExit.playFromStart();
                }
            });
        }
    }

}