package nl.tudelft.gogreen.client;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ring {

    private Circle innerCircle = new Circle();
    private Circle outerCircle = new Circle();
    private ArrayList<RingSegment> segments = new ArrayList<RingSegment>();
    private int centerx, centery;


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

    void addNodes(AnchorPane root) {
        root.getChildren().add(outerCircle);
        for (RingSegment rs : segments)
            rs.addNodes(root);
        root.getChildren().add(innerCircle);
    }

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
        public void handle(long l) {
            double progress = ((l - timerStart) / 15_000_000d);
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

        private double smoothFormula(double n) {
            return Math.sin(Math.PI * (n - .5)) / 2 + .5;
        }
    };

    Color blend(Color c1, Color c2, double ratio) {
        if (ratio > 1f) ratio = 1;
        else if (ratio < 0f) ratio = 0;
        double iRatio = 1.0f - ratio;

        double a1 = c1.getOpacity();
        double r1 = c1.getRed();
        double g1 = c1.getGreen();
        double b1 = c1.getBlue();

        double a2 = c2.getOpacity();
        double r2 = c2.getRed();
        double g2 = c2.getGreen();
        double b2 = c2.getBlue();

        double a = ((a1 * iRatio) + (a2 * ratio));
        double r = Math.sqrt((r1 * r1 * iRatio) + (r2 * r2 * ratio));
        double g = Math.sqrt((g1 * g1 * iRatio) + (g2 * g2 * ratio));
        double b = Math.sqrt((b1 * b1 * iRatio) + (b2 * b2 * ratio));

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

            //Setting the type of the arc
            arc.setType(ArcType.ROUND);

            arc.setFill(color);
            arc.setStroke(Color.BLACK);

            addTransitions();
        }

        void addNodes(AnchorPane root) {
            root.getChildren().add(arc);
        }

        Arc arc;
        Color color;
        Ring ring;
        double percentage;


        void addTransitions() {
            Color mouseOver = blend(color, Color.WHITE, 1d / 4);
            Transition mouseEnter = new FillTransition(Duration.millis(200), arc, color, mouseOver);
            Transition mouseExited = new FillTransition(Duration.millis(100), arc, mouseOver, color);

            arc.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseExited.jumpTo(Duration.millis(100));
                    mouseEnter.playFromStart();

                }
            });

            arc.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseEnter.jumpTo(Duration.millis(200));
                    mouseExited.playFromStart();
                }
            });
        }
    }


}