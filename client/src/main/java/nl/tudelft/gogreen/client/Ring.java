package nl.tudelft.gogreen.client;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Ring {

    private Circle innerCircle = new Circle();
    private Circle outerCircle = new Circle();
    private ArrayList<RingSegment> segments = new ArrayList<>();
    private int centerOffs;
    private long timerStart;
    private Consumer<String> handler;
    private String name;
    private Text temporaryUsername = new Text();
    private Pane textPane = new Pane();
    private AnchorPane pane;
    static final double MAXPOINTS = 1000;

    /**
     * Constructor for Ring Class.
     *
     * @param innerRadius Radius of inner Circle
     * @param outerRadius Radius of outer Circle
     * @param centerX     X coordinate of center of Circle
     * @param centerY     Y coordinate of center of Circle
     */
    public Ring(int innerRadius, int outerRadius, int centerX, int centerY, String name) {
        this.name = name;
        centerOffs = outerRadius;


        innerCircle.setCenterX(centerOffs);
        innerCircle.setCenterY(centerOffs);
        innerCircle.setRadius(innerRadius);
        innerCircle.setFill(Color.LIGHTGRAY);
        innerCircle.setStroke(Color.BLACK);

        outerCircle.setCenterX(centerOffs);
        outerCircle.setCenterY(centerOffs);
        outerCircle.setRadius(outerRadius);
        outerCircle.setFill(Color.GRAY);
        outerCircle.setStroke(Color.BLACK);

        addSegment(0, Color.LIME, "Food");
        addSegment(0, Color.YELLOW, "Energy");
        addSegment(0, Color.GREEN, "Transport");

        textPane = new Pane(temporaryUsername);
        textPane.setLayoutX(50);
        textPane.setLayoutY(centerOffs * 2 + 5);
        textPane.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, .0), null, null)));
        temporaryUsername.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        temporaryUsername.setY(30);


        pane = new AnchorPane();
        pane.getChildren().add(outerCircle);
        for (RingSegment rs : segments)
            rs.addNodes(pane);
        pane.getChildren().add(innerCircle);
        pane.getChildren().add(textPane);
        pane.setLayoutX(centerX - outerCircle.getRadius());
        pane.setLayoutY(centerY - outerCircle.getRadius());

    }

    private void addSegment(int percentage, Color color, String name) {
        segments.add(new RingSegment(this, percentage, color, name));
    }

    public void setHandler(Consumer<String> handler) {
        this.handler = handler;
    }

    public Pane getPane() {
        return pane;
    }

    public void setX(int centerX) {
        pane.setLayoutX(centerX - outerCircle.getRadius());
    }

    public void setUsername(String username) {
        temporaryUsername.setText(username);
        System.out.println(temporaryUsername.getLayoutBounds().getWidth());
//        temporaryUsername.setWrappingWidth(temporaryUsername.getLayoutBounds().getWidth()+30)
        textPane.setLayoutX(centerOffs - temporaryUsername.getLayoutBounds().getWidth() / 2);
        textPane.setLayoutY(centerOffs - temporaryUsername.getLayoutBounds().getHeight() / 2);

    }

    public void setSegmentValues(double... newValues) throws ArrayIndexOutOfBoundsException {
        for (int i = 0; i < newValues.length; i++) {
            segments.get(i).delta = newValues[i] - segments.get(i).points;
        }

    }

    public String getName() {
        return name;
    }

    public void startAnimation() {
//        if (System.nanoTime() - timerStart > 3000_000_000d) {
        timerStart = System.nanoTime();
        timer.start();
//        }
    }

    private AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long time) {
            double progress = (time - timerStart) / (3_000_000_000d / MAXPOINTS);
            double startAngle = 0;

            if (progress > MAXPOINTS)
                timer.stop();


            for (RingSegment rs : segments) {
                rs.arc.setStartAngle(90 + startAngle);
                double animationLength = Math.max(-1, Math.min(progress / rs.delta, 1));
                rs.arc.setLength((rs.points + smoothFormula(animationLength) * rs.delta) * -360 / MAXPOINTS);

                if (animationLength == 1 || animationLength == -1) {
                    rs.points += rs.delta;
                    rs.delta = 0;
                }
//                rs.cutArc.;
                startAngle += rs.arc.getLength();
            }
        }

        private double smoothFormula(double num) {
            return Math.sin(Math.PI * (num - .5)) / 2 + .5;
        }
    };

    private Color blend(Color c1) {
        final double ratio = 0.25;
        final double iRatio = 1d - ratio;

        final double r1 = c1.getRed();
        final double g1 = c1.getGreen();
        final double b1 = c1.getBlue();

        final double r = Math.sqrt((r1 * r1 * iRatio) + (ratio));
        final double g = Math.sqrt((g1 * g1 * iRatio) + (ratio));
        final double b = Math.sqrt((b1 * b1 * iRatio) + (ratio));


        return new Color(r, g, b, 1);
    }

    private class RingSegment {

        protected Color color;
        protected String name;
        protected String ringName;
        double points;
        double delta;
        Arc arc;

        RingSegment(Ring ring, double percentage, Color color, String name) {
            this.delta = percentage;
            this.color = color;
            this.name = name;
            ringName = ring.name;

            arc = new Arc();

            arc.setCenterX(ring.centerOffs);
            arc.setCenterY(ring.centerOffs);
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

            arc.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> handler.accept(ringName + ":" + name));
        }
    }


}