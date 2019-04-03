package nl.tudelft.gogreen.client;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Ring {

    private Circle innerCircle = new Circle();
    private Circle outerCircle = new Circle();
    private ArrayList<RingSegment> segments = new ArrayList<>();
    private int centerOffs;
    private long timerStart;
    private Consumer<String> handler;
    private String name;
    private Text temporaryUsername = new Text();
    private StackPane textPane;
    private AnchorPane pane;
    private int totalPoints = -1;
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

        textPane = new StackPane(temporaryUsername);
        textPane.setLayoutX(50);
        textPane.setLayoutY(centerOffs * 2 + 5);
        textPane.setBackground(new Background(new BackgroundFill(
        		new Color(1, 1, 1, .9), new CornerRadii(10), null)));
        textPane.setBorder(new Border(new BorderStroke(
        		Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), null)));
        textPane.setPadding(new Insets(5, 0, 5, 0));
        temporaryUsername.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        temporaryUsername.setY(30);

        pane = new AnchorPane();
        pane.getChildren().add(outerCircle);
        pane.getChildren().add(innerCircle);
        pane.getChildren().add(textPane);
        for (RingSegment rs : segments)
            rs.addNodes(pane);
        innerCircle.toFront();
        
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
        if(name.equals("MAIN") || username == null || username.equals("")) {
            textPane.setVisible(false);
            return;
        }
        temporaryUsername.setText(username);
        textPane.setPrefWidth(150);
        textPane.setLayoutX(centerOffs - 75);
        textPane.setLayoutY(-50);

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
        isEmpty();
        timerStart = System.nanoTime();
        timer.start();

    }

    private AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long time) {
            double progress = (time - timerStart) / (3_000_000_000d / MAXPOINTS);
            double startAngle = 0;

            if (progress > MAXPOINTS)
                timer.stop();

            for (RingSegment rs : segments) {
                if (rs.delta == 0)
                    continue;
                rs.arc.setStartAngle(90 + startAngle);
                double animationLength = Math.max(-1, Math.min(progress / rs.delta, 1));
                rs.arc.setLength((rs.points + smoothFormula(animationLength) * rs.delta) * -360 / MAXPOINTS);

                if (animationLength == 1 || animationLength == -1) {
                    rs.points += rs.delta;
                    rs.delta = 0;
                    
                }
                rs.updateHoverText(rs.points + smoothFormula(animationLength) * rs.delta);
//                rs.cutArc.;
                startAngle += rs.arc.getLength();
            }
        }

        private double smoothFormula(double num) {
            return Math.sin(Math.PI * (num - .5)) / 2 + .5;
        }
    };

    private void isEmpty() {
        int sum = 0;
        for (RingSegment rs : segments) {
            sum += rs.points + rs.delta;
            rs.arc.setStrokeWidth(rs.points + rs.delta > 0 ? 1 : 0);

        }

        if (sum == totalPoints)
            return;

        if (sum == 0) {
            outerCircle.setFill(new Color(.7, .7, .7, 1));

        } else {
            outerCircle.setFill(Color.GRAY);
            System.out.println(segments.get(0).arc.getStrokeWidth());
        }
        totalPoints = sum;
    }

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
        private double points;
        double delta;
        Arc arc;
        StackPane hoverText;

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
            Text text = new Text(name + ": " + points);
            text.setFont(new Font(centerOffs/5));
            hoverText = new StackPane(text);
            BackgroundFill bgf = new BackgroundFill(new Color(1,1,1,.95), new CornerRadii(5), null);
            hoverText.setBackground(new Background(bgf));
            hoverText.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, null)));
            hoverText.setPadding(new Insets(0, 5, 0, 5));
            hoverText.setAlignment(Pos.TOP_CENTER);
            hoverText.setMouseTransparent(true);
            hoverText.setVisible(false);

        }

        void updateHoverText(double points) {
            Text text = (Text) hoverText.getChildren().get(0);
            double width = hoverText.getLayoutBounds().getWidth();
            double height = hoverText.getLayoutBounds().getHeight();

            final double r = (.25 - points / MAXPOINTS / 2) * Math.PI * 2;
            double x = Math.cos(r) * centerOffs * .90;
            double y = -Math.sin(r) * centerOffs * .90;
            
            if (x < 0)
                x -= width;
            if (y < 0)
                y -= height;
            hoverText.setLayoutX(centerOffs + x);
            hoverText.setLayoutY(centerOffs + y);
            if(ringName.equals("MAIN"))
                text.setText(name + ": " + (int)(points));
            else
                text.setText(""+(int)(points));
        }

        void addNodes(Pane root) {
            root.getChildren().add(arc);
            root.getChildren().add(hoverText);
        }

        void addTransitions() {
            Color mouseOver = blend(color);
            Transition brighten = new FillTransition(Duration.millis(200), arc, color, mouseOver);
            Transition resetColor = new FillTransition(Duration.millis(100), arc, mouseOver, color);

            arc.setOnMouseEntered(event -> {
                resetColor.jumpTo(Duration.millis(100));
                brighten.playFromStart();
                hoverText.setVisible(true);
            });

            arc.setOnMouseExited(event -> {
                brighten.jumpTo(Duration.millis(200));
                resetColor.playFromStart();
                hoverText.setVisible(false);
            });

            arc.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> handler.accept(ringName + ":" + name));
        }
    }

}