package nl.tudelft.gogreen.client;

import static javafx.geometry.Pos.CENTER;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Authentication Demo To Demonstrate Client Server Communication.
 *
 * @author Jahson
 * @version 1.0
 */
public class AuthenticationDemo extends Application implements EventHandler<ActionEvent> {
    //Buttons
    private Button login;
    private Button register;
    private Button ping;
    private Button back;

    private Stage window;
    private Scene mainScene;
    private Scene unImpScene;

    public static void main(String[] args) {
        //Start Application implementation;
        launch(args);
    }

    /**
     * Start method override from Application contains Scenes and buttons.
     *
     * @param main Main Stage.
     */
    @Override
    public void start(Stage main) {
        window = main;
        window.setTitle("GoGreen Authentication Demo");

        login = new Button();
        register = new Button();
        ping = new Button();
        login.setText("Login");
        register.setText("Register");
        ping.setText("Ping Server");
        //Attaching Event Handler to Buttons
        login.setOnAction(this);
        register.setOnAction(this);
        ping.setOnAction(this);

        //Layout for Buttons H box (One after other)
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(15, 12, 15, 12));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(register, login, ping);

        buttons.setAlignment(CENTER);

        //Main Layout BorderPain (Top, Left, Right, Center, Button)
        BorderPane mainLayout = new BorderPane();
        mainLayout.setBottom(buttons);

        Text centerTxt = new Text("Welcome to the Login Demo\n " +
                "This is the show communication between the Server and Client");
        StackPane center = new StackPane();
        center.getChildren().add(centerTxt);

        mainLayout.setCenter(center);

        //Adding Scene to window
        Scene scene = new Scene(mainLayout, 500, 200);
        mainScene = scene;
        window.setScene(scene);
        window.show();

        back = new Button();
        back.setText("Go Back To Login Page");
        back.setOnAction(this);

        BorderPane subLayout = new BorderPane();
        Text unImp = new Text("Sorry This Has Not Been Implemented Yet");

        subLayout.setBottom(back);
        subLayout.setCenter(unImp);
        BorderPane.setAlignment(back, Pos.CENTER);

        unImpScene = new Scene(subLayout, 500, 200);


    }

    /**
     * EventHandler on Mouse Click
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == login) {
            window.setScene(unImpScene);
        }
        if (event.getSource() == register) {
            window.setScene(unImpScene);
        }
        if (event.getSource() == ping) {
            //Implement Method calling here
            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpGet get = new HttpGet("http://localhost:8080/ping");

            try {
                CloseableHttpResponse res = httpclient.execute(get);
                System.out.println(EntityUtils.toString(res.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == back) {
            window.setScene(mainScene);
        }
    }
}
