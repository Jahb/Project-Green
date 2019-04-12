package nl.tudelft.gogreen.client;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
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
import nl.tudelft.gogreen.shared.ProfileEmblem;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Ring {

    private static final double MAXPOINTS = 1000;
    private Circle innerCircle = new Circle();
    private Circle outerCircle = new Circle();
    private ImageView emblem;
    private ArrayList<RingSegment> segments = new ArrayList<>();
    private int centerOffs;
    private long timerStart;
    private Consumer<String> handler;
    private String name;
    private Text temporaryUsername = new Text();
    private StackPane textPane;
    private AnchorPane pane;
    private int totalPoints = -1;

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long time) {
            final double progress = Math.min((time - timerStart) / 2_000_000_000d, 1);

            double startAngle = 0;
            for (RingSegment rs : segments) {
                if (rs.delta == 0) {
                    rs.arc.setStartAngle(90 + startAngle);
                    rs.updateHoverText(startAngle, rs.points);
                    startAngle += rs.arc.getLength();
                    continue;
                }
                rs.arc.setStartAngle(90 + startAngle);
                final double animationLength = Math.max(-1, Math.min(1,
                        progress * MAXPOINTS / rs.delta));
                final double showPoints = rs.points + smoothFormula(animationLength) * rs.delta;
                rs.arc.setLength(showPoints / MAXPOINTS * -360);
                rs.updateHoverText(startAngle, showPoints);
                startAngle += rs.arc.getLength();

                if (Math.abs(animationLength) == 1) {
                    rs.points += rs.delta;
                    rs.delta = 0;
                }
            }

            if (progress > 1)
                timer.stop();
        }

        private double smoothFormula(double num) {
            return Math.sin(Math.PI * (num - .5)) / 2 + .5;
        }
    };

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
        innerCircle.setFill(Color.WHITE);
        innerCircle.setStroke(Color.BLACK);

        outerCircle.setCenterX(centerOffs);
        outerCircle.setCenterY(centerOffs);
        outerCircle.setRadius(outerRadius);
        outerCircle.setFill(Color.LIGHTGREY);
        outerCircle.setStroke(Color.BLACK);

        addSegment(Color.LIME, "Food");
        addSegment(Color.YELLOW, "Energy");
        addSegment(Color.GREEN, "Transport");
        addSegment(Color.GOLD, "Bonus");

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
        emblem = new ImageView();
        emblem.setX(outerRadius - innerRadius + 2);
        emblem.setY(outerRadius - innerRadius + 2);
        emblem.setFitWidth(innerRadius * 2 - 4);
        emblem.setFitHeight(innerRadius * 2 - 4);

        pane = new AnchorPane();
        pane.getChildren().add(outerCircle);
        pane.getChildren().add(innerCircle);
        pane.getChildren().add(emblem);
        pane.getChildren().add(textPane);
        for (RingSegment rs : segments)
            rs.addNodes(pane);
        innerCircle.toFront();
        emblem.toFront();

        pane.setLayoutX(centerX - outerCircle.getRadius());
        pane.setLayoutY(centerY - outerCircle.getRadius());

    }

    private void addSegment(Color color, String name) {
        segments.add(new RingSegment(this, 0, color, name));
    }

    void setHandler(Consumer<String> handler) {
        this.handler = handler;
    }

    Pane getPane() {
        return pane;
    }

    void setX(int centerX) {
        pane.setLayoutX(centerX - outerCircle.getRadius());
    }

    /**
     * Sets the name of the owner of the Ring.
     * 
     * @param username name to set
     */
    public void setUsername(String username) {
        if (name.equals("MAIN") || username == null || username.equals("")) {
            textPane.setVisible(false);
            return;
        }
        temporaryUsername.setText(username);
        textPane.setPrefWidth(150);
        textPane.setLayoutX(centerOffs - 75);
        textPane.setLayoutY(-50);
        updateEmblem();
    }

    /**
     * Sets all the values of the Ring.
     * 
     * @param newValues values to set
     */
    public void setSegmentValues(double... newValues) {
        for (int i = 0; i < newValues.length; i++) {
            segments.get(i).delta = newValues[i] - segments.get(i).points;
        }
    }

    /**
     * Sets all the values of the Ring.
     * 
     * @param newValues values to set
     */
    public void addToSegmentValues(double... newValues) {
        for (int i = 0; i < newValues.length; i++) {
            segments.get(i).delta = newValues[i];
        }
    }

    private void updateEmblem() {
        emblem.setImage(ProfileEmblem.getImage((int) (totalPoints / MAXPOINTS * 4 + 1)));
    }

    public String getName() {
        return name;
    }

    /**
     * Starts the ring animation.
     * 
     */
    public void startAnimation() {
        isEmpty();
        updateEmblem();

        timerStart = System.nanoTime();
        timer.start();
    }

    private void isEmpty() {
        int sum = 0;
        for (RingSegment rs : segments) {
            sum += rs.points + rs.delta;
            rs.arc.setStrokeWidth(rs.points + rs.delta > 0 ? 1 : 0);
        }
        if (sum == totalPoints)
            return;
        if (sum == 0)
            outerCircle.setFill(new Color(.7, .7, .7, 1));
        else
            outerCircle.setFill(Color.GRAY);

        totalPoints = sum;
    }

    private Color blend(Color c1) {
        final double ratio = 0.25;
        final double iRatio = 1d - ratio;

        final double r1 = c1.getRed();
        final double g1 = c1.getGreen();
        final double b1 = c1.getBlue();

        final double r = Math.sqrt((r1 * r1 * iRatio) + ratio);
        final double g = Math.sqrt((g1 * g1 * iRatio) + ratio);
        final double b = Math.sqrt((b1 * b1 * iRatio) + ratio);

        return new Color(r, g, b, 1);
    }

    private class RingSegment {
        private Color color;
        private String name;
        private String ringName;
        private double points;
        private double delta;
        private Arc arc;
        private StackPane hoverText;

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
            text.setFont(new Font(centerOffs / 5));
            hoverText = new StackPane(text);
            BackgroundFill bgf = new BackgroundFill(
                    new Color(1, 1, 1, .95), new CornerRadii(5), null);
            hoverText.setBackground(new Background(bgf));
            hoverText.setBorder(new Border(new BorderStroke(
                    Color.DARKGRAY, BorderStrokeStyle.SOLID, null, null)));
            hoverText.setPadding(new Insets(0, 5, 0, 5));
            hoverText.setAlignment(Pos.TOP_CENTER);
            hoverText.setMouseTransparent(true);
            hoverText.setVisible(false);
        }

        void updateHoverText(double startAngle, double points) {
            Text text = (Text) hoverText.getChildren().get(0);
            double width = hoverText.getLayoutBounds().getWidth();
            double height = hoverText.getLayoutBounds().getHeight();

            final double r = (startAngle / 360 + (.25 - points / MAXPOINTS / 2)) * Math.PI * 2;
            double xpos = Math.cos(r) * centerOffs * .90;
            double ypos = -Math.sin(r) * centerOffs * .90;

            if (xpos < 0)
                xpos -= width;
            if (ypos < 0)
                ypos -= height;
            hoverText.setLayoutX(centerOffs + xpos);
            hoverText.setLayoutY(centerOffs + ypos);
            if (ringName.equals("MAIN"))
                text.setText(name + ": " + (int) points);
            else
                text.setText("" + (int) points);
        }

        void addNodes(Pane root) {
            root.getChildren().add(arc);
            root.getChildren().add(hoverText);
        }

        void addTransitions() {
            Color mouseOver = blend(color);
            Transition brighten = new FillTransition(Duration.millis(200), arc, color, mouseOver);
            Transition resetColor = new FillTransition(
                    Duration.millis(100), arc, mouseOver, color);

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

            arc.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> handler.accept(ringName + ":" + name));
        }
    }

}