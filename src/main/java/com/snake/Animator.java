package com.snake;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Animator extends AnimationTimer {
	private int WIDTH = 600;
	private int HEIGHT = 400;
	boolean up, down, left, right;
	Snake snake = new Snake(WIDTH / 2, HEIGHT / 2);

	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private VBox vbox = new VBox(canvas);
	private Stage primaryStage;

	Animator(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(new Scene(vbox));
		canvas.setFocusTraversable(true);
		this.primaryStage.show();

	}

	// GAME LOOP
	@Override
	public void handle(long arg0) {

		checkIfKeyPressed();

		update();
		show();
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
	}

	private void update() {
		int dx = 0, dy = 0;
		if (up) { dx = -1;
		}
		if (down) { dx = 1;
		}
		if (left) { dy = 1;
		}
		if (right) { dy = -1;
		}
		snake.update(dx,dy);
	}

}
