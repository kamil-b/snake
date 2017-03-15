package com.snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

	private static final long FRAME_RATE = 1000000000 / 20;
	private int SCALE = 20;
	private int WIDTH = 600;
	private int HEIGHT = 400;
	private boolean up, down, left, right;
	private int dx = 1, dy = 0;
	private LinkedList<Snake> snakeList = new LinkedList<Snake>();
	private Snake snake;
	private Food food;
	private long delta = 0;
	private long lastTime = 0;
	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private VBox vbox = new VBox(canvas);
	private Stage primaryStage = new Stage();
	boolean game = true;

	Animator(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(new Scene(vbox));
		canvas.setFocusTraversable(true);
		snake = new Snake(WIDTH / 2, HEIGHT / 2, Color.GREEN, SCALE);
		snakeList.add(snake);
		food = new Food(200, 334, Color.RED, SCALE);
		primaryStage.show();
	}

	private boolean checkIfFoodAte() {
		for (Snake snake : snakeList) {
			if ((Math.abs(snake.getPosX() - food.getPosX()) < 10
					|| Math.abs((snake.getPosX() + SCALE) - food.getPosX()) < 10)
					&& (Math.abs(snake.getPosY() - food.getPosY()) < 10
							|| Math.abs((snake.getPosY() + SCALE) - food.getPosY()) < 10)) {
				return true;
			}

		}
		return false;
	}

	private void DropNewFoodLocation() {
		int col = WIDTH / SCALE;
		int rows = HEIGHT / SCALE;
		food.setPosX(new Random().nextInt(col) * SCALE);
		food.setPosY(new Random().nextInt(rows) * SCALE);

	}

	private void checkIfKeyPressed() {
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				// System.out.println("key pressed: " +
				// key.getCode().toString());
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
				default:
					break;

				}
			}
		});

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				// System.out.println("key release: " +
				// key.getCode().toString());
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
				default:
					break;

				}
			}

		});
	}

	private void show() {
		gc.clearRect(0, 0, WIDTH, HEIGHT);

		for (int index = 0; index < snakeList.size(); index++) {
			snakeList.get(index).show(gc);
		}
		food.show(gc);

	}

	private void update() {
		checkIfKeyPressed();

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

		snakeList.get(0).update(dx, dy);
		int posX = snakeList.get(0).getPosX();
		int posY = snakeList.get(0).getPosY();

		shiftPosition(posX, posY);

		if (checkIfFoodAte() == true) {
			DropNewFoodLocation();
			makeSnakeLonger(dx, dy);
		}
	}

	private void shiftPosition(int x, int y) {
		snakeList.removeLast();
		snakeList.push(new Snake(x, y, Color.GREEN, SCALE));

	}

	/*
	 * private void ShiftSnakePosition() { for (int index = 0; index <
	 * snakeList.size() - 1; index++) { snakeList.get(index +
	 * 1).update(snakeList.get(index).getPosX(),
	 * snakeList.get(index).getPosY()); System.out.println("UPDATE" +
	 * snakeList.get(index).toString()); } }
	 */

	private void makeSnakeLonger(int dx, int dy) {
		Snake snake = snakeList.get(0);
		int posX = 0;
		int posY = 0;
		if (dx == 1) {
			posX = -SCALE;
		}
		if (dx == -1) {
			posX = SCALE;
		}
		if (dy == -1) {
			posY = SCALE;
		}
		if (dy == 1) {
			posY = -SCALE;
		}
		Snake newSnake = new Snake(snake.getPosX() + posX, snake.getPosY() + posY, Color.GREEN, SCALE);
		snakeList.push(newSnake);

		System.out.println("Added new part nr: " + snakeList.size());
		System.out.println("parameters of new part: " + newSnake.toString());
		System.out.println("old: " + snake.toString());
	}

	@Override
	public void handle(long currentNanoTime) {

		delta += currentNanoTime - lastTime;
		lastTime = currentNanoTime;
		if (delta >= FRAME_RATE) {
			update();
			show();
			delta = 0;
		}
	}
}
