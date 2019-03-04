package nl.tudelft.gogreen.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static int width = 1280;
	public static int height = 720;

	@Override
	public void start(Stage primaryStage) throws IOException {
		long startTime = System.nanoTime();

		stage = primaryStage;
		openMainScreen();
		stage.show();

		System.out.println("Initialization code took " + ((System.nanoTime() - startTime) / 1000000 / 1000.0) + "s");
	}

	private static Stage stage;

	private static LoginScreen loginScreen = new LoginScreen();
	private static MainScreen mainScreen = new MainScreen();

	public static void openLoginScreen() {
		try {
			stage.setScene(loginScreen.getScene());
		} catch (Exception e) {
			pageOpenError(e);
		}
	}

	public static void openMainScreen() {
		try {
			stage.setScene(mainScreen.getScene());
		} catch (Exception e) {
			pageOpenError(e);
		}
	}

	public static void openLeaderboardScreen() {
		try {
			Parent root1 = FXMLLoader.load(Main.class.getResource("/LeaderboardGUI.fxml"));
			stage.setScene(new Scene(root1, width, height));
		} catch (Exception e) {
			pageOpenError(e);
		}
	}

	public static void openProfileScreen() {
		try {
			Parent root1 = FXMLLoader.load(Main.class.getResource("/ProfileGUI.fxml"));
			stage.setScene(new Scene(root1, width, height));
		} catch (Exception e) {
			pageOpenError(e);
		}
	}

	public static void openAchievementsScreen() {
		try {
			Parent root1 = FXMLLoader.load(Main.class.getResource("/SettingsGUI.fxml"));
			stage.setScene(new Scene(root1, width, height));
		} catch (Exception e) {
			pageOpenError(e);
		}
	}

	private static void pageOpenError(Exception e) {
		e.printStackTrace();
		Pane p = new Pane();
		p.getChildren().add(new Label("Something went wrong"));
		stage.setScene(new Scene(p, width, height));
	}

	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Unirest.setObjectMapper(new ObjectMapper() {
			private Gson gson = new GsonBuilder().setPrettyPrinting().create();

			@Override
			public <T> T readValue(String value, Class<T> valueType) {
				return gson.fromJson(value, valueType);
			}

			@Override
			public String writeValue(Object value) {
				return gson.toJson(value);
			}
		});

		launch(args);
	}
}
