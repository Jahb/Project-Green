package nl.tudelft.gogreen.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	static int width = 1280;
	static int height = 720;

	LoginScreen loginScreen = new LoginScreen();
	MainScreen mainScreen = new MainScreen();

	@Override
	public void start(Stage primaryStage) throws IOException {
		long startTime = System.nanoTime();

		primaryStage.setScene(mainScreen.getScene());
		primaryStage.show();

		System.out.println("Initialization code took " + ((System.nanoTime() - startTime) / 1000000 / 1000.0) + "s");
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
