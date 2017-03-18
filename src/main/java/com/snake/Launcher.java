package com.snake;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	Game game;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		game = new Game(primaryStage);
		game.start();

	}

}
