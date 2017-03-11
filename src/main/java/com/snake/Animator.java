package com.snake;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Animator extends AnimationTimer {
	private int WIDTH = 600;
	private int HEIGHT = 400;
	boolean up, down, left, right;
	private Snake snake = new Snake(WIDTH / 2, HEIGHT / 2, Color.GREEN);
	private Food food = new Food(200, 334, Color.RED);
	int dx = 1, dy = 0;

	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private VBox vbox = new VBox(canvas);
	private Stage primaryStage;
	private int SCORE = 0;

	Animator(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(new Scene(vbox));
		canvas.setFocusTraversable(true);
		this.primaryStage.show();

	}

	// GAME LOOP
	@Override
	public void handle(long arg0) {

		update();
		show();

	}

	private boolean checkIfFoodAte() {
		if ((Math.abs(snake.getPosX() - food.getPosX()) < 10 || Math.abs((snake.getPosX() + 10) - food.getPosX()) < 10)
				&& (Math.abs(snake.getPosY() - food.getPosY()) < 10
						|| Math.abs((snake.getPosY() + 10) - food.getPosY()) < 10)) {
			return true;
		}
		return false;
	}

	private void DropNewFoodLocation() {
		food.setPosX(new Random().nextInt(WIDTH));
		food.setPosY(new Random().nextInt(HEIGHT));
		SCORE++;
	}

	private void checkIfKeyPressed() {
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				System.out.println("key pressed: " + key.getCode().toString());
				switch (key.getCode()) {
				case W:
					up = true;
					break;
				case S:
					down = true;
					break;
				case A:
					left = true;
					break;
				case D:
					right = true;
					break;

				}
			}
		});

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				System.out.println("key release: " + key.getCode().toString());
				switch (key.getCode()) {
				case W:
					up = false;
					break;
				case S:
					down = false;
					break;
				case A:
					left = false;
					break;
				case D:
					right = false;
					break;

				}
			}

		});
	}

	private void show() {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		snake.show(gc);
		food.show(gc);
	}

	private void update() {
		checkIfKeyPressed();
		if (checkIfFoodAte()) {
			DropNewFoodLocation();
		}
		

		if (up) {
			dy = -1;
			dx = 0;
		}
		if (down) {
			dy = 1;
			dx = 0;
		}
		if (left) {
			dx = -1;
			dy = 0;
		}
		if (right) {
			dx = 1;
			dy = 0;
		}
		snake.update(dx, dy);
		// food.update(dx, dy);
	}

}
