package com.snake;

import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {

	Animator animator;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		animator = new Animator(primaryStage);
		animator.start();

	}

}
