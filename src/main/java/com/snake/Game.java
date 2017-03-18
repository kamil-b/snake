package com.snake;

import java.util.LinkedList;
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

public class Game extends AnimationTimer {

	private static final long FRAME_RATE = 1000000000 / 20;
	private int SCALE = 20;
	private int WIDTH = 600;
	private int HEIGHT = 400;
	private boolean up, down, left, right;
	private int dx = 1, dy = 0;
	private LinkedList<Snake> snakeList = new LinkedList<Snake>();
	private Snake snake;
	private Food food;
	private int score;
	private long timeAtGameStart;
	private long delta = 0;
	private long lastTime = 0;
	private boolean makeSnakeLonger = false;
	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private VBox vbox = new VBox(canvas);
	private Stage primaryStage = new Stage();
	boolean game = true;
	private int lastdx, lastdy;

	Game(Stage primaryStage) {
		timeAtGameStart = System.currentTimeMillis();
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
			if (Math.abs((snake.getPosX() + SCALE / 2) - (food.getPosX() + SCALE / 2)) < SCALE
					&& Math.abs((snake.getPosY() + SCALE / 2) - (food.getPosY() + SCALE / 2)) < SCALE) {
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

		for (Snake s : snakeList) {
			s.show(gc);
		}
		food.show(gc);

		showStaticstcs();
	}

	private void showStaticstcs() {
		long gameLength = (System.currentTimeMillis() - timeAtGameStart) / 1000;
		gc.setFill(Color.BLACK);
		gc.fillText("Score: " + (score * 10), 500, 390);
		gc.fillText("Time: " + gameLength, 450, 390);
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
		if (snakeList.size() > 1) {
			if (lastdx == -1 && dx == 1) {
				dx = -1;
			}
			if (lastdx == 1 && dx == -1) {
				dx = 1;
			}
			if (lastdy == -1 && dy == 1) {
				dy = -1;
			}
			if (lastdy == 1 && dy == -1) {
				dy = 1;
			}
		}
		if (checkIfFoodAte() == true) {
			DropNewFoodLocation();
			score++;
			makeSnakeLonger = true;
		}
		snakeList.get(0).update(dx, dy);
		lastdx = dx;
		lastdy = dy;
		int posX = snakeList.get(0).getPosX();
		int posY = snakeList.get(0).getPosY();

		shiftPosition(posX, posY);

		if (checkIfHeadColideWithBody()) {
			this.stop();
		}

	}

	// TODO
	private boolean checkIfHeadColideWithBody() {
		Snake snake = snakeList.get(0);

		for (int i = 2; i < snakeList.size(); i++) {
			if ((Math.abs((snake.getPosX() + SCALE / 2) - (snakeList.get(i).getPosX() + SCALE / 2)) < SCALE)
					&& (Math.abs((snake.getPosY() + SCALE / 2) - (snakeList.get(i).getPosY() + SCALE / 2)) < SCALE)) {
				return true;
			}
		}
		return false;
	}

	private void shiftPosition(int x, int y) {

		snakeList.push(new Snake(x, y, Color.GREEN, SCALE));
		if (makeSnakeLonger == false) {
			snakeList.removeLast();

		} else {
			makeSnakeLonger = false;
		}
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
