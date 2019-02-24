package nl.tudelft.gogreen.client;

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
import jdk.internal.util.xml.impl.Input;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static javafx.geometry.Pos.CENTER;

/**
 * Authentication Demo To Demonstrate Client Server Communication
 *
 * @author Jahson
 * @version 1.0
 */
public class AuthenticationDEMO extends Application implements EventHandler<ActionEvent> {
    //Intit Buttons
    private Button login;
    private Button register;
    private Button ping;
    private Button back;

    private Stage window;
    private Scene mainScene;
    private Scene unimpScene;

    public static void main(String[] args) {
        //Start Application implementation;
        launch(args);
    }

    /**
     * Start method overide from Application contains Scenes and buttons
     *
     * @param main
     * @throws Exception
     */
    @Override
    public void start(Stage main) throws Exception {
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

        //Layout for Butons Hbox (One after other)
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(15, 12, 15, 12));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(register, login, ping);

        buttons.setAlignment(CENTER);

        //Main Layout BorderPain (Top, Left, Right, Center, Button)
        BorderPane mainLayout = new BorderPane();
        mainLayout.setBottom(buttons);

////////////Failed Image adding to GUI
//       ImageView logo= new ImageView();
//       logo.setImage(new Image("/client/src/main/assets/logo.png"));
//        center.getChildren().add(logo);

        Text centertxt = new Text("Welcome to the Login Demo\n This is the show communication between the Server and Client");
        StackPane center = new StackPane();
        center.getChildren().add(centertxt);

        mainLayout.setCenter(center);

        //Adding Scene to window
        Scene scene = new Scene(mainLayout, 500, 200);
        mainScene = scene;
        window.setScene(scene);
        window.show();

        ///////////////////////Creating UnImplemented Scene TO BE DELETED
        BorderPane subLayout = new BorderPane();

        Text unimp = new Text("Sorry This Has Not Been Implemented Yet");
        back = new Button();
        back.setText("Go Back To Login Page");
        back.setOnAction(this);


        subLayout.setBottom(back);
        subLayout.setCenter(unimp);
        subLayout.setAlignment(back, Pos.CENTER);

        Scene scene2 = new Scene(subLayout, 500, 200);
        unimpScene = scene2;


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
            window.setScene(unimpScene);
        }
        if (event.getSource() == register) {
            window.setScene(unimpScene);
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
